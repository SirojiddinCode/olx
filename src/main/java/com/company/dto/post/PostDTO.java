package com.company.dto.post;

import com.company.dto.AttachDTO;
import com.company.enums.CurrencyType;
import com.company.enums.PaymentType;
import com.company.enums.PostCondition;
import com.company.enums.PostStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostDTO {
    private Integer id;
    @NotNull(message = "title can not be null")
    @Size(min = 16,max = 70,message = "title length error")
    private String title;
    @NotNull(message ="content can not be null" )
    @Size(min = 80,max = 9000)
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime expirationDate;
    @NotBlank
    private PaymentType paymentType;
    @NotBlank
    private PostCondition condition;

    private long price;
    @NotBlank
    private Integer categoryId;
    @NotBlank
    private Integer profileId;
    @NotBlank
    private Integer regionId;

    private String ownerPhoneNumber;
    private PostStatus status;

    private List<AttachDTO> attachList;
    private CurrencyType currencyType;
}
