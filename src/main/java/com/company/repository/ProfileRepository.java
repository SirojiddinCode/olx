package com.company.repository;

import com.company.entity.ProfileEntity;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<ProfileEntity,Integer> ,
        JpaSpecificationExecutor<ProfileEntity> {

    boolean existsByEmail(String email);

    Optional<ProfileEntity> findByEmail(String email);
}
