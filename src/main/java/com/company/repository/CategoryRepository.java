package com.company.repository;

import com.company.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<CategoryEntity,Integer>, JpaSpecificationExecutor<CategoryEntity> {

    @Query("select c from CategoryEntity c where c.parent.id is null")
    List<CategoryEntity> getParent();

    List<CategoryEntity> findAllByParent(CategoryEntity parent);
}
