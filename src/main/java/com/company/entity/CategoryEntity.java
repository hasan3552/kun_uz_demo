package com.company.entity;

import com.company.enums.CategoryStatus;
import com.company.enums.RegionStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "category")
public class CategoryEntity {

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
    private LocalDateTime createdDate = LocalDateTime.now();

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private CategoryStatus status = CategoryStatus.ACTIVE;

    @Column(nullable = false)
    Boolean visible = Boolean.TRUE;
}

