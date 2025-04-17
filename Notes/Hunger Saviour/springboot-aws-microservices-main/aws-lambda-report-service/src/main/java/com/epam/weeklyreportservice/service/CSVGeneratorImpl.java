package com.epam.weeklyreportservice.service;

import com.epam.weeklyreportservice.repository.ReportRepository;
import io.awspring.cloud.s3.ObjectMetadata;
import io.awspring.cloud.s3.S3Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Map;

@Service
public class CSVGeneratorImpl implements CSVGenerator {
    @Autowired
    private ReportRepository repository;
    @Autowired
    private S3Template s3Template;

    @Override
    public void generateCSV() throws IOException {
        System.out.println("generated csv");

        repository.getAllReports().get().iterator().forEachRemaining(System.out::println);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        OutputStreamWriter writer = new OutputStreamWriter(baos);
        writer.write("Username,PaymentId,TotalPrice\n");
        repository.getAllReports().ifPresent(
                reports -> {
                    reports.items()
                            .stream()
                            .forEach(
                                    report -> {
                                        try {
                                            writer.write(report.getUsername() + " , " + report.getPaymentId() + " , " + report.getTotalPrice() + "\n");
                                        } catch (IOException e) {
                                            throw new RuntimeException(e);
                                        }
                                    }
                            );
                }
        );
        writer.close();
        byte[] csvBytes = baos.toByteArray();
        ByteArrayInputStream csvInputStream = new ByteArrayInputStream(csvBytes);
        s3Template.upload("reports-s3","Orders_" + Instant.now(),csvInputStream,ObjectMetadata.builder().contentType("text/plain").build());
    }
}
