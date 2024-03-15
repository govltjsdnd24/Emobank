package com.ssafy.api.service;

import org.json.simple.JSONObject;

public interface EmotionService {
    JSONObject analyzeEmotion(String memo);
}
