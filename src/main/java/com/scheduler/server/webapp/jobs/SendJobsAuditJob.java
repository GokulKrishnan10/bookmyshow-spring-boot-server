package com.scheduler.server.webapp.jobs;

import java.io.FileWriter;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.JsonObject;
import com.opencsv.CSVWriter;
import com.scheduler.server.webapp.entity.Job;
import com.scheduler.server.webapp.entity.JobResult;
import com.scheduler.server.webapp.enums.JobType;
import com.scheduler.server.webapp.services.JobService;

@Component
public class SendJobsAuditJob implements ScheduledJob {

    public final String fileName = "job-audits.csv";
    // String.format("job-audits.csv", Instant.now().toString());

    JsonObject params;

    @Autowired
    JobService service;

    @Override
    public void initialize(JsonObject params) {
        this.params = params;
    }

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
            String json = "{\"toAddrs\":\"gokulkrish.elangovan10@gmail.com\",\"subject\":\"Monthly Job Audits Export\",\"content\":\"Please find the below attached of audits table.\",\"has_attachment\":true,\"fileName\":\"job-audits.csv\"}";
            System.out.println("Json striing " + json);
            this.service.scheduleJob(Job.builder().jobName(JobType.SEND_EMAIL)
                    .params(json)
                    .scheduledAt(Timestamp.from(Instant.now().plus(Duration.ofSeconds(10)))).build());
        } catch (Exception e) {

        }
        return getJobType().name() + " executed successfully";
    }

    @Override
    public JobType getJobType() {
        return JobType.EXPORT_JOBS_AUDIT_TO_CSV;
    }

}
