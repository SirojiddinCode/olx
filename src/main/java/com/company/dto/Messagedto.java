package com.company.dto;

import com.company.enums.MessageStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Messagedto {
    private Integer id;
    private String fromAccount;
    private String toAccount;
    private String title;
    private String content;
    private LocalDateTime usedDate;
    private MessageStatus status;
    private LocalDateTime createdDate=LocalDateTime.now();
}
