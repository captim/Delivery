package com.dumanskyi.delivery.utils;

import com.dumanskyi.delivery.entities.novaposhta.requests.NPRequestBody;
import com.dumanskyi.delivery.entities.novaposhta.responses.NPResponseBody;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.Scanner;

@UtilityClass
public class RequestsUtil {

    private String getRequestBodyByFileName(String fileName) {
        StringBuilder request = new StringBuilder();
        try {
            File file = ResourceUtils.getFile("classpath:requests/" + fileName);
            InputStream in = new FileInputStream(file);
            Scanner scanner = new Scanner(in);
            while (scanner.hasNext()) {
                request.append(scanner.nextLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return request.toString();
    }

    public String getRequestBody(String filename, Map<String, String> params) {
        String request = getRequestBodyByFileName(filename);
        for (Map.Entry<String, String> param: params.entrySet()) {
            request = request.replace("{{ " + param.getKey() + " }}", param.getValue());
        }
        return request;
    }
    public  String sendRequest(String uri, String body) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .header("accept", "appication/json")
                .header("Accept-Charset", "utf-8")
                .header("accept-language", "ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7,uk;q=0.6")
                .method("GET", HttpRequest.BodyPublishers.ofString(body))
                .build();
        HttpResponse<String> response;
        try {
            response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return StringEscapeUtils.unescapeJava(response.body());
    }
    public NPResponseBody sendRequestNP(String apiUri, NPRequestBody requestBody) {
        ObjectMapper objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            String requestBodyString = objectMapper.writeValueAsString(requestBody);
            return objectMapper.readValue(sendRequest(apiUri, requestBodyString), NPResponseBody.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
