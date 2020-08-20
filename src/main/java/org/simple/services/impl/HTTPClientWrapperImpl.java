package org.simple.services.impl;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.simple.services.HTTPClientWrapper;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class HTTPClientWrapperImpl implements HTTPClientWrapper {

    private static final String API_KEY_HEADER = "API_KEY";

    public HttpResponse get(String apiKey, String url, int timeoutSeconds) throws IOException {
        HttpGet req = new HttpGet(url);
        req.setHeader(API_KEY_HEADER, apiKey);

        int timeout = timeoutSeconds * 1000;
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(timeout)
                .setConnectionRequestTimeout(timeout)
                .setSocketTimeout(timeout).build();
        HttpClient client = HttpClients.custom()
                .setDefaultRequestConfig(requestConfig).build();
        return client.execute(req);
    }

}
