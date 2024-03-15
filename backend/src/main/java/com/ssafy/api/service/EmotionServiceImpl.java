package com.ssafy.api.service;

import com.ssafy.api.service.EmotionService;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service("emotionService")
public class EmotionServiceImpl implements EmotionService {

    private static final String FAST_API_URL = "http://127.0.0.1:8000";

    @Override
    public JSONObject analyzeEmotion(String memo) {
        String url = FAST_API_URL + "/emotion/dic_test";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("memo", memo);

        HttpEntity<?> requestMessage = new HttpEntity<>(jsonObject.toString(), httpHeaders);

        try {
            HttpEntity<String> response = restTemplate.postForEntity(url, requestMessage, String.class);

            // JSON 형식의 응답 데이터를 파싱하여 JSONObject로 반환
            JSONParser parser = new JSONParser();
            JSONObject jsonResponse = (JSONObject) parser.parse(response.getBody());

            // "fear"와 "surprise" 필드의 값을 갖는 JSONObject를 생성하여 반환
            JSONObject emotions = new JSONObject();
            emotions.put("fear", jsonResponse.get("fear"));
            emotions.put("surprise", jsonResponse.get("surprise"));
            System.out.println("emotions : " + emotions);
            System.out.println("jsonResponse : " + jsonResponse);
            return emotions;

        } catch (HttpClientErrorException error) {
            String errorMessage = error.getResponseBodyAsString();
            System.out.println("HTTP 요청 실패: " + errorMessage);
            return null;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
