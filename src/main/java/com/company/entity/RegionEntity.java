package com.company.entity;

import com.company.enums.RegionStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "region")
public class RegionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nameUz;

    @Column(nullable = false)
    private String nameRu;

    @Column(nullable = false)
    private String nameEn;

    @Column(nullable = false, unique = true)
    private String key;

    @Column(nullable = false,name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "status")
    private RegionStatus status;

}
