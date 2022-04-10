package com.company.dto.auth;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Setter
@Getter
public class AuthorizationDTO {
    @Email(message = "Email Error")
    private String email;

    @NotEmpty(message = "Password is empty")
    @Size(min = 4, message = "Password Length Error")
    private String password;

}
