package com.sl.foodorderingsystem.utils;

import com.sl.foodorderingsystem.entity.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
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

    public void forgetPasswordMail(String toEmail , String subject ,String password) throws MessagingException {

        MimeMessage message= mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true);

        helper.setFrom(fromEmail);
        helper.setTo(toEmail);
        helper.setSubject(subject);

        String msg= "<p><b> Your Login details for Cafe Management System </b><br>" +
                "<b> Email : "+toEmail+" <br> <b> Password :"+password+"<br> <a href= \"http://localhost:4200\">click here to login</a></p>";
        message.setContent(msg,"text/html");
        mailSender.send(message);
    }

}
