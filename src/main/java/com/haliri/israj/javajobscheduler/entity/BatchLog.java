package com.haliri.israj.javajobscheduler.entity;

import com.haliri.israj.javajobscheduler.enumeration.State;
import com.haliri.israj.javajobscheduler.enumeration.TaskType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "BATCH_LOG")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BatchLog {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "ID", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "TASK")
    @Enumerated(EnumType.STRING)
    private TaskType taskType;

    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private State status;

    @Column(name = "DATE_CREATED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;
}
