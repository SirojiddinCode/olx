package com.company.dto;

import com.company.enums.RegionType;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class RegionDTO {
    private Integer id;
    @NotBlank
    private String name_uz;
    @NotBlank
    private String name_ru;

    private RegionType type;

    private Integer parentId;

}
