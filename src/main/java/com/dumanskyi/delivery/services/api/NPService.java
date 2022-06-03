package com.dumanskyi.delivery.services.api;

import com.dumanskyi.delivery.entities.novaposhta.responses.KeyValue;

import java.util.List;
import java.util.Map;

public interface NPService {
    String getStandardWarehouseTypeId();
    List<KeyValue> getCitiesIdByName(String name, int amount);
    List<KeyValue> getWarehouseByCityId(String id, int amount);
    List<KeyValue> getWarehouseByCityId(String id);
    List<KeyValue> getWarehouseByCityIdAndNumber(String id, int number);
    List<KeyValue> getWarehouseById(String id);
}
