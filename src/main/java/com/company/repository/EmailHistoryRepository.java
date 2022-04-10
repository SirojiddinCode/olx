package com.company.repository;

import com.company.entity.EmailHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmailHistoryRepository extends JpaRepository<EmailHistoryEntity,Integer> {


    List<EmailHistoryEntity> findAllByToAccountOrderByCreatedDateDesc(String toAccount);
}
