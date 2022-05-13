package com.company.dto;

import com.company.enums.ProfileRole;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProfileJwtDto {
    private Integer id;
    private String email;
}
