package com.company.service;

import com.company.entity.ProfileEntity;
import com.company.exceptions.ItemNotFoundException;
import com.company.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;

    public ProfileEntity get(Integer id){
        return profileRepository.findById(id).orElseThrow(()->new ItemNotFoundException("Profile not found"));
    }

}
