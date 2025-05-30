package com.loontao.utilityservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Value("${app.website.url}") // This property should be defined in your application.properties
    private String companyWebsiteUrl;
    
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private TemplateEngine templateEngine;


    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Async
    public void sendVerificationEmail(String toEmail, String verificationUrl, String userName, int expiryTimeInMinutes) {
        try {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        // Use MimeMessageHelper to set details and indicate HTML content
        // "UTF-8" is a good encoding for emails
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, "UTF-8");

        // Prepare the Thymeleaf context
        Context context = new Context();
        context.setVariable("userName", userName); // This will be used in th:text="${name}"
        context.setVariable("verificationUrl", verificationUrl); // Used in th:href="${verificationUrl}"
        context.setVariable("appName", "LoonTao"); // You can set the app name or any other variable you need
        context.setVariable("expirationTimeInMinutes", expiryTimeInMinutes); // Used in th:text="${expiryTime} minutes"
        context.setVariable("companyWebsiteUrl", companyWebsiteUrl); // Used in th:href="${companyWebsiteUrl}"
        // Process the HTML template with the context variables
        // "mail/verificationEmail" is the path to your template under src/main/resources/templates/
        String htmlContent = templateEngine.process("mail/verificationEmail", context);

        helper.setFrom("noreply.loontao@gmail.com");
        helper.setTo(toEmail);
        helper.setSubject("LoonTao: Please Verify Your Account & Email!!");
        helper.setText(htmlContent, true); // true indicates that the text is HTML
        javaMailSender.send(mimeMessage);
        System.out.println("Verification email sent to: " + toEmail);
        
        } catch (Exception e) {
            // Log the exception or handle it as needed
            System.err.println("Error sending verification email: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

