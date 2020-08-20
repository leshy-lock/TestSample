package org.simple.contract.dto;

import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Getter;
import org.simple.services.impl.ObjectDeserializer;

@Getter
@JsonDeserialize(using = ObjectDeserializer.class)
@Builder
public class ObjectDTO {

    private final String id;

    private final long timestampSeconds;

    @JsonRawValue
    private final String jsonContent;

}
