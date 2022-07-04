package com.company.repository;

import com.company.entity.SmsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SmsRepository extends JpaRepository<SmsEntity, Integer> {
    Optional<SmsEntity> findTopByPhoneOrderByCreatedDateDesc(String phone);


    @Query(value = "select count(*) from sms where phone = :phone and created_date + INTERVAL '1 MINUTE' > now() ", nativeQuery = true)
    Long countResend(@Param("phone") String phone);
}
