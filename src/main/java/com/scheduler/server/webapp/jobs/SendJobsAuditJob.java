package com.scheduler.server.webapp.jobs;

import java.io.*;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.opencsv.CSVWriter;
import com.scheduler.server.webapp.entity.Job;
import com.scheduler.server.webapp.entity.JobResult;
import com.scheduler.server.webapp.enums.JobType;
import com.scheduler.server.webapp.jobs.defns.ScheduledJob;
import com.scheduler.server.webapp.services.JobService;

@Component
public class SendJobsAuditJob extends ScheduledJob {

    private static final String FILE_NAME = "job-audits.csv";
    private static final String ZIP_FILE_NAME = "audits.zip";
    private static final String EMAIL_JSON_TEMPLATE = "{\"toAddrs\":\"gokulkrish.elangovan10@gmail.com\",\"subject\":\"Monthly Job Audits Export %s\",\"content\":\"Please find the below attached audits for the Job table.\nThanks & Regards,\nSpring boot app.\",\"has_attachment\":true,\"fileName\":\"audits.zip\"}";

    @Autowired
    private JobService service;

    @Override
    public String executeJob() throws Exception {
        List<JobResult> list = service.getAllAudits();
        try (CSVWriter writer = new CSVWriter(new FileWriter(FILE_NAME))) {
            writeCsvHeader(writer);
            writeCsvRecords(writer, list);
            zipAndSendTheFile(FILE_NAME);
            scheduleEmailJob();
        } catch (Exception e) {
            // Log the exception
        }
        return getJobType().name() + " executed successfully";
    }

    private void writeCsvHeader(CSVWriter writer) {
        String[] headers = { "id", "job_name", "job_status", "created_at", "scheduled_at", "error", "exported_at" };
        writer.writeNext(headers);
    }

    private void writeCsvRecords(CSVWriter writer, List<JobResult> list) {
        list.forEach(record -> {
            String[] csvRecord = {
                    record.getId().toString(),
                    record.getJobName().name(),
                    record.getJobStatus().name(),
                    record.getCreatedAt().toString(),
                    record.getScheduledAt().toString(),
                    record.getError(),
                    Timestamp.from(Instant.now()).toString()
            };
            writer.writeNext(csvRecord);
        });
    }

    private void zipAndSendTheFile(String sourceFile) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(ZIP_FILE_NAME);
                ZipOutputStream zip = new ZipOutputStream(fos);
                FileInputStream fis = new FileInputStream(new File(sourceFile))) {

            ZipEntry zipEntry = new ZipEntry(sourceFile);
            zip.putNextEntry(zipEntry);

            byte[] bytes = new byte[1024];
            int length;
            while ((length = fis.read(bytes)) >= 0) {
                zip.write(bytes, 0, length);
            }
        }
    }

    private void scheduleEmailJob() {
        String json = String.format(EMAIL_JSON_TEMPLATE, Timestamp.from(Instant.now()).toString());
        Job emailJob = Job.builder()
                .jobName(JobType.SEND_EMAIL)
                .params(json)
                .scheduledAt(Timestamp.from(Instant.now().plus(Duration.ofSeconds(10))))
                .build();
        service.scheduleJob(emailJob);
    }

    @Override
    public JobType getJobType() {
        return JobType.EXPORT_JOBS_AUDIT_TO_CSV;
    }
}
