package com.company.dto;

import com.company.enums.Language;
import com.company.enums.ProfileRole;
import com.company.enums.ProfileStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileDTO {

    private Integer id;
    private String name;
    private String surname;
    private String password;
    private String email;
    private LocalDateTime createdDate;
    private Boolean visible;
    private ProfileStatus status;
    private ProfileRole role;
    private Language language;

    private String jwt;

}
