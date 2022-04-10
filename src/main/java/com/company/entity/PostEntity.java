package com.company.entity;

import com.company.enums.CurrencyType;
import com.company.enums.PaymentType;
import com.company.enums.PostCondition;
import com.company.enums.PostStatus;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "post")
public class PostEntity extends BaseEntity{

    @Column(name = "title")
    private String title;
    @Column(columnDefinition = " text")
    private String content;

    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;
    @Column(name = "price")
    private Long price;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_type")
    private PaymentType paymentType;
    private CurrencyType currencyType;

    @Enumerated(EnumType.STRING)
    @Column(name = "condition")
    private PostCondition condition;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    @JoinColumn(name = "profile_Id")
    private ProfileEntity profile;

    @Column(name = "owner_phone_number")
    private String ownerPhoneNumber;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    private RegionEntity region;

    @Column(name = "status")
    private PostStatus status;
}
