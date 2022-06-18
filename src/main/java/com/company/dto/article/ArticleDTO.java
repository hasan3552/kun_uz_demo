package com.company.dto.article;

import com.company.dto.category.CategoryDTO;
import com.company.dto.profile.ProfileDTO;
import com.company.dto.region.RegionDTO;
import com.company.dto.tag.TagDTO;
import com.company.dto.type.TypeDTO;
import com.company.entity.ProfileEntity;
import com.company.enums.ArticleStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ArticleDTO {

    private String uuid;
    private String title;
    private String description;
    private String content;

    private RegionDTO regionDTO;
    private CategoryDTO categoryDTO;

    private List<TypeDTO> typeDTOList;
    private List<TagDTO> tagDTOList;

    private Integer shareCount;
    private Integer viewCount;

    private Integer likeCount;
    private Integer dislikeCount;

    private LocalDateTime createdDate;
    private LocalDateTime publicDate;

    private ProfileDTO moderatorDTO;
    private ProfileDTO publisherDTO;
    private ArticleStatus status;
    private Boolean visible;

    public ArticleDTO(String uuid, String title, String description, String content, Integer shareCount,
                      Integer viewCount, LocalDateTime publicDate, ArticleStatus status, Boolean visible) {
        this.uuid = uuid;
        this.title = title;
        this.description = description;
        this.content = content;
        this.shareCount = shareCount;
        this.viewCount = viewCount;
        this.publicDate = publicDate;
        this.status = status;
        this.visible = visible;
    }
}
