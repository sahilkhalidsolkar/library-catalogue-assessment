package com.example.libraryBookCatalogue.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.HashMap;

@Service
@AllArgsConstructor
public class EmailService {
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    public void sendThymeLeafEmail(String sendEmailTo,String subject,String body,String recepientName,String senderName) {
        Context context = new Context();
        HashMap<String, Object> contextData = new HashMap<>();
        contextData.put("sendEmailTo", sendEmailTo);
        contextData.put("subject", subject);
        contextData.put("body", body);
        contextData.put("recepientName", recepientName);
        contextData.put("senderName", senderName);


        context.setVariables(contextData);
        String html = templateEngine.process("emailTemplate", context);
        System.out.println(html);
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            mimeMessageHelper.setTo(sendEmailTo);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(html,true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }


    }
}
