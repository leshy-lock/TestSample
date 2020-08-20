package org.simple.contract.client;

import org.simple.contract.dto.ObjectDTO;
import org.simple.contract.dto.ObjectsListDTO;

import java.io.IOException;

public interface ServiceClient {

    ObjectsListDTO findAll(String apiKey, long containerId, QueryParameters params) throws IOException;

    ObjectDTO find(String apiKey, long objectId) throws IOException;

}
