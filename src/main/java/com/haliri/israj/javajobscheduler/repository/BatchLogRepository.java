package com.haliri.israj.javajobscheduler.repository;

import com.haliri.israj.javajobscheduler.entity.BatchLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BatchLogRepository extends JpaRepository<BatchLog, String> {
}
