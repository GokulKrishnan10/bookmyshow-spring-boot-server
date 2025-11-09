package com.scheduler.server.webapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scheduler.server.webapp.entity.Job;
import com.scheduler.server.webapp.entity.JobResult;
import com.scheduler.server.webapp.enums.JobStatus;
import com.scheduler.server.webapp.repository.JobRepository;
import com.scheduler.server.webapp.repository.JobResultRepository;

import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Service
public class JobService {
    @Autowired
    JobRepository jobRepository;

    @Autowired
    JobResultRepository jobResultRepository;

    @Transactional
    public void scheduleJob(Job job) {
        jobRepository.save(job);
    }

    public List<Job> getJobByTimeRange() {
        long currentTimeMillis = System.currentTimeMillis();
        Timestamp starTimestamp = new Timestamp(currentTimeMillis - (long) (5 * 60 * 1000));
        Timestamp endTimestamp = Timestamp.from(Instant.now());
        List<Job> jobs = jobRepository.findAllByScheduledAtInRange(starTimestamp, endTimestamp);
        return jobs;
    }

    public void save(Job job) {
        jobRepository.save(job);
    }

    public List<Job> getAll() {
        return jobRepository.findAll();
    }

    public List<JobResult> getAllAudits() {
        return jobResultRepository.findAll();
    }

    @Transactional
    public void saveStatus(Long id, JobStatus status) {
        jobRepository.lockRecord(id);
        jobRepository.updateJobStatus(status, id);
    }

    @Transactional
    public void deleteJob(Long id) {
        jobRepository.lockRecord(id);
        jobRepository.deleteById(id);
    }

    @Transactional
    public void deleteCompletedJobs() {
        jobRepository.deleteAllSuccessJobs();
    }

    public void insertSuccess(Job job) {
        JobResult result = JobResult.builder().error("No error").jobStatus(JobStatus.SUCCESS).jobName(job.getJobName())
                .scheduledAt(job.getScheduledAt())
                .executionTime("null")
                .executedAt(Timestamp.from(Instant.now())).build();
        jobResultRepository.save(result);
    }

    public void insertFailedStatus(Job job, String exString) {
        JobResult result = JobResult.builder().error(exString).jobStatus(JobStatus.FAILED).jobName(job.getJobName())
                .scheduledAt(job.getScheduledAt())
                .executionTime("null")
                .executedAt(Timestamp.from(Instant.now())).build();
        jobResultRepository.save(result);
    }
}
