package com.directory.report.service;

import com.directory.report.model.Report;

import java.util.List;
import java.util.UUID;

public interface ReportService {

    Report createReport();

    List<Report> getAllReports();

    Report getReportById(UUID id);
}
