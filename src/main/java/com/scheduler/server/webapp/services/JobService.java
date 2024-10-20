package com.scheduler.server.webapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scheduler.server.webapp.entity.Job;
import com.scheduler.server.webapp.entity.JobResult;
import com.scheduler.server.webapp.enums.JobStatus;
import com.scheduler.server.webapp.repository.JobRepository;
import com.scheduler.server.webapp.repository.JobResultRepository;

import jakarta.transaction.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.time.Duration;

@Service
public class JobService {
    @Autowired
    JobRepository jobRepository;

    @Autowired
    JobResultRepository jobResultRepository;

    @Transactional
    public void scheduleJob(Job job) {
        Job scheduledJob = Job.builder().jobName(job.getJobName()).scheduledAt(job.getScheduledAt()).build();
        jobRepository.save(scheduledJob);
    }

    public List<Job> getJobByTimeRange() {
        long currentTimeMillis = System.currentTimeMillis();
        Timestamp starTimestamp = new Timestamp(currentTimeMillis - (long) (5 * 60 * 1000));
        Timestamp endTimestamp = Timestamp.from(Instant.now());
        List<Job> jobs = jobRepository.findAllByScheduledAtInRange(starTimestamp, endTimestamp);
        return jobs;
    }

    public List<Job> getAll() {
        return jobRepository.findAll();
    }

    @Transactional
    public void deleteById(Long id) {
        jobRepository.deleteById(id);
    }

    @Transactional
    public void insertSuccess(Job job, JobStatus status) {
        JobResult result = JobResult.builder().error("No error").jobStatus(status).jobName(job.getJobName())
                .scheduledAt(job.getScheduledAt())
                .executionTime("null")
                .executedAt(Timestamp.from(Instant.now())).build();
        jobResultRepository.save(result);
    }
}
