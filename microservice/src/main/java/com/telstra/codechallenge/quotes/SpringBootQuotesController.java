package com.telstra.codechallenge.quotes;

import java.util.Arrays;
import java.util.List;

import com.telstra.codechallenge.model.Root;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class SpringBootQuotesController {

  private SpringBootQuotesService springBootQuotesService;

  public SpringBootQuotesController(
      SpringBootQuotesService springBootQuotesService) {
    this.springBootQuotesService = springBootQuotesService;
  }

  @RequestMapping(path = "/quotes", method = RequestMethod.GET)
  public List<Quote> quotes() {
    return Arrays.asList(springBootQuotesService.getQuotes());
  }

  @RequestMapping(path = "/quotes/random", method = RequestMethod.GET)
  public Quote quote() {
    return springBootQuotesService.getRandomQuote();
  }

  @GetMapping(value="/gitrepositories", produces= "application/json")
  public ResponseEntity getStarredGitRepo(@RequestParam String noOfRecords) {
    return springBootQuotesService.getRemoteResponse(noOfRecords);
  }
}
