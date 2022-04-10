package com.company.repository;

import com.company.entity.RegionEntity;
import com.company.enums.RegionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RegionRepository extends JpaRepository<RegionEntity,Integer>, JpaSpecificationExecutor<RegionEntity> {
    @Query("select r from RegionEntity r where r.name_ru like ?1")
    List<RegionEntity> findByName_ruLike(String name);

    @Query("select r from RegionEntity r where r.name_uz like ?1")
    List<RegionEntity> findByName_uzLike(String name);

    List<RegionEntity> findByIdAndParent(Integer id,RegionEntity parent);

    @Query("select r from RegionEntity r where r.parent is null and r.type=?1")
    List<RegionEntity> findAllParent(RegionType type);

    List<RegionEntity> findAllByParent(RegionEntity parent);
}
