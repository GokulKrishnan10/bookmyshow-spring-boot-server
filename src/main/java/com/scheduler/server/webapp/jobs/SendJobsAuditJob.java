package com.scheduler.server.webapp.jobs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
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

    public final String fileName = "job-audits.csv";
    // String.format("job-audits.csv", Instant.now().toString());

    @Autowired
    JobService service;

    @Override
    public String executeJob() throws Exception {
        List<JobResult> list = this.service.getAllAudits();
        try (CSVWriter writer = new CSVWriter(new FileWriter(fileName))) {
            String[] headers = new String[] { "id", "job_name", "job_status", "created_at", "scheduled_at", "error",
                    "exported_at" };
            writer.writeNext(headers);
            list.forEach(record -> {

                String[] csvRecord = new String[] {
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
            this.zipAndSendTheFile("job-audits.csv");
            String json = "{\"toAddrs\":\"gokulkrish.elangovan10@gmail.com\",\"subject\":\"Monthly Job Audits Export %s\",\"content\":\"Please find the below attached audits for the Job table.\nThanks & Regards,\nSpring boot app.\",\"has_attachment\":true,\"fileName\":\"audits.zip\"}";
            json = String.format(json, Timestamp.from(Instant.now()).toString());
            this.service.scheduleJob(Job.builder().jobName(JobType.SEND_EMAIL)
                    .params(json)
                    .scheduledAt(Timestamp.from(Instant.now().plus(Duration.ofSeconds(10)))).build());
        } catch (Exception e) {

        }
        return getJobType().name() + " executed successfully";
    }

    public void zipAndSendTheFile(String sourceFile) throws Exception {
        FileOutputStream fos = new FileOutputStream("audits.zip");
        ZipOutputStream zip = new ZipOutputStream(fos);
        File file = new File(sourceFile);
        FileInputStream fis = new FileInputStream(file);
        ZipEntry zipEntry = new ZipEntry(file.getName());
        zip.putNextEntry(zipEntry);
        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zip.write(bytes, 0, length);
        }
        zip.close();
        fis.close();
    }

    @Override
    public JobType getJobType() {
        return JobType.EXPORT_JOBS_AUDIT_TO_CSV;
    }

}
