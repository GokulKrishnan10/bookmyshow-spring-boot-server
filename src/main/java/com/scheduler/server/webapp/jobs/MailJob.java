package com.scheduler.server.webapp.jobs;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

import com.scheduler.server.webapp.enums.JobType;
import com.scheduler.server.webapp.jobs.defns.ScheduledJob;
import com.scheduler.server.webapp.services.JobService;

import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;

@Component
public class MailJob extends ScheduledJob {

    @Value("${spring.mail.username}")
    private String sender;

    private JavaMailSender mailSender;

    @Autowired
    private JobService service;

    public MailJob(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    private void sendSimpleMail() throws Exception {
        Boolean hasAttachMent = Boolean.valueOf(getParams().get("has_attachment").getAsString());
        if (hasAttachMent) {
            this.sendMailWithAttachment();
            return;
        }
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(sender);
        mailMessage.setTo(getParams().get("toAddrs").getAsString());
        mailMessage.setText(getParams().get("content").getAsString());
        mailMessage.setCc(getParams().get("cc").getAsString());
        mailMessage.setSubject(getParams().get("subject").getAsString());
        mailSender.send(mailMessage);
    }

    public void sendMailWithAttachment() {
        MimeMessagePreparator preparator = mimemessage -> {
            mimemessage.setRecipient(Message.RecipientType.TO,
                    new InternetAddress(this.getParams().get("toAddrs").getAsString()));
            mimemessage.setFrom(this.sender);
            mimemessage.setText(this.getParams().get("content").getAsString());
            mimemessage.setSubject(this.getParams().get("subject").getAsString());

            FileSystemResource resource = new FileSystemResource(
                    new File(this.getParams().get("fileName").getAsString()));
            MimeMessageHelper helper = new MimeMessageHelper(mimemessage, true);
            if (resource.exists()) {
                helper.addAttachment(this.getParams().get("fileName").getAsString(), resource);
                helper.setText(this.getParams().get("content").getAsString(), true);
            } else {
                System.out.println("File Not Found");
                return;
            }

        };

        mailSender.send(preparator);
    }

    @Override
    public String executeJob() throws Exception {
        System.out.println("Mail Send successfully " + this.getParams().get("jobId").getAsLong());
        sendSimpleMail();
        System.out.println("Mail Send successfully " + this.getParams().get("jobId").getAsLong());
        // this.service.deleteJob(this.getParams().get("jobId").getAsLong());
        return "Mail Send successfully";
    }

    @Override
    public void validate() {
        if (this.getParams().get("toAddrs") == null || this.getParams().get("toAddrs").getAsString().isEmpty()) {
            throw new IllegalStateException("toAddrs parameter is required.");
        }
        if (this.getParams().get("subject") == null || this.getParams().get("subject").getAsString().isEmpty()) {
            throw new IllegalStateException("subject parameter is required.");
        }
        if (this.getParams().get("content") == null || this.getParams().get("content").getAsString().isEmpty()) {
            throw new IllegalStateException("content parameter is required.");
        }
        if (this.getParams().get("subject") == null || this.getParams().get("subject").getAsString().isEmpty()) {
            throw new IllegalStateException("subject parameter is required.");
        }
    }

    @Override
    public JobType getJobType() {
        return JobType.SEND_EMAIL;
    }

}
