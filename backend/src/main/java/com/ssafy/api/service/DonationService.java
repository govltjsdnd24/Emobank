package com.ssafy.api.service;

import com.ssafy.api.request.DonationPayReq;
import com.ssafy.db.join.GetDonationRecommend;

import java.util.List;

public interface DonationService {
    List<GetDonationRecommend> getDonationRecommend(String emotion);

    boolean donationPay(DonationPayReq donationPayReq);
}
