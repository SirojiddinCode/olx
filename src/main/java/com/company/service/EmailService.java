package com.company.service;

import com.company.dto.Messagedto;
import com.company.entity.EmailHistoryEntity;
import com.company.enums.MessageStatus;
import com.company.exceptions.BadRequestException;
import com.company.repository.EmailHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private EmailHistoryRepository emailHistoryRepository;

    public Messagedto sendEmail(Messagedto messagedto){
        SimpleMailMessage message=new SimpleMailMessage();
        message.setTo(messagedto.getToAccount());
        message.setText(messagedto.getContent());
        message.setSubject(messagedto.getTitle());
        mailSender.send(message);
        return messagedto;
    }
    public void saveMessage(Messagedto messagedto) {
        EmailHistoryEntity entity=new EmailHistoryEntity();
        entity.setTitle(messagedto.getTitle());
        entity.setContent(messagedto.getContent());
        entity.setToAccount(messagedto.getToAccount());
        entity.setCreatedDate(messagedto.getCreatedDate());
        entity.setFromAccount(messagedto.getFromAccount());
        entity.setStatus(messagedto.getStatus());

        emailHistoryRepository.save(entity);
    }

    public void setUsed(String email,String jwt){
        List<EmailHistoryEntity> entityList=emailHistoryRepository
                .findAllByToAccountOrderByCreatedDateDesc(email);
        EmailHistoryEntity entity= entityList.get(0);
        String[] arr=entity.getContent().split("/");
        if (arr[arr.length-1].equals(jwt)){
           entity.setStatus(MessageStatus.USED);
           emailHistoryRepository.save(entity);
        }else {
            throw new BadRequestException("Not Found");
        }
    }

}
