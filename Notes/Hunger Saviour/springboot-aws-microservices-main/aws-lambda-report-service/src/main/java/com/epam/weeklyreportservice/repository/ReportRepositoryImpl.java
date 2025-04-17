package com.epam.weeklyreportservice.repository;

import com.epam.weeklyreportservice.collections.Orders;
import io.awspring.cloud.dynamodb.DynamoDbTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.model.PageIterable;

import java.util.Optional;


@Repository
public class ReportRepositoryImpl implements ReportRepository{

    @Autowired
    private DynamoDbTemplate dynamoDbTemplate;


    @Override
    public Optional<PageIterable<Orders>> getAllReports() {
        return Optional.of(dynamoDbTemplate.scanAll(Orders.class));
    }
}
