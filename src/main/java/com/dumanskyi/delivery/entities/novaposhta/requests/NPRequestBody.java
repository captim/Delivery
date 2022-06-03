package com.dumanskyi.delivery.entities.novaposhta.requests;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class NPRequestBody {
    @Value("${novaposhta.api.key}")
    private static String apiKey;
    private String modelName;
    private String calledMethod;
    private Map<String, String> methodProperties;
}
