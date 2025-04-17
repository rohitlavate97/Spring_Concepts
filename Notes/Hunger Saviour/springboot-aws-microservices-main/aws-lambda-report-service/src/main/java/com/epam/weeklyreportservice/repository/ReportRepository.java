package com.epam.weeklyreportservice.repository;

import com.epam.weeklyreportservice.collections.Orders;
import software.amazon.awssdk.enhanced.dynamodb.model.PageIterable;
import java.util.Optional;


public interface ReportRepository {
    Optional<PageIterable<Orders>> getAllReports();
}
