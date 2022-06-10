package com.company.dto;

import com.company.enums.CategoryStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ArticleTypeDTO {

    private Integer id;
    private String nameUz;
    private String nameRu;
    private String nameEn;
    private String key;
    private LocalDateTime createdDate;
    private CategoryStatus status;
    Boolean visible;

}
