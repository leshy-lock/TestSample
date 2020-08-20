package org.simple.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.simple.contract.client.ServiceClient;
import org.simple.contract.client.QueryParameters;
import org.simple.contract.dto.ObjectDTO;
import org.simple.contract.dto.ObjectsListDTO;
import org.simple.contract.exception.RemoteServiceInteractionException;
import org.simple.services.HTTPClientWrapper;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class ServiceClientImpl implements ServiceClient {

    private final HTTPClientWrapper httpClient;

    private final ObjectMapper mapper;

    public ObjectsListDTO findAll(String apiKey, long containerId,
                                  @Valid QueryParameters params) throws IOException {
        val url = URLBuilder.objectsList(containerId, params);
        return mapper.readValue(performRequest(apiKey, url), ObjectsListDTO.class);
    }

    public ObjectDTO find(String apiKey, long objectId) throws IOException {
        val url = URLBuilder.singleObject(objectId);
        return mapper.readValue(performRequest(apiKey, url), ObjectDTO.class);
    }

    private String performRequest(String apiKey, String url) throws IOException {
        val response = httpClient.get(apiKey, url, 60);
        val responseContent = IOUtils.toString(response.getEntity().getContent());
        if (isOk(response)) {
            return responseContent;
        } else {
            throw new RemoteServiceInteractionException("Cannot perform request to API: " + responseContent);
        }
    }

    private boolean isOk(HttpResponse response) {
        return response.getStatusLine().getStatusCode() == HttpStatus.SC_OK;
    }

}
