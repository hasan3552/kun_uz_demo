package com.company.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "article")
public class ArticleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();

    @Column(nullable = false, name = "public_date")
    private LocalDateTime publicDate;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Integer shareCount = 0;

    @JoinColumn(name = "image_id", nullable = false)
    @OneToOne(targetEntity = AttachEntity.class, fetch = FetchType.LAZY)
    private AttachEntity attachEntity;

    @JoinColumn(name = "region_id", nullable = false)
    @OneToOne(targetEntity = RegionEntity.class, fetch = FetchType.LAZY)
    private RegionEntity regionEntity;

    @Column(nullable = false)
    Boolean visible = Boolean.TRUE;








}
