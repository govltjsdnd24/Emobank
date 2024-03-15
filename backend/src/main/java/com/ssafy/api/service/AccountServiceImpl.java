package com.ssafy.api.service;

import com.ssafy.api.request.AccountCreateReq;
import com.ssafy.common.util.FintechAPIUtil;
import com.ssafy.db.entity.Account;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.AccountRepository;
import com.ssafy.db.repository.UserRepository;
import org.checkerframework.checker.units.qual.A;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Service
public class AccountServiceImpl implements AccountService{
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    S3UpDownloadService s3UpDownloadService;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Value("${Fintech.baseUrl}")
    String fintechUrl;

    @Value("${Fintech.institutionCode}")
    String institutionCode;

    @Value("${Fintech.fintechAppNo}")
    String fintechAppNo;

    @Value("${Fintech.apiKey}")
    String apiKey;

    @Autowired
    FintechAPIUtil fintechAPIUtil;

    @Override
    public boolean accountCreate(AccountCreateReq info) {
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HHmmss"));

        User user = userService.getUserByUserId(info.getUserId());

        Account account = new Account();
        RestTemplate restTemplate = new RestTemplate();

        String url = fintechUrl + "/edu/account/openAccount";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        JSONObject body = new JSONObject();
        //"004-1-74fe2deafd3277"
        body.put("accountTypeUniqueNo",info.getAccountTypeUniqueNo());


        JSONObject header = FintechAPIUtil.baseHeaderSetting("openAccount",user.getUserKey());
//        header.put("apiName","openAccount");
//        header.put("transmissionDate",date);
//        header.put("transmissionTime",time);
//        header.put("institutionCode",institutionCode);
//        header.put("fintechAppNo",fintechAppNo);
//        header.put("apiServiceCode","openAccount");
//        header.put("institutionTransactionUniqueNo",date+time+"000001");
//        header.put("apiKey",apiKey);
//        header.put("userKey",userRepository.findUserKey(userPk));

        body.put("Header",header);

        HttpEntity<?> requestMessage = new HttpEntity<>(body,httpHeaders);

        try {
            HttpEntity<String> response = restTemplate.postForEntity(url,requestMessage,String.class);
            JSONParser parser = new JSONParser();
            Object parse = parser.parse(response.getBody());
            JSONObject responseJson = (JSONObject) parse;
            JSONObject rec = (JSONObject) responseJson.get("REC");
            account.setNumber((String) rec.get("accountNo"));
            account.setBankCode((String) rec.get("bankCode"));
        } catch (Exception e) {
            return false;
        }

        account.setBalance(info.getBalance());
        account.setName(info.getName());
        account.setGoal(info.getGoal());
        account.setPassword(passwordEncoder.encode(info.getPassword()));
        account.setUserPk(user.getUserPk());
        account.setCreateAt(new Date());
        try {
            String imageUrl = s3UpDownloadService.saveImage(info.getImage(), info.getImage().getOriginalFilename(),user.getUserPk());
            account.setImageUrl(imageUrl);
        } catch (IOException e) {
            return false;
        }

        try {
            accountRepository.save(account);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    @Override
    public Account accountDetail(String userId) {
        return accountRepository.findByUserPk(userService.getUserByUserId(userId).getUserPk());
    }
}
