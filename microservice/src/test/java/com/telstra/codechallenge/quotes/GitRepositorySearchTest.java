package com.telstra.codechallenge.quotes;

import com.telstra.codechallenge.model.Root;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import com.telstra.codechallenge.quotes.CustomErrorHandler;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.ZonedDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class GitRepositorySearchTest {

    @InjectMocks
    SpringBootQuotesService gitRepositorySearch;
    @Mock
    RestTemplate restTemplate;


    private String gitRepo="http://localhost:8080";


    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(gitRepositorySearch ,"gitRepo",gitRepo);
    }

    @Test
    void testLastWeekFunction() {
        ZonedDateTime inputTime = ZonedDateTime.now();
        String dateTest=gitRepositorySearch.lastWeekReturn(inputTime);
        ZonedDateTime startOfLastWeek = inputTime.minusWeeks(1);
        String dateOfLastWeek=startOfLastWeek.getYear()+"-"+startOfLastWeek.getMonthValue()+"-"+startOfLastWeek.getDayOfMonth();
        Assertions.assertEquals(dateOfLastWeek,dateTest);
    }

    @Test
    void testsEndPoint() {
        ReflectionTestUtils.setField(gitRepositorySearch ,"gitRepo",gitRepo);
        when(restTemplate.getForEntity(anyString(),any())).thenReturn(new ResponseEntity<>(HttpStatus.OK));
        Assertions.assertEquals(gitRepositorySearch.getRemoteResponse("10").getStatusCode(),HttpStatus.OK);       // Assertions.assertEquals(restTemplate.getForEntity(gitRepo, Root.class).getStatusCode(),HttpStatus.NOT_FOUND);
    }

    @Test
    void testBadEndPoint() {
        ReflectionTestUtils.setField(gitRepositorySearch ,"gitRepo",gitRepo);
        when(restTemplate.getForEntity(anyString(),any())).thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        Assertions.assertEquals(gitRepositorySearch.getRemoteResponse("10").getStatusCode(),HttpStatus.NOT_FOUND);       // Assertions.assertEquals(restTemplate.getForEntity(gitRepo, Root.class).getStatusCode(),HttpStatus.NOT_FOUND
    }
}