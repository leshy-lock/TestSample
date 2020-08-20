package org.simple.contract.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;
import org.simple.services.impl.ObjectsListDeserializer;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(value = {"next", "prev", "item_per_page", "page"})
public class ObjectsListDTO {

    private int count;

    @JsonProperty("delivery_services_available")
    private List<DeliveryServiceDTO> deliveryServicesAvailable;

    @JsonProperty("data")
    @JsonDeserialize(using = ObjectsListDeserializer.class, as = List.class)
    private List<ObjectDTO> objects;

}
