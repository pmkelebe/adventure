package com.pmkelebe.webscraper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pmkelebe.domain.Results;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class WebScraper {

    public static final String SITE_ROOT_URL = "https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk";


    /**
     * @param url
     * @return
     * @throws Exception
     */
    protected abstract Results getResults(String url) throws Exception;

    /**
     * @param url
     * @return
     * @throws Exception
     */
    public String scrape(String url) throws Exception {
        log.debug("start scraping from url: {}", url);

        Results results = getResults(url);

        log.debug("received scraping result");

        String resultsAsJsonString = new ObjectMapper()
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(results);

        log.debug("finished scraping");
        return resultsAsJsonString;
    }
}
