package com.dumanskyi.delivery.entities.novaposhta.responses;

import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class NPResponseBody {
    private boolean success;
    private List<Map<String, Object>> data;
}
