package com.company.repository;

import com.company.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PostRepository extends JpaRepository<PostEntity, Integer>, JpaSpecificationExecutor<PostEntity> {

}
