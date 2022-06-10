package com.company.entity;

import com.company.enums.ArticleTagStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "article_and_type")
public class ArticleArticleTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate = LocalDateTime.now();

    @JoinColumn(name = "type_id", nullable = false)
    @OneToOne(targetEntity = ArticleTypeEntity.class, fetch = FetchType.LAZY)
    private ArticleTypeEntity tagEntity;

    @JoinColumn(name = "article_id", nullable = false)
    @OneToOne(targetEntity = ArticleEntity.class, fetch = FetchType.LAZY)
    private ArticleEntity articleEntity;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ArticleTagStatus status;

    @Column(nullable = false)
    Boolean visible = Boolean.TRUE;

}
