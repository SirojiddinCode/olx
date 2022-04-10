package com.company.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "attach")
public class AttachEntity extends BaseEntity{

    @Column(name = "key")
    private String key; //UUID
    @Column(name = "size")
    private Long size;
    @Column(name = "file_path")
    private String filePath;
    @Column(name = "extension")
    private String extension;
    @Column(name = "origin_name")
    private String originName;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToOne(mappedBy = "attach")
    private PostAttachEntity postAttach;
}
