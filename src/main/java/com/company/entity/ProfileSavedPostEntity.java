package com.company.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table
@Getter
@Setter
public class ProfileSavedPostEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile")
    private ProfileEntity profile;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post")
    private PostEntity postEntity;

}
