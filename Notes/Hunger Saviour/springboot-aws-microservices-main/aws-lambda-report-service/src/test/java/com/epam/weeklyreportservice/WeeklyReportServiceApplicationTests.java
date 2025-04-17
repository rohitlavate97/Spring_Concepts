package com.epam.weeklyreportservice;

import com.epam.weeklyreportservice.service.CSVGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class WeeklyReportServiceApplicationTests {
	@Autowired
	CSVGenerator csvGenerator;

	@Test
	void contextLoads() {
	}

	@Test
	void myTest() throws IOException {
		csvGenerator.generateCSV();
	}

}
