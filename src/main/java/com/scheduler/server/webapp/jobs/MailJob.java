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

import com.scheduler.server.webapp.enums.JobStatus;
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

    @Autowired
    public MailJob(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    private void sendSimpleMail() throws Exception {
        Boolean hasAttachMent = Boolean.valueOf(params.get("has_attachment").getAsString());
        if (hasAttachMent) {
            this.sendMailWithAttachment();
            return;
        }
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(sender);
        mailMessage.setTo(params.get("toAddrs").getAsString());
        mailMessage.setText(params.get("content").getAsString());
        mailMessage.setCc(params.get("cc").getAsString());
        mailMessage.setSubject(params.get("subject").getAsString());
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
        this.service.deleteJob(this.getParams().get("jobId").getAsLong());
        return "Mail Send successfully";
    }

    @Override
    public JobType getJobType() {
        return JobType.SEND_EMAIL;
    }

}
