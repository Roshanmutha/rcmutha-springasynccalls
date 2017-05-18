package com.rcmutha.springasync;


import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AppRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(AppRunner.class);

    private final QuoteLookupService quoteLookupService;

    public AppRunner(QuoteLookupService quoteLookupService) {
        this.quoteLookupService = quoteLookupService;
    }

    @Override
    public void run(String... args) throws Exception {
    	//------------------------------------With Async-----------------------------------------------
    	// Start the clock
        long start = System.currentTimeMillis();

        // Kick of multiple, asynchronous lookups
        Future<Quote> page1 = quoteLookupService.findQuote("Call One");
        Future<Quote> page2 = quoteLookupService.findQuote("Call Two");
        Future<Quote> page3 = quoteLookupService.findQuote("Call Three");

        // Wait until they are all done
        while (!(page1.isDone() && page2.isDone() && page3.isDone())) {
            Thread.sleep(1); //10-millisecond pause between each check
        }

        // Print results, including elapsed time
        logger.info("With Async Elapsed time: " + (System.currentTimeMillis() - start));
        logger.info("--> " + page1.get());
        logger.info("--> " + page2.get());
        logger.info("--> " + page3.get());
        
        
        //------------------------------------With Synchronization-----------------------------------------------
        
        long startsync = System.currentTimeMillis();
        Quote quote1 = quoteLookupService.findQuoteSync("Quote Call One");
        Quote quote2 = quoteLookupService.findQuoteSync(" Quote Call Two");
        Quote quote3 = quoteLookupService.findQuoteSync("Quote Call Three");
        logger.info("With Synchronization elapsed time: " + (System.currentTimeMillis() - startsync));
        
        logger.info("--> " + quote1);
        logger.info("--> " + quote2);
        logger.info("--> " + quote3);
    }

}
