package org.simple.services.impl;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.simple.contract.client.QueryParameters;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class URLBuilder {

    // TODO should use app resources instead
    private static final String BASE = "https://api.service.com/api";
    private static final String OBJECTS_LIST = BASE + "/object/list?container_id=%d%s";
    private static final String OBJECT = BASE + "/object/%d";

    public static String objectsList(long containerId, QueryParameters params) {
        Map<String, String> queryParams = new HashMap();
        if (params != null) {
            applyPagingParams(params.getPage(), params.getLimit(), queryParams);
            applyOtherParams(params.getDeliveryServices(), queryParams);
            applyDateParams(params.getFromTimestamp(), params.getToTimestamp(), queryParams);
        }
        queryParams.put("sort_order", "desc");

        StringBuilder additionalParams = new StringBuilder();
        for (Map.Entry<String, String> param : queryParams.entrySet()) {
            additionalParams.append(String.format("&%s=%s", param.getKey(), param.getValue()));
        }

        return String.format(OBJECTS_LIST, containerId, additionalParams.toString());
    }

    private static void applyOtherParams(List<Integer> deliveryServices, Map<String, String> queryParams) {
        if (deliveryServices != null && !deliveryServices.isEmpty()) {
            queryParams.put("other_service_type", StringUtils.join(deliveryServices.toArray(), ","));
        }
    }

    private static void applyDateParams(long fromTimestamp, long toTimestamp, Map<String, String> queryParams) {
        if (fromTimestamp > 0) {
            queryParams.put("from_timestamp", Long.toString(fromTimestamp));
        }

        if (toTimestamp > 0 && fromTimestamp == 0) {
            throw new IllegalArgumentException("Param 'fromTimestamp' is required if 'toTimestamp' is set");
        }

        if (toTimestamp > 0 && toTimestamp > fromTimestamp) {
            queryParams.put("to_timestamp", Long.toString(toTimestamp));
        }
    }

    private static void applyPagingParams(int page, int limit, Map<String, String> queryParams) {
        if (page > 0 && limit == 0) {
            throw new IllegalArgumentException("Limit is required if page is set");
        }

        if (limit > 0 && page == 0) {
            throw new IllegalArgumentException("Page is required if limit is set");
        }

        if (page > 0) queryParams.put("page", Integer.toString(page));
        if (limit > 0)
            queryParams.put("item_per_page", Integer.toString(limit));
    }

    public static String singleObject(long objectId) {
        return String.format(OBJECT, objectId);
    }

}
