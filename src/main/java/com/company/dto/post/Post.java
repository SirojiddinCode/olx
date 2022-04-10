package com.company.dto.post;

import com.company.dto.AttachDTO;
import com.company.dto.RegionDTO;
import com.company.enums.CurrencyType;
import com.company.enums.PaymentType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Post {
    private Integer postId;
    private String title;
    private LocalDateTime createdDate;
    private PaymentType paymentType;
    private Long price;
    private CurrencyType currencyType;
    private RegionDTO regionDTO;
    private AttachDTO attach;
}
