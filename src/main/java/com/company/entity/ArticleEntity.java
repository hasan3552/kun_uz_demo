package com.company.entity;

import com.company.enums.ArticleStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity
@Table(name = "article")
@NoArgsConstructor
public class ArticleEntity {

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(name = "uuid", unique = true)
    private String uuid;


    @Column(nullable = false, columnDefinition = "TEXT")
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private Integer shareCount = 0;

    @Column(nullable = false)
    private Integer viewCount = 0;

    @Column(nullable = false, name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();

    @Column(name = "public_date")
    private LocalDateTime publicDate;

//    @JoinColumn(name = "image_id", nullable = false)
//    @OneToOne(targetEntity = AttachEntity.class, fetch = FetchType.LAZY)
//    private AttachEntity attachEntity;

    @JoinColumn(name = "region_id", nullable = false)
    @ManyToOne(targetEntity = RegionEntity.class, fetch = FetchType.LAZY)
    private RegionEntity region;

    @JoinColumn(name = "category_id", nullable = false)
    @ManyToOne(targetEntity = CategoryEntity.class, fetch = FetchType.LAZY)
    private CategoryEntity category;

    @JoinColumn(name = "moderator_id", nullable = false)
    @ManyToOne(targetEntity = ProfileEntity.class, fetch = FetchType.LAZY)
    private ProfileEntity moderator;

    @JoinColumn(name = "publisher_id")
    @ManyToOne(targetEntity = ProfileEntity.class, fetch = FetchType.LAZY)
    private ProfileEntity publisher;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ArticleStatus status;

    @Column(nullable = false)
    private Boolean visible = Boolean.TRUE;

    public ArticleEntity(String uuid) {
        this.uuid = uuid;
    }
}
