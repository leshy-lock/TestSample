package org.simple.services;

import org.apache.http.HttpResponse;

import java.io.IOException;

public interface HTTPClientWrapper {

    HttpResponse get(String apiKey, String url, int timeoutSeconds) throws IOException;

}
