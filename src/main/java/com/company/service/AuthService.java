package com.company.service;

import com.company.dto.Messagedto;
import com.company.dto.ProfileDTO;
import com.company.dto.auth.AuthorizationDTO;
import com.company.dto.auth.RegistrationDto;
import com.company.entity.ProfileEntity;
import com.company.enums.MessageStatus;
import com.company.enums.ProfileRole;
import com.company.enums.ProfileStatus;
import com.company.exceptions.BadRequestException;
import com.company.exceptions.ItemNotFoundException;
import com.company.repository.ProfileRepository;
import com.company.util.JwtUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private EmailService emailService;


    public void registration(RegistrationDto dto) throws BadRequestException {
        if (profileRepository.existsByEmail(dto.getEmail())) {
            throw new BadRequestException("Email address already exists");
        }
        ProfileEntity entity = new ProfileEntity();
        entity.setEmail(dto.getEmail());
        BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();
        String pswd = bCrypt.encode(dto.getPassword());
        entity.setPassword(pswd);
        entity.setCreatedDate(LocalDateTime.now());
        entity.setStatus(ProfileStatus.CREATED);
        entity.setRole(ProfileRole.User);
        profileRepository.save(entity);
        Messagedto messagedto = new Messagedto();
        messagedto.setCreatedDate(LocalDateTime.now());
        messagedto.setToAccount(dto.getEmail());
        messagedto.setFromAccount("sirojiddinmamatqulov0228@gmail.com");
        String jwt = JwtUtil.createJwtById(entity.getId());
        StringBuilder builder = new StringBuilder();
        builder.append("Salom jigar Qalaysan\n");
        builder.append("Agar bu sen bo'lsang shu linkga bos: ");
        builder.append("http://localhost:8080/auth/account/verification/" + jwt);
        String title = "Olx.Uz verification";
        messagedto.setTitle(title);
        messagedto.setContent(builder.toString());
        messagedto.setStatus(MessageStatus.NOTUSED);
        emailService.sendEmail(messagedto);
        emailService.saveMessage(messagedto);
    }

    public void verification(String jwt) {
        Integer id = JwtUtil.decodeJwtAndGetId(jwt);
        ProfileEntity profile = profileRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("User not found"));
        emailService.setUsed(profile.getEmail(), jwt);
        profile.setStatus(ProfileStatus.ACTIVE);
        profileRepository.save(profile);
    }

    public ProfileDTO authorization(AuthorizationDTO dto) {
        Optional<ProfileEntity> optionalProfile = profileRepository.findByEmail(dto.getEmail());
        if (optionalProfile.isEmpty()) {
            throw new ItemNotFoundException("User Not Found");
        } else {
            ProfileEntity entity = optionalProfile.get();
            BCryptPasswordEncoder bCrypt=new BCryptPasswordEncoder();
            if (bCrypt.matches(dto.getPassword(),entity.getPassword())) {
                ProfileDTO profileDTO = new ProfileDTO();
                profileDTO.setEmail(entity.getEmail());
                profileDTO.setPswd(entity.getPassword());
                profileDTO.setId(entity.getId());
                profileDTO.setJwtTooken(JwtUtil.createJwt(entity.getId(), entity.getRole()));
                return profileDTO;
            } else throw new BadRequestException("Password is invalid");
        }
    }
}
