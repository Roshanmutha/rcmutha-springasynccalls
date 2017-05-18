package com.rcmutha.springasync;

import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class QuoteLookupService {

	private static final Logger logger = LoggerFactory.getLogger(QuoteLookupService.class);

	private final RestTemplate restTemplate;

	public QuoteLookupService(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.build();
	}

	@Async
	public Future<Quote> findQuote(String user) throws InterruptedException {
		logger.info("Looking up " + user);
		String url = String.format("http://gturnquist-quoters.cfapps.io/api/random");
		Quote results = restTemplate.getForObject(url, Quote.class);
		// Artificial delay of 1s for demonstration purposes
		Thread.sleep(1000L);
		return new AsyncResult<>(results);
	}
	
	
	public Quote findQuoteSync(String user) throws InterruptedException {
		logger.info("Looking up " + user);
		String url = String.format("http://gturnquist-quoters.cfapps.io/api/random");
		Quote results = restTemplate.getForObject(url, Quote.class);
		// Artificial delay of 1s for demonstration purposes
		Thread.sleep(1000L);
		return results;
	}

}
