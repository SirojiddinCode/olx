package com.company.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
@Getter
@Setter
@ToString
public class AttachDTO {
    private Integer id;
    private String key; //UUID
    private Long size;
    private String filePath;
    private String extension;
    private LocalDateTime createdDate;
    private String originName;
    private String url;
}
