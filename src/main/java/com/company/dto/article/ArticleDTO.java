package com.company.dto.article;

import com.company.dto.category.CategoryDTO;
import com.company.dto.profile.ProfileDTO;
import com.company.dto.region.RegionDTO;
import com.company.dto.tag.TagDTO;
import com.company.dto.type.TypeDTO;
import com.company.entity.ProfileEntity;
import com.company.enums.ArticleStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
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
    private LocalDateTime createdDate;
    private LocalDateTime publicDate;

    private ProfileDTO moderatorDTO;
    private ProfileDTO publisherDTO;
    private ArticleStatus status;
    private Boolean visible;


}
