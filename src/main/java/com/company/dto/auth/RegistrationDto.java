package com.company.dto.auth;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class RegistrationDto {
    @NotEmpty(message = "message is empty")
    @Size(min = 4, message = "Password Length Error")
    private String password;
    @Email(message = "Email Error")
    private String email;

}
