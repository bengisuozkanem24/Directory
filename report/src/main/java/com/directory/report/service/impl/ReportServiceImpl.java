package com.directory.report.service.impl;

import com.directory.person.model.ContactInfo;
import com.directory.report.model.PersonServiceResponse;
import com.directory.report.model.Report;
import com.directory.report.model.ReportStatus;
import com.directory.report.repository.ReportRepository;
import com.directory.report.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    private final String personServiceUrl = "http://localhost:8080/api/person";

    @Override
    public Report createReport() {
        Report report = new Report();
        report.setRequestedAt(new Date());
        report.setStatus(ReportStatus.PENDING);

        Report savedReport = reportRepository.save(report);


        kafkaTemplate.send("report-requests", savedReport.getId().toString());

        return savedReport;
    }

    @Async
    public void processReport(UUID reportId) {
        RestTemplate restTemplate = new RestTemplate();

        try {
            PersonServiceResponse[] persons = restTemplate.getForObject(personServiceUrl, PersonServiceResponse[].class);

            if (persons != null) {
                Map<String, List<PersonServiceResponse>> groupedByLocation = Arrays.stream(persons)
                        .collect(Collectors.groupingBy(
                                person -> person.getContactInfoList().stream()
                                        .filter(contact -> contact.getContactType().equals("LOCATION"))
                                        .map(ContactInfo::getContent)
                                        .findFirst().orElse("Unknown")
                        ));

                StringBuilder reportContent = new StringBuilder();
                groupedByLocation.forEach((loc, people) -> {
                    long phoneCount = people.stream()
                            .flatMap(person -> person.getContactInfoList().stream())
                            .filter(contact -> contact.getContactType().equals("PHONE"))
                            .count();

                    reportContent.append(String.format("Location: %s\nPerson Count: %d\nPhone Count: %d\n\n",
                            loc, people.size(), phoneCount));
                });

                Report report = reportRepository.findById(reportId).orElseThrow(() -> new RuntimeException("Report not found"));
                report.setStatus(ReportStatus.COMPLETED);
                report.setContent(reportContent.toString());
                reportRepository.save(report);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error fetching data from Person Service", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Report getReportById(UUID id) {
        Optional<Report> reportOpt = reportRepository.findById(id);
        if(reportOpt.isPresent()) {
            return reportOpt.get();
        } else {
            throw new RuntimeException("Report not found.");
        }
    }

}
