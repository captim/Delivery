package com.dumanskyi.delivery.services.impl;

import com.dumanskyi.delivery.entities.novaposhta.requests.NPRequestBody;
import com.dumanskyi.delivery.entities.novaposhta.responses.KeyValue;
import com.dumanskyi.delivery.entities.novaposhta.responses.NPDataConstants;
import com.dumanskyi.delivery.entities.novaposhta.responses.NPResponseBody;
import com.dumanskyi.delivery.services.api.NPService;
import com.dumanskyi.delivery.utils.NPRequestBodyUtil;
import com.dumanskyi.delivery.utils.RequestsUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class NPServiceImpl implements NPService {
    @Value("${novaposhta.api.entry-point.json}")
    private String apiUri;

    @Override
    public String getStandardWarehouseTypeId() {
        NPRequestBody requestBody = NPRequestBodyUtil.getWarehouseTypes();
        NPResponseBody types = RequestsUtil.sendRequestNP(apiUri, requestBody);

        return (String)types.getData().stream()
                .filter(type -> "Поштове відділення".equals(type.get(NPDataConstants.DESCRIPTION)))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("Can't find Post type"))
                .get(NPDataConstants.REF);
    }

    @Override
    public List<KeyValue> getCitiesIdByName(String name, int amount) {
        NPRequestBody requestBody;
        if (name != null && !name.isEmpty()) {
            requestBody = NPRequestBodyUtil.getCitiesByName(name, amount);
        } else {
            requestBody = NPRequestBodyUtil.getAllCities(amount);
        }
        NPResponseBody responseBody = RequestsUtil.sendRequestNP(apiUri, requestBody);
        return requestBodyToKeyValueMap(responseBody);
    }

    @Override
    public List<KeyValue> getWarehouseByCityId(String id, int amount) {
        NPRequestBody requestBody = NPRequestBodyUtil.getWarehousesByCityId(id, amount);
        NPResponseBody responseBody = RequestsUtil.sendRequestNP(apiUri, requestBody);
        return requestBodyToKeyValueMap(responseBody);
    }

    @Override
    public List<KeyValue> getWarehouseByCityId(String id) {
        NPRequestBody requestBody = NPRequestBodyUtil.getWarehousesByCityId(id, getStandardWarehouseTypeId());
        NPResponseBody responseBody = RequestsUtil.sendRequestNP(apiUri, requestBody);
        return requestBodyToKeyValueMap(responseBody);
    }

    @Override
    public List<KeyValue> getWarehouseByCityIdAndNumber(String id, int number) {
        NPRequestBody requestBody = NPRequestBodyUtil.getWarehousesByCityIdAndNumber(id, number);
        NPResponseBody responseBody = RequestsUtil.sendRequestNP(apiUri, requestBody);
        return requestBodyToKeyValueMap(responseBody);
    }

    @Override
    public List<KeyValue> getWarehouseById(String id) {
        NPRequestBody requestBody = NPRequestBodyUtil.getWarehousesById(id);
        NPResponseBody responseBody = RequestsUtil.sendRequestNP(apiUri, requestBody);
        return requestBodyToKeyValueMap(responseBody);
    }

    private List<KeyValue> requestBodyToKeyValueMap(NPResponseBody responseBody) {
        List<KeyValue> keyValues = new ArrayList<>();
        for (Map<String, Object> data : responseBody.getData()) {
            keyValues.add(new KeyValue((String)data.get(NPDataConstants.REF), (String)data.get(NPDataConstants.DESCRIPTION)));
        }
        return keyValues;
    }
}
