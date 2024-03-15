package com.ssafy.db.repository;

import com.ssafy.db.entity.Donation;
import com.ssafy.db.join.GetDonationRecommend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DonationRepository extends JpaRepository<Donation, Long> {

    @Query(value = "select d.name, d.imageUrl, d.currentAmount from Donation d\n" +
            "join Emotion e\n" +
            "on d.emotion = e.emotionPk\n" +
            "where e.kind = :kind",nativeQuery = true)
    List<GetDonationRecommend> donationRecommend(String kind);

    Donation findByName(String name);
}
