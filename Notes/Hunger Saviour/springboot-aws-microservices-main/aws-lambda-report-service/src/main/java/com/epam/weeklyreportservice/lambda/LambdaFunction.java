package com.epam.weeklyreportservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.epam.weeklyreportservice.WeeklyReportServiceApplication;
import com.epam.weeklyreportservice.service.CSVGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import java.io.IOException;

public class LambdaFunction implements RequestHandler<String,String> {
    ApplicationContext context = SpringApplication.run(WeeklyReportServiceApplication.class);
    private CSVGenerator csvGenerator;

    public LambdaFunction() {
        this.csvGenerator = context.getBean(CSVGenerator.class);
    }

    @Override
    public String handleRequest(String s, Context context) {
        String response;
        try {
            csvGenerator.generateCSV();
            response = "Report is uploaded successfully";
        }
        catch (IOException | RuntimeException e)
        {
            response = e.getMessage();
        }
        return response;
    }

}
