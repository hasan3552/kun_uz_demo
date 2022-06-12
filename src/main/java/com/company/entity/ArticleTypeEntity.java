package com.company.entity;

import com.company.enums.ArticleTagStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "article_type")
public class ArticleTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate = LocalDateTime.now();

    @JoinColumn(name = "type_id", nullable = false)
    @ManyToOne(targetEntity = TypesEntity.class)
    private TypesEntity types;

    @JoinColumn(name = "article_id", nullable = false)
    @ManyToOne(targetEntity = ArticleEntity.class)
    private ArticleEntity article;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ArticleTagStatus status = ArticleTagStatus.ACTIVE;

    @Column(nullable = false)
    Boolean visible = Boolean.TRUE;

}
