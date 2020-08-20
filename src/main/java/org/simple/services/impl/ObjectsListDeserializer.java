package org.simple.services.impl;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import org.simple.contract.dto.ObjectDTO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ObjectsListDeserializer extends JsonDeserializer<List<ObjectDTO>> {

    @Override
    public List<ObjectDTO> deserialize(JsonParser jp,
                                       DeserializationContext ctx) throws IOException {
        JsonNode objectsNode = jp.getCodec().readTree(jp);
        val result = new ArrayList<ObjectDTO>();
        if (objectsNode.isArray()) {
            ObjectMapper mapper = new ObjectMapper();
            for (final JsonNode objectNode: objectsNode) {
                result.add(mapper.readValue(objectNode.toString(), ObjectDTO.class));
            }
        } else {
            throw new IOException("Node is not an array");
        }

        return result;
    }

}
