package com.company.dto.article;

import com.company.dto.category.CategoryDTO;
import com.company.dto.region.RegionDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ArticleShortDTO {

    private String uuid;
    private String title;
    private LocalDateTime publicDate;

}
