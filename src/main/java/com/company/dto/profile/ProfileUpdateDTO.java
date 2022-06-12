package com.company.dto.profile;

import com.company.enums.Language;
import com.company.enums.ProfileRole;
import com.company.enums.ProfileStatus;

import java.time.LocalDateTime;

public class ProfileUpdateDTO {

    private String name;
    private String surname;
    private String password;
    private String email;
    private ProfileRole role;
    private Language language;

}
