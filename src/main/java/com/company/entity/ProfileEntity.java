package com.company.entity;

import com.company.enums.ProfileRole;
import com.company.enums.ProfileStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table( name = "profile")
public class ProfileEntity extends BaseEntity{


    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "password")
    private String password;

    @Column(unique = true,name = "email")
    private String email;

    @Column(unique = true,name = "phone")
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private ProfileRole role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false,name = "status")
    private ProfileStatus status;

    @Column(name = "last_active_date")
    private LocalDateTime lastActiveDate;

    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @OneToMany(mappedBy = "profile")
    private List<PostEntity> posts;

}
