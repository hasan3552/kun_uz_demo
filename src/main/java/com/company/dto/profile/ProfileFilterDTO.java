package com.company.dto.profile;

import com.company.enums.Language;
import com.company.enums.ProfileRole;
import com.company.enums.ProfileStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ProfileFilterDTO {

    private Integer id;
    private String name;
    private String surname;
    private String email;
    private String password;
    private String phone;
    private String createdDateFrom;
    private String createdDateTo;
    private Boolean visible;
    private ProfileStatus status;
    private ProfileRole role;
    private Language language;

}
