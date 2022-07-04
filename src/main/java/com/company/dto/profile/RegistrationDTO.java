package com.company.dto.profile;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RegistrationDTO {

 //   @ApiModelProperty(name = "get qilish", example = "masalan")
    private String name;
    private String surname;
    private String password;
    private String phone;
    private String email;

   // @ApiModelProperty(name = "get qilish", example = "masalan")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
