package com.company.dto;

import com.company.enums.RegionStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Getter
@Setter
public class RegionDTO {

    private Integer id;
    private String nameUz;
    private String nameRu;
    private String nameEn;
    private String key;
    private LocalDateTime createdDate = LocalDateTime.now();
    private RegionStatus status = RegionStatus.ACTIVE;
    Boolean visible = Boolean.TRUE;

}
