package com.ssafy.db.repository;

import com.ssafy.db.entity.Account;
import com.ssafy.db.entity.OriginalAccount;
import com.ssafy.db.join.GetAccountHistory;
import com.ssafy.db.join.GetBalanceAndGoal;
import com.ssafy.db.join.GetDeposit;
import com.ssafy.db.join.GetEmotionRank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OriginalAccountRepository extends JpaRepository<OriginalAccount, Long> {

}
