package com.myecom.gallery.gallery.Services.Impl;

import com.myecom.gallery.gallery.Services.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
@Service
public class MailServiceImpl implements MailService {
    @Autowired
  private JavaMailSender javaMailSender;


    @Override
    public void sendmail(String name, String email, String subject, String msg) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(msg);
        javaMailSender.send(simpleMailMessage);
    }

    @Override
    public boolean sendmail(String email, String subject, String msg) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(msg);
        javaMailSender.send(simpleMailMessage);
        return true;
    }
}
