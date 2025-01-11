package com.directory.report.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "REPORT")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private Date requestedAt;

    @Enumerated(EnumType.STRING)
    private ReportStatus status;

    @Lob
    private String content;

}
