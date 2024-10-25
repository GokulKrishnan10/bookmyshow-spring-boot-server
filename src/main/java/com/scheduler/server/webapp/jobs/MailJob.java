package com.scheduler.server.webapp.jobs;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.eclipse.angus.mail.handlers.multipart_mixed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties.Io;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

import com.google.gson.JsonObject;
import com.scheduler.server.webapp.enums.JobType;
import com.scheduler.server.webapp.exception.AppException;

import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeUtility;

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

    @Autowired
    public MailJob(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    private void sendSimpleMail() throws Exception {
        Boolean hasAttachMent = Boolean.valueOf(params.get("has_attachment").getAsString());
        System.out.println("Mail has attachment or not " + hasAttachMent);
        if (hasAttachMent) {
            this.sendMailWithAttachment();
            return;
        }
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(sender);
            mailMessage.setTo(params.get("toAddrs").getAsString());
            mailMessage.setText(params.get("content").getAsString());
            mailMessage.setCc(params.get("cc").getAsString());
            mailMessage.setSubject(params.get("subject").getAsString());
            mailSender.send(mailMessage);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public void sendMailWithAttachment() {
        MimeMessagePreparator preparator = mimemessage -> {
            mimemessage.setRecipient(Message.RecipientType.TO,
                    new InternetAddress(this.params.get("toAddrs").getAsString()));
            mimemessage.setFrom(this.sender);
            mimemessage.setText(this.params.get("content").getAsString());
            mimemessage.setSubject(this.params.get("subject").getAsString());

            FileSystemResource resource = new FileSystemResource(new File(this.params.get("fileName").getAsString()));
            MimeMessageHelper helper = new MimeMessageHelper(mimemessage, true);
            if (resource.exists()) {
                helper.addAttachment("job-audits.csv", resource);
                helper.setText("", true);
            } else {
                System.out.println("File Not Found");
                return;
            }

        };

        try {
            mailSender.send(preparator);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String executeJob() throws Exception {
        sendSimpleMail();
        return "Mail Send successfully";
    }

    @Override
    public JobType getJobType() {
        return JobType.SEND_EMAIL;
    }

}
