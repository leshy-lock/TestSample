package org.simple.services.impl;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.simple.contract.dto.ObjectDTO;

import java.io.IOException;
import java.sql.Timestamp;

public class ObjectDeserializer extends JsonDeserializer<ObjectDTO> {

    @Override
    public ObjectDTO deserialize(JsonParser jp,
                                 DeserializationContext ctx) throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);
        return ObjectDTO.builder()
                .id(node.get("id").asText())
                .timestampSeconds(toTimestampSeconds(node.get("api_updated_at").asText()))
                .jsonContent(node.toString())
                .build();
    }

    private long toTimestampSeconds(String timestampStr) {
        return Timestamp.valueOf(timestampStr.replace("T", " ")).getTime()/1000;
    }

}
