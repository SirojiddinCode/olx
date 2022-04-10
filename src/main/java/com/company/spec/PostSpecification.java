package com.company.spec;

import com.company.entity.CategoryEntity;
import com.company.entity.PostEntity;
import com.company.entity.RegionEntity;
import com.company.enums.PostCondition;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.script.ScriptEngine;
import java.util.List;

public class PostSpecification {
    public static Specification<PostEntity>getbyCategory(CategoryEntity category){
       return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("category"),category));
    }
    public static  Specification<PostEntity> getByAllCategory(List<CategoryEntity> categoryEntityList){
        return ((root, query, criteriaBuilder) -> {
           CriteriaBuilder.In<CategoryEntity> in= criteriaBuilder.in(root.get("category"));
           categoryEntityList.forEach(in::value);
           return in;
        });
    }
    public static Specification<PostEntity> isnotNull(){
        return ((root, query, criteriaBuilder) -> criteriaBuilder.isNotNull(root.get("id")));
    }
    public static Specification<PostEntity> getByRegion(RegionEntity region){
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("region"),region));
    }
    public static Specification<PostEntity> getByAllRegion(List<RegionEntity> regionList){
        return ((root, query, criteriaBuilder) -> {
            CriteriaBuilder.In<RegionEntity> in= criteriaBuilder.in(root.get("region"));
            regionList.forEach(in::value);
            return in;
        });
    }
    public static Specification<PostEntity> getByCondition(PostCondition condition){
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("condition"),condition);
    }
    public static Specification<PostEntity> fromPrice(Long price){
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThan(root.get("price"),price);
    }
    public static Specification<PostEntity> toPrice(Long price){
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThan(root.get("price"),price);
    }

}
