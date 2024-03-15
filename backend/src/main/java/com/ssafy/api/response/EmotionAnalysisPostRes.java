package com.ssafy.api.response;

import com.ssafy.common.model.response.BaseResponseBody;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.json.simple.JSONObject;

import java.util.List;

@Getter
@Setter
@ToString
public class EmotionAnalysisPostRes extends BaseResponseBody {
    String fear;
    String surprise;
    JSONObject emotions;
    public static EmotionAnalysisPostRes of(int statusCode, String message, JSONObject emotions){
        EmotionAnalysisPostRes res = new EmotionAnalysisPostRes();
        res.setStatusCode(statusCode);
        res.setMessage(message);
        res.setEmotions(emotions);
        res.setFear((String)emotions.get("fear"));
        res.setSurprise((String)emotions.get("surprise"));
        return res;
    }
}
