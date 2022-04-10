package com.company.controller;

import com.company.dto.ProfileDTO;
import com.company.dto.auth.AuthorizationDTO;
import com.company.dto.auth.RegistrationDto;
import com.company.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @PostMapping("/account/register")
    public ResponseEntity<?> registration(@RequestBody RegistrationDto dto){
        authService.registration(dto);
        return ResponseEntity.ok("Successfully!!!");
    }
    @GetMapping("/account/verification/{jwt}")
    public ResponseEntity<?> verification(@PathVariable("jwt") String jwt){
        authService.verification(jwt);
        return ResponseEntity.ok("Success");
    }
     @PostMapping("/account/login")
    public ResponseEntity<ProfileDTO> authorization(@RequestBody AuthorizationDTO dto){
        ProfileDTO profileDTO=authService.authorization(dto);
        return ResponseEntity.ok(profileDTO);
     }
}
