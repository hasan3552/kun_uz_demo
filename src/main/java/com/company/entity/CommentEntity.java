package com.company.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "comment")
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();

    @Column(name = "update_date")
    private LocalDateTime updateDate;

    @JoinColumn(nullable = false, name = "profile_id")
    @ManyToOne(targetEntity = ProfileEntity.class, fetch = FetchType.LAZY)
    private ProfileEntity profileEntity;

    @JoinColumn(nullable = false, name = "article_id")
    @ManyToOne(targetEntity = ArticleEntity.class, fetch = FetchType.LAZY)
    private ArticleEntity articleEntity;

    @JoinColumn(name = "comment_id")
    @ManyToOne(targetEntity = CommentEntity.class, fetch = FetchType.LAZY)
    private CommentEntity commentEntity;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Boolean visible = Boolean.TRUE;

}
