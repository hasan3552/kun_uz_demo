package com.company.repository;

import com.company.entity.ArticleEntity;
import com.company.entity.ProfileEntity;
import com.company.entity.SmsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SmsRepository extends JpaRepository<SmsEntity, Integer> {
    Optional<SmsEntity> findTopByPhoneOrderByCreatedDateDesc(String phone);


    @Query(value = "select * from sms where phone = :phone and created_date + INTERVAL '1 MINUTE' > now() ", nativeQuery = true)
    List<SmsEntity> findAllByPhoneLimit5(@Param("phone") String phone);
}
