package com.company.entity;

import com.company.enums.RegionType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "region")
public class RegionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name_uz;
    private String name_ru;
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_id")
    private RegionEntity parent;
    private RegionType type;

    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @OneToMany(mappedBy = "region")
    private List<PostEntity> postEntities;

}
