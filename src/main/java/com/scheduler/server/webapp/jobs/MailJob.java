package com.scheduler.server.webapp.jobs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.google.gson.JsonObject;
import com.scheduler.server.webapp.enums.JobType;

@Component
public class MailJob implements ScheduledJob {

    JsonObject params;

    @Value("${spring.mail.username}")
    private String sender;

    @Override
    public void initialize(JsonObject params) {
        this.params = params;
    }

    private JavaMailSender mailSender;

    private void sendSimpleMail() {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(sender);
            mailMessage.setTo("gokulphone1010@gmail.com");
            mailMessage.setText("Spring Boot Mail test");
            mailMessage.setSubject("This is just a sample mail from the Spring boot App");
            mailSender.send(mailMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Autowired
    public MailJob(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public String executeJob() {
        sendSimpleMail();
        return "Mail Send successfully";
    }

    @Override
    public JobType getJobType() {
        return JobType.SEND_EMAIL;
    }

}
