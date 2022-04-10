package com.company.dto.post;

import com.company.enums.PostCondition;
import lombok.Data;

@Data
public class PostFilterDto {
    private Integer categoryId;
    private Integer regionId;
    private PostCondition condition;
    private Long fromprice;
    private Long toprice;
}
