package com.webank.oracle.base.utils;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.webank.oracle.base.enums.ReqStatusEnum;
import com.webank.oracle.base.exception.RemoteCallException;

import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * A util to send common http request.
 */
@Slf4j
public class HttpUtil {

    public static final String EMPTY_RESPONSE = "";


    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    /**
     * 连接超时时间, 默认 5s.
     */
    private static final int DEFAULT_CONNECT_TIMEOUT = 5;
    /**
     * 默认读取超时时间, 默认 5s.
     */
    private static final int DEFAULT_READ_TIMEOUT = 5;
    /**
     * 默认写数据超时时间, 默认 5s.
     */
    private static final int DEFAULT_WRITE_TIMEOUT = 5;

    // avoid creating several instances, should be singleon
    private static OkHttpClient client = null;


    /**
     *
     * @param connectTimeout
     * @param readTimeout
     * @param writeTimeout
     */
    public static void init(int connectTimeout, int readTimeout, int writeTimeout) {
        log.info("Init http util, connect timeout:[{}], read timeout:[{}], write timeout:[{}]",
                connectTimeout, readTimeout, writeTimeout);

        client = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .connectTimeout(connectTimeout, TimeUnit.SECONDS) //连接超时
                .readTimeout(readTimeout, TimeUnit.SECONDS) //读取超时
                .writeTimeout(writeTimeout, TimeUnit.SECONDS) //写超时
                .build();
    }

    /**
     *
     * @param url
     * @param queryMap
     * @param body
     * @return
     * @throws IOException
     */
    public static String post(String url, Map<String, String> queryMap, Object body) throws Exception {
        // check if client is null
        if (client == null) {
            log.warn("Http util was not initialized yet, init with default timeout." );

            init(DEFAULT_CONNECT_TIMEOUT, DEFAULT_READ_TIMEOUT, DEFAULT_WRITE_TIMEOUT);
        }

        log.info("Start Http request:[{}:{}:{}]", url, JsonUtils.toJSONString(queryMap), JsonUtils.toJSONString(body));

        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
        if (queryMap != null && queryMap.size() > 0) {
            queryMap.entrySet().forEach(entry -> urlBuilder.addQueryParameter(entry.getKey(), entry.getValue()));
        }

        String newUrl = urlBuilder.build().toString();

        Request.Builder requestBuilder = new Request.Builder().url(newUrl);
        if (body != null) {
            requestBuilder.post(RequestBody.create(JsonUtils.toJSONString(body), JSON));
        }

        String responseString = EMPTY_RESPONSE;
        try {
            Request request = requestBuilder.build();

            Response response = client.newCall(request).execute();
            checkHttpStatusCode(response);
            responseString = response.body().string();
        } catch (Exception e) {
            throw e;
        } finally {
            log.info("Req:[{}], body:[{}], response:[{}]", newUrl, JsonUtils.toJSONString(body), responseString);
        }
        return responseString;

    }

    public static String post(String url, Object body) throws Exception {
        return post(url, null, body);
    }


    public static String get(String url) throws Exception {
        return get(url, null);

    }

    public static String get(String url, Map<String, String> queryMap) throws Exception {
        return post(url, queryMap, null);
    }

    /**
     * Check http status code.
     *
     * @param response
     */
    public static void checkHttpStatusCode(Response response){
        if (response == null){
            throw RemoteCallException.build(ReqStatusEnum.EMPTY_RESPONSE_ERROR);
        }
        switch (response.code()){
            case 200:
                return;
            case 404:
                throw RemoteCallException.build(ReqStatusEnum._404_NOT_FOUND_ERROR);

            case 500:
                throw RemoteCallException.build(ReqStatusEnum._500_SERVER_ERROR);

            default:
                throw RemoteCallException.build(ReqStatusEnum.OTHER_CODE_ERROR, String.valueOf(response.code()));
        }
    }
}