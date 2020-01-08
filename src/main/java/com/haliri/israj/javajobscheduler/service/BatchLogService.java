package com.haliri.israj.javajobscheduler.service;

import com.haliri.israj.javajobscheduler.entity.BatchLog;
import com.haliri.israj.javajobscheduler.enumeration.State;
import com.haliri.israj.javajobscheduler.enumeration.TaskType;
import com.haliri.israj.javajobscheduler.repository.BatchLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class BatchLogService {

    @Autowired
    private BatchLogRepository batchLogRepository;

    public int countBatchLog(){
        if (batchLogRepository.findAll().size() < 1){
            BatchLog batchLog = new BatchLog();
            batchLog.setId(UUID.randomUUID());
            batchLog.setDateCreated(new Date());
            batchLog.setStatus(State.DONE);
            batchLog.setTaskType(TaskType.BIFF_BUZZ);

            batchLogRepository.save(batchLog);
        }

        return batchLogRepository.findAll().size();
    }
}
