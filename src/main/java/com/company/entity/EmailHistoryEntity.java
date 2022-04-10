package com.company.entity;


import com.company.enums.MessageStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
@Getter
@Setter
@Entity
@Table(name = "emailHistory")
public class EmailHistoryEntity extends BaseEntity{


    @Column(name = "from_account")
    private String fromAccount;
    @Column(name = "to_account")
    private String toAccount;
    @Column(name = "title")
    private String title;
    @Column(name = "content")
    private String content;
    @Column(name = "used_date")
    private LocalDateTime usedDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private MessageStatus status;
}
