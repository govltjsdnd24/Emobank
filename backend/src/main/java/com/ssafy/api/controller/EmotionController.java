package com.ssafy.api.controller;

import com.ssafy.api.request.EmotionAnalysisPostReq;
import com.ssafy.api.response.EmotionAnalysisPostRes;
import com.ssafy.api.response.MyPageGetUserRes;
import com.ssafy.api.response.UserLoginPostRes;
import com.ssafy.api.service.EmotionService;
import com.ssafy.api.service.MyPageService;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.User;
import io.swagger.annotations.*;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@Api(value = "감정분석 API", tags = {"Emotion."})
@RestController
@RequestMapping("/emotion")
public class EmotionController {

    @Autowired
    EmotionService emotionService;

    @PostMapping("/ai")
    @ApiOperation(value = "AI 분석 요청", notes = "<strong>메모</strong>의 감정 분석을 요청한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공", response = UserLoginPostRes.class),
            @ApiResponse(code = 401, message = "인증 실패", response = BaseResponseBody.class),
            @ApiResponse(code = 404, message = "사용자 없음", response = BaseResponseBody.class),
            @ApiResponse(code = 500, message = "서버 오류", response = BaseResponseBody.class)
    })
    public ResponseEntity<? extends BaseResponseBody> analyzeEmotion(@RequestBody @ApiParam(value="Ai 감정 분석", required = true) EmotionAnalysisPostReq emotionAnalysisInfo) {
        String userId = emotionAnalysisInfo.getUserId();
        String memo = emotionAnalysisInfo.getMemo();

        JSONObject emotions = emotionService.analyzeEmotion(memo);

        if(memo != null) {
            return ResponseEntity.ok(EmotionAnalysisPostRes.of(200, "Success", (JSONObject) emotions));
        }
        return ResponseEntity.ok(BaseResponseBody.of(200,"Fail"));
    }
}
