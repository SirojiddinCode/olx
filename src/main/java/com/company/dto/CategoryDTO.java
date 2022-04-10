package com.company.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryDTO {
    private Integer id;
    @NotBlank
    private String name_ru;
    @NotBlank
    private String name_uz;
    private String key;

    private Integer  parentId;
}
