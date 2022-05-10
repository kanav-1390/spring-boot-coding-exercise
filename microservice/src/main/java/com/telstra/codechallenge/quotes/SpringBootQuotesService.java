package com.telstra.codechallenge.quotes;

import com.telstra.codechallenge.model.Root;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.ZonedDateTime;

@Service
public class SpringBootQuotesService {

  @Value("${quotes.base.url}")
  private String quotesBaseUrl;

  @Value("${git.remote.repo}")
  private String gitRepo;

  private RestTemplate restTemplate;

  public SpringBootQuotesService(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  /**
   * Returns an array of spring boot quotes. Taken from https://spring.io/guides/gs/consuming-rest/.
   *
   * @return - a quote array
   */
  public Quote[] getQuotes() {

    return restTemplate.getForObject(quotesBaseUrl + "/api", Quote[].class);
  }

  /**
   * Returns a random spring boot quote. Taken from https://spring.io/guides/gs/consuming-rest/.
   *
   * @return - a quote
   */
  public Quote getRandomQuote() {
    return restTemplate.getForObject(quotesBaseUrl + "/api/random", Quote.class);
  }

  public ResponseEntity getRemoteResponse(String noOfRecords) {
    String sortOrdering="sort=stars&order=desc";
    ZonedDateTime input = ZonedDateTime.now();
    String dateOfLastWeek=lastWeekReturn(input);
    String uri = gitRepo+dateOfLastWeek+"&per_page="+noOfRecords+"&"+sortOrdering;
    restTemplate.setErrorHandler(new CustomErrorHandler());
    return restTemplate.getForEntity(uri, Root.class);
  }

  String lastWeekReturn(ZonedDateTime input) {
    ZonedDateTime startOfLastWeek = input.minusWeeks(1);
    String dateOfLastWeek=startOfLastWeek.getYear()+"-"+startOfLastWeek.getMonthValue()+"-"+startOfLastWeek.getDayOfMonth();
    return dateOfLastWeek;
  }
}
