package com.company.entity;

import com.company.enums.ArticleLikeStatus;
import com.company.enums.ArticleStatus;
import com.company.enums.LikeStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "article_like")
public class ArticleLikeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate = LocalDateTime.now();

    @JoinColumn(name = "profile_id", nullable = false)
    @OneToOne(targetEntity = ProfileEntity.class, fetch = FetchType.LAZY)
    private ProfileEntity profile;

    @JoinColumn(name = "article_id", nullable = false)
    @OneToOne(targetEntity = ArticleEntity.class, fetch = FetchType.LAZY)
    private ArticleEntity article;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private LikeStatus status;

}
