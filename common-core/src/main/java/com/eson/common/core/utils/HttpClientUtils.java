package com.eson.common.core.utils;

import java.util.Collections;
import java.util.Map;

import com.eson.common.function.ThrowCallable;

import okhttp3.Call;
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
    private static final OkHttpClient OK_HTTP_CLIENT = new OkHttpClient();
    private final MediaType JSON_MEDIA_TYPE = MediaType.parse("application/json; charset=utf-8");
    private static final String CONTENT_TYPE = "application/json";

    public String get(String url) {
        return get(url, Collections.emptyMap());
    }

    public String get(String url, Map<String, Object> params) {
        url = UrlUtils.buildUrl(url, params);
        // 创建请求
        Request request = new Request.Builder()
                .addHeader("Content-Type", CONTENT_TYPE)
                .url(url)
                .build();

        return execute(request);
    }

    public String post(String url, String bodyJson) {
        RequestBody requestBody = RequestBody.create(bodyJson, JSON_MEDIA_TYPE);

        // 创建请求
        Request request = new Request.Builder()
                .addHeader("Content-Type", CONTENT_TYPE)
                .url(url)
                .post(requestBody)
                .build();
        return execute(request);
    }

    private String execute(Request request) {
        Call call = OK_HTTP_CLIENT.newCall(request);
        Response response = execute(call::execute);

        if (response.isSuccessful()) {
            ResponseBody body = response.body();
            if (body != null) {
                return execute(body::toString);
            }
            throw new RuntimeException("Http fail");
        }

        throw new RuntimeException("Http fail");
    }

    private <T> T execute(ThrowCallable<T> callable) {
        try {
            return callable.call();
        }
        catch (Throwable e) {
            throw new RuntimeException("Http fail", e);
        }
    }
}
