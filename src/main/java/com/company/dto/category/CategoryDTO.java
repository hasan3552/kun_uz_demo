package com.company.dto.category;

import com.company.enums.CategoryStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

@Getter
@Setter
public class CategoryDTO {

    private Integer id;
    private String nameUz;
    private String nameRu;
    private String nameEn;
    private String key;
    private LocalDateTime createdDate;
    private CategoryStatus status;
    Boolean visible;

}
