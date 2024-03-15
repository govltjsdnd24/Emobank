package com.ssafy.db.repository;

import com.ssafy.db.entity.Account;
import com.ssafy.db.join.GetAccountHistory;
import com.ssafy.db.join.GetBalanceAndGoal;
import com.ssafy.db.join.GetDeposit;
import com.ssafy.db.join.GetEmotionRank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query(value = "select balance, goal from Account where userPk = :userPk", nativeQuery = true)
    GetBalanceAndGoal getBalanceAndGoal(int userPk);

    @Query(value = "select ah.amount, ah.createAt, ea.memo, k.kind " +
            "from EmotionAccountHistory eah " +
            "join AccountHistory ah " +
            "on eah.transactionPk = ah.transactionPk " +
            "join EmotionAccount ea " +
            "on eah.emotionAccountPk = ea.emotionAccountPk " +
            "join Emotion k " +
            "on ea.emotionPk = k.emotionPk " +
            "where eah.userPk = :userPk",nativeQuery = true)
    List<GetAccountHistory> getAccountHistory(int userPk);

    @Query(value = "select createAt, amount, SUM(amount) OVER(ORDER BY transactionPk) AS amountsum " +
            "from AccountHistory " +
            "where (select accountPk from Account where userPk = :userPk);", nativeQuery = true)
    List<GetDeposit> getDeposit(int userPk);

    @Query(value = "select emotionPk, count(emotionPk) as count " +
            "from EmotionAccount " +
            "where userPk = :userPk " +
            "group by emotionPk " +
            "order by emotionPk", nativeQuery = true)
    List<GetEmotionRank> getEmotionRank(int userPk);

    Account findByUserPk(int userPk);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update Account set balance = :balance where userPk = :userPk",nativeQuery = true)
    int changeBalance(long balance, int userPk);
}
