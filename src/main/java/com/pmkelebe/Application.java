package com.pmkelebe;

import com.pmkelebe.webscraper.BerriesCherriesCurrantsScraper;
import com.pmkelebe.webscraper.WebScraper;
import lombok.extern.slf4j.Slf4j;

import static com.pmkelebe.webscraper.BerriesCherriesCurrantsScraper.BERRIES_CHERRIES_CURRANTS_URL;

@Slf4j
public class Application {

    public static void main(String[] args) {

        WebScraper webScraper = new BerriesCherriesCurrantsScraper();
        try {
            String resultsAsJsonString = webScraper.scrape(BERRIES_CHERRIES_CURRANTS_URL);
            log.info("Result for scraping: \n {}", resultsAsJsonString);

        } catch (Exception e) {
            // ignored for now
        }
    }
}
