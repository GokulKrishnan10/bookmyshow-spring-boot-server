package com.scheduler.server.webapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.scheduler.server.webapp.entity.RowLock;

@Repository
public interface RowLockRepository extends JpaRepository<RowLock, Long> {

    // List<SysConfig> findById(Long id);

    @Modifying
    @Query(value = "insert into transactions.row_locks(job_id,created_at) values(?1,now()) on conflict(job_id) do nothing", nativeQuery = true)
    void lockRecord(Long jobId);

    List<RowLock> findAll();

    RowLock findByJobId(Long jobId);

    @Modifying
    void deleteByJobId(Long jobId);

}
