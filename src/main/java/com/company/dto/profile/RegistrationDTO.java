package com.company.dto.profile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationDTO {

    private String name;
    private String surname;
    private String password;
    private String phone;
    private String email;
}
