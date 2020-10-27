package com.webank.oracle.http;

import static com.webank.oracle.base.properties.ConstantProperties.MAX_ERROR_LENGTH;
import static com.webank.oracle.base.utils.JsonUtils.stringToJsonNode;
import static com.webank.oracle.base.utils.JsonUtils.toList;

import java.net.SocketTimeoutException;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.fisco.bcos.web3j.utils.Numeric;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.webank.oracle.base.enums.ReqStatusEnum;
import com.webank.oracle.base.exception.RemoteCallException;
import com.webank.oracle.base.properties.ConstantProperties;
import com.webank.oracle.base.utils.HttpUtil;
import com.webank.oracle.repository.ReqHistoryRepository;
import com.webank.oracle.repository.domian.ReqHistory;

import lombok.extern.slf4j.Slf4j;

/**
 * service for http request.
 */
@Slf4j
@Service
public class HttpService {

    @Autowired private ConstantProperties constantProperties;
    @Autowired private ReqHistoryRepository reqHistoryRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        HttpUtil.init(constantProperties.getConnectTimeout(),
                constantProperties.getReadTimeout(), constantProperties.getWriteTimeout());

    }


    /**
     * get result by url,and get value from the result by keyList.
     *
     * @param logId
     * @param url
     * @param resultKeyList
     * @return
     */
    public Object getObjectByUrlAndKeys(byte[] logId, String url, String format, List<String> resultKeyList) throws Exception {
        //get data
        String reqId = Numeric.toHexString(logId);
        int reqStatus = ReqStatusEnum.SUCCESS.getStatus();
        String error = null;

        long start = System.currentTimeMillis();
        Object value = null;
        try {
            String result = HttpUtil.get(url);

            // fetch value from result by format
            switch (StringUtils.lowerCase(format)) {
                case "json":
                    JsonNode jsonNode = stringToJsonNode(result);
                    if (jsonNode == null) {
                        throw RemoteCallException.build(ReqStatusEnum.RESULT_FORMAT_ERROR, format, result);
                    }
                    value = getValueByKeys(jsonNode, resultKeyList);
                default:
                    value = result.split("\n")[0];
            }
        } catch (SocketTimeoutException e) {

            // socket error
            String errorMsg = ExceptionUtils.getRootCauseMessage(e);
            ReqStatusEnum reqStatusEnum = ReqStatusEnum.getBySocketErrorMsg(errorMsg);
            reqStatus = reqStatusEnum.getStatus();
            error = reqStatusEnum.format(errorMsg);
            log.error("Request url:[{}] socket error:[{}]", url, error);
        } catch (RemoteCallException e) {

            // response error
            reqStatus = e.getStatus();
            error = e.getDetailMessage();
            log.error("Request url:[{}] response error:[{}]", url, error);
        } catch (Exception e) {

            // other exception
            reqStatus = ReqStatusEnum.UNEXPECTED_EXCEPTION_ERROR.getStatus();
            error = ReqStatusEnum.UNEXPECTED_EXCEPTION_ERROR.format(ExceptionUtils.getRootCauseMessage(e));
            log.error("Request url:[{}] unexpected exception error:[{}]", url, error);
        } finally {

            // check req history exists
            Optional<ReqHistory> reqHistoryOptional = this.reqHistoryRepository.findByReqId(reqId);
            if (reqHistoryOptional.isPresent()) {
                // update req history record
                ReqHistory reqHistory = reqHistoryOptional.get();
                reqHistory.setReqStatus(reqStatus);
                if (StringUtils.isNotBlank(error)) {
                    reqHistory.setError(StringUtils.length(error) > MAX_ERROR_LENGTH ? StringUtils.substring(error, 0, MAX_ERROR_LENGTH) : error);
                }
                reqHistory.setProcessTime(System.currentTimeMillis() - start);
                reqHistory.setResult(String.valueOf(value));

                // save
                this.reqHistoryRepository.save(reqHistory);
            } else {
                log.error("No request history found. Should not be here.");
            }
        }
        return value;
    }

    /**
     * get value from object by keyList.
     *
     * @param jsonNode
     * @param keyList
     * @return
     */
    private static Object getValueByKeys(JsonNode jsonNode, List<String> keyList) {
        if (jsonNode == null || keyList == null || keyList.size() == 0) return jsonNode;
        Object finalResult = jsonNode;
        for (String key : keyList) {
            finalResult = getValueByKey(finalResult, key);
        }
        return finalResult;
    }


    /**
     * get value by key.
     *
     * @param jsonNode
     * @param key
     * @return
     */
    private static Object getValueByKey(Object jsonNode, String key) {
        if (jsonNode instanceof ArrayNode) {
            List<Object> jsonArray = toList(jsonNode);
            return jsonArray.get(Integer.valueOf(String.valueOf(key)));
        }
        try {
            JsonNode jsonNode1 = (JsonNode) jsonNode;
            return jsonNode1.get(key);
        } catch (Exception ex) {
            // TODO. throws ??
            return jsonNode;
        }
    }

}
