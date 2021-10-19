package com.sqa.services;

import com.sqa.utils.TestLogger;
import okhttp3.Interceptor;
import okhttp3.Request;
import okio.BufferedSource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class LoggingInterceptor implements Interceptor, TestLogger {
    @Override
    public okhttp3.Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        log(String.format("Sending '%s' request to '%s'. Headers: %s", request.method(), request.url(), request.headers()));

        okhttp3.Response response = chain.proceed(request);
        BufferedSource responseBodySource = Objects.requireNonNull(response.body()).source();

        log(String.format("Receiving response %s. Headers: %n%s" +
                "%nBody: %s", response.request().url(), response.headers(), responseBodySource.readString(StandardCharsets.UTF_8)));

        return response;
    }
}
