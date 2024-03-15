package com.ssafy.api.service;

import com.ssafy.db.entity.User;
import com.ssafy.db.join.GetAccountHistory;
import com.ssafy.db.join.GetBalanceAndGoal;
import com.ssafy.db.join.GetDeposit;
import com.ssafy.db.join.GetEmotionRank;
import com.ssafy.db.repository.AccountRepository;
import com.ssafy.db.repository.UserRepository;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("myPageService")
public class MyPageServiceImpl implements MyPageService{
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AccountRepository accountRepository;

    @Override
    public User getUserInfo(String userId) {
        return userRepository.findByUserIdAndIsDelete(userId,0);
    }

    @Override
    public int updateUserInfo(String originUserId, String updateUserId, String password, String nickname, String gender) {
        int userPk = getUserInfo(originUserId).getUserPk();
        return userRepository.updateUserInfo(updateUserId,password,nickname,gender,userPk);
    }

    @Override
    public Map<String, Object> getBalanceAndGoal(String userId) {
        GetBalanceAndGoal result = accountRepository.getBalanceAndGoal(getUserInfo(userId).getUserPk());

        if(result != null) {
            Map<String, Object> data = new HashMap<>();
            data.put("balance",result.getBalance());
            data.put("goal",result.getGoal());
            return data;
        }
        return null;
    }

    @Override
    public List<GetAccountHistory> getAccountHistory(String userId) {
        return accountRepository.getAccountHistory(getUserInfo(userId).getUserPk());
    }

    @Override
    public List<GetDeposit> getDeposit(String userId) {
        return accountRepository.getDeposit(getUserInfo(userId).getUserPk());
    }

    @Override
    public List<GetEmotionRank> getEmotionRank(String userId) {
        return accountRepository.getEmotionRank(getUserInfo(userId).getUserPk());
    }
}
