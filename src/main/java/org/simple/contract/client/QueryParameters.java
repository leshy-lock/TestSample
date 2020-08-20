package org.simple.contract.client;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

import javax.validation.constraints.Max;
import java.util.List;

@Getter
@Builder
public class QueryParameters {
    private final int page;
    @Max(value = 100, message = "Objects limit should be lower than 100")
    private final int limit;
    private final long fromTimestamp;
    private final long toTimestamp;
    @Singular
    private final List<Integer> deliveryServices;
}
