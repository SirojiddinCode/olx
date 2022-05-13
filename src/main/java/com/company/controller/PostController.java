package com.company.controller;


import com.company.dto.ProfileJwtDto;
import com.company.dto.post.PostDTO;
import com.company.enums.ProfileRole;
import com.company.service.PostService;
import com.company.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


@RestController
@RequestMapping("/post")
public class PostController {
    @Autowired
    private PostService postService;

    @PreAuthorize(value = "hasAnyRole('ADMIN','USER')")
    @PostMapping("/add_new_post")
    public ResponseEntity<PostDTO> createPost(@Valid @RequestBody PostDTO post, HttpServletRequest request) {
        return null;
    }
}
