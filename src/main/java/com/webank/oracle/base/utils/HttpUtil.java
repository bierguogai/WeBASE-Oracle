package com.webank.oracle.base.utils;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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


    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");


    /**
     * 连接超时时间, 默认 10s.
     */
    private static final int CONNECT_TIMEOUT = 5000;
    /**
     * 默认超时时间, 默认 10s.
     */
    private static final int DEFAULT_TIMEOUT = 60000;

    // avoid creating several instances, should be singleon
    private static OkHttpClient client = null;

    static {
        client = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS) //连接超时
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS) //读取超时
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS) //写超时
                .build();
    }

    public static String post(String url, Map<String, String> queryMap, Object body) {
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
            responseString = response.body().string();
        } catch (IOException e) {
            log.error("Http request failed:Req:[{}], body:[{}]",
                    newUrl, JsonUtils.toJSONString(body), e);
            // TODO.
        } finally {
            log.info("Req:[{}], body:[{}], response:[{}]", newUrl, JsonUtils.toJSONString(body), responseString);
        }
        return responseString;

    }

    public static String post(String url, Object body) {
        return post(url, null, body);
    }


    public static String get(String url) {
        return get(url, null);

    }

    public static String get(String url, Map<String, String> queryMap) {
        return post(url, queryMap, null);
    }
}