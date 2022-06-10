package com.company.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegionCreateDTO {

    private String nameUz;
    private String nameRu;
    private String nameEn;

}
