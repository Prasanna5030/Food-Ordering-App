package com.sl.foodorderingsystem.utils;

import com.sl.foodorderingsystem.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailUtils {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public void sendEmail(String to, String subject, String text , List<User> admins) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        if(!admins.isEmpty())
            message.setCc(getCCList(admins));

        mailSender.send(message);
    }

    private String[] getCCList(List<User> admins) {
        String[] ccList= new String[admins.size()];
        for(int i=0; i<admins.size(); i++){
            ccList[i] = admins.get(i).getEmail();
        }
        return ccList;
    }
}
