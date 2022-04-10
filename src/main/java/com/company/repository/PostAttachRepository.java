package com.company.repository;

import com.company.entity.PostAttachEntity;
import com.company.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostAttachRepository extends JpaRepository<PostAttachEntity,Integer> {

    List<PostAttachEntity> findAllByPost(PostEntity post);
}
