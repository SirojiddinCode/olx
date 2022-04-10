package com.company.controller;

import com.company.service.ProfileService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/profile")
@Api(tags = "Profile")
public class ProfileController {
    @Autowired
    private ProfileService profileService;
}
