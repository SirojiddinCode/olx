package com.company.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
@Getter
@Setter
@Entity
@Table
public class PostAttachEntity extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private PostEntity post;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attach_id")
    private AttachEntity attach;

}
