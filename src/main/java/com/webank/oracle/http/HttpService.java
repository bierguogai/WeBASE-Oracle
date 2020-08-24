package com.webank.oracle.http;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.webank.oracle.base.utils.HttpsUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import static com.webank.oracle.base.utils.JsonUtils.stringToJsonNode;
import static com.webank.oracle.base.utils.JsonUtils.toJSONString;
import static com.webank.oracle.base.utils.JsonUtils.toList;

/**
 * service for http request.
 */
@Slf4j
@Service
public class HttpService {
    @Autowired
    private RestTemplate restTemplate;

    /**
     * get result by url,and get value from the result by keyList.
     *
     * @param url
     * @param resultKeyList
     * @return
     */
    //todo  exception log
    public Object getObjectByUrlAndKeys(String url, String formate, List<String> resultKeyList) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, IOException {
        try {
            //String result = restTemplate.getForObject(url, String.class);
            String result =     HttpsUtil.get(url);
            if(formate.equals("json")) {
                JsonNode jsonNode = stringToJsonNode(result);
                return getValueByKeys(jsonNode, resultKeyList);
            }
            //if(formate.equals("plain"))
            else  {
                return result.split("\n")[0];
            }
        }
//        catch (ResourceAccessException e) {
//            log.error("getObjectByUrlAndKeys error, please check the url, url:{} resultKeyList:{}",url, toJSONString(resultKeyList), e);
//        }

        catch (Exception ex) {
            log.error("getObjectByUrlAndKeys error, url:{} resultKeyList:{}", url, toJSONString(resultKeyList), ex);
            throw ex;
        }
    }

    /**
     * get value from object by keyList.
     *
     * @param jsonNode
     * @param keyList
     * @return
     */
    private static  Object getValueByKeys(JsonNode jsonNode, List<String> keyList) {
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
            JsonNode jsonNode1 = (JsonNode)jsonNode;
            return jsonNode1.get(key);
        } catch (Exception ex) {
            return jsonNode;
        }
    }

}
