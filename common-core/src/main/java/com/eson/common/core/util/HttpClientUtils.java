package com.eson.common.core.util;

import java.util.Collections;
import java.util.Map;

import com.eson.common.function.util.ThrowUtils;

import okhttp3.Call;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * 基于OKHttp
 *
 * @author dengxiaolin
 * @since 2021/01/15
 */
public class HttpClientUtils {

    public static final MediaType JSON_MEDIA_TYPE = MediaType.parse("application/json; charset=utf-8");
    private static final String JSON_CONTENT_TYPE = "application/json";

    private static final OkHttpClient OK_HTTP_CLIENT = new OkHttpClient();

    public static String get(String url) {
        return get(url, Collections.emptyMap());
    }

    public static String get(String url, Map<String, Object> params) {
        url = UrlUtils.buildUrl(url, params);
        // 创建请求
        Request request = new Request.Builder()
                .addHeader("Content-Type", JSON_CONTENT_TYPE)
                .url(url)
                .build();

        return execute(request);
    }

    public static String get(String url, Map<String, String> headerMap, Map<String, Object> paramMap) {
        url = UrlUtils.buildUrl(url, paramMap);
        headerMap = headerMap == null ? Collections.emptyMap() : headerMap;

        // 创建请求
        Request request = new Request.Builder()
                .headers(Headers.of(headerMap))
                .url(url)
                .build();

        return execute(request);
    }

    public static String post(String url, String bodyJson) {
        RequestBody requestBody = RequestBody.create(bodyJson, JSON_MEDIA_TYPE);

        // 创建请求
        Request request = new Request.Builder()
                .addHeader("Content-Type", JSON_CONTENT_TYPE)
                .url(url)
                .post(requestBody)
                .build();
        return execute(request);
    }

    public static String post(String url, Map<String, String> headerMap, String bodyJson) {
        RequestBody requestBody = RequestBody.create(bodyJson, JSON_MEDIA_TYPE);
        headerMap = headerMap == null ? Collections.emptyMap() : headerMap;

        // 创建请求
        Request request = new Request.Builder()
                .headers(Headers.of(headerMap))
                .url(url)
                .post(requestBody)
                .build();
        return execute(request);
    }

    private static String execute(Request request) {
        Call call = OK_HTTP_CLIENT.newCall(request);
        Response response = ThrowUtils.execute(call::execute);

        if (response.isSuccessful()) {
            ResponseBody body = response.body();
            if (body != null) {
                return ThrowUtils.execute(body::string);
            }
            throw new RuntimeException("Http fail, response =" + response.toString());
        }

        throw new RuntimeException("Http fail, response = " + response.toString());
    }
}
