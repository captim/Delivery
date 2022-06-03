package com.dumanskyi.delivery.utils;

import com.dumanskyi.delivery.entities.novaposhta.requests.NPRequestBody;
import com.dumanskyi.delivery.entities.novaposhta.responses.NPDataConstants;
import lombok.experimental.UtilityClass;

import java.util.HashMap;
import java.util.Map;

@UtilityClass
public class NPRequestBodyUtil {
    public NPRequestBody getWarehouseTypes() {
        return NPRequestBody.builder()
                .modelName("Address")
                .calledMethod("getWarehouseTypes")
                .methodProperties(new HashMap<>())
                .build();
    }
    public NPRequestBody getCitiesByName(String name, int amount) {
        return NPRequestBody.builder()
                .modelName("Address")
                .calledMethod("getCities")
                .methodProperties(Map.of(
                        NPDataConstants.FIND_BY_STRING, name,
                        NPDataConstants.PAGE, Integer.toString(1),
                        NPDataConstants.LIMIT, Integer.toString(amount)
                ))
                .build();
    }
    public NPRequestBody getAllCities(int amount) {
        return NPRequestBody.builder()
                .modelName("Address")
                .calledMethod("getCities")
                .methodProperties(Map.of(
                        NPDataConstants.PAGE, Integer.toString(1),
                        NPDataConstants.LIMIT, Integer.toString(amount)
                ))
                .build();
    }
    public NPRequestBody getWarehousesByCityId(String id, int amount) {
        return NPRequestBody.builder()
                .modelName("Address")
                .calledMethod("getWarehouses")
                .methodProperties(Map.of(
                        NPDataConstants.CITY_REF, id,
                        NPDataConstants.PAGE, Integer.toString(1),
                        NPDataConstants.LIMIT, Integer.toString(amount)
                ))
                .build();
    }
    public NPRequestBody getWarehousesByCityId(String id, String warehouseId) {
        return NPRequestBody.builder()
                .modelName("Address")
                .calledMethod("getWarehouses")
                .methodProperties(Map.of(
                        NPDataConstants.CITY_REF, id,
                        NPDataConstants.PAGE, Integer.toString(1),
                        NPDataConstants.TYPE_WAREHOUSE_REF, warehouseId
                ))
                .build();
    }
    public NPRequestBody getWarehousesByCityIdAndNumber(String id, int number) {
        return NPRequestBody.builder()
                .modelName("Address")
                .calledMethod("getWarehouses")
                .methodProperties(Map.of(
                        NPDataConstants.WAREHOUSE_ID, Integer.toString(number),
                        NPDataConstants.CITY_REF, id,
                        NPDataConstants.PAGE, Integer.toString(1),
                        NPDataConstants.LIMIT, Integer.toString(20)
                ))
                .build();
    }
    public NPRequestBody getWarehousesById(String id) {
        return NPRequestBody.builder()
                .modelName("Address")
                .calledMethod("getWarehouses")
                .methodProperties(Map.of(
                        NPDataConstants.REF, id
                ))
                .build();
    }
}
