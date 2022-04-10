package com.company.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileDTO {
    private Integer id;
    private String name;
    private String surname;
    private String login;
    private String pswd;
    private String email;
    private LocalDateTime lastActiveDate;

    private String jwtTooken;
}
