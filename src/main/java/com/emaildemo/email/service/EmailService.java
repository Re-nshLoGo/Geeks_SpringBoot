package com.emaildemo.email.service;


import com.emaildemo.email.model.EmailTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.*;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.util.Properties;

@Service
public class EmailService {
    public Boolean send(EmailTemplate emailTemplate){
        Boolean flag = false;
        try {
            String fromAddress = emailTemplate.getFrom();
            String toAddress = emailTemplate.getTo();
            String subject = emailTemplate.getSubject();
            String message = emailTemplate.getMessage();
            String pass = "dwfuomlwhtgffvnl";
            Session session = getSesstion(fromAddress, pass);
            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(fromAddress);
            mimeMessage.addRecipients(Message.RecipientType.TO,toAddress);
            mimeMessage.setSubject(subject);
            mimeMessage.setText(message);
            Transport.send(mimeMessage);
            flag = true;
        }catch (Exception e){
           e.printStackTrace();
        }
        return flag;
    }

    public Boolean sendwithattach(EmailTemplate tempwithAttach,MultipartFile multipartFile) {
        Boolean flag = false;
        try{
            String fromAddress = tempwithAttach.getFrom();
            String toAddress = tempwithAttach.getTo();
            String subject = tempwithAttach.getSubject();
            String message = tempwithAttach.getMessage();
            String pass = "dwfuomlwhtgffvnl";
            Session session = getSesstion(fromAddress, pass);
            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(fromAddress);
            mimeMessage.addRecipients(Message.RecipientType.TO,toAddress);
            mimeMessage.setSubject(subject);
            MimeMultipart mimeMultipart = new MimeMultipart();
            MimeBodyPart bodytext = new MimeBodyPart();
            MimeBodyPart bodyattach = new MimeBodyPart();
            bodytext.setText(message);
            File file = new File("C:\\Users\\master\\ReenaSpringBoot\\sendingEmail\\src\\main\\resources\\static\\images");

            multipartFile.transferTo(file);
            bodyattach.attachFile(file);
            mimeMultipart.addBodyPart(bodytext);
            mimeMultipart.addBodyPart(bodyattach);
            mimeMessage.setContent(mimeMultipart);
            Transport.send(mimeMessage);
            flag = true;

        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    private Session getSesstion(String fromAddress, String pass) {
        Properties properties =  System.getProperties();
        properties.put("mail.smtp.host","smtp.gmail.com");
        properties.put("mail.smtp.port","465");
        properties.put("mail.smtp.ssl.enable","true");
        properties.put("mail.smtp.auth","true");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromAddress,pass);
            }
        });
        return session;
    }

}
