package com.pmkelebe.webscraper;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class BerriesCherriesCurrantsScraperTest {
    private BerriesCherriesCurrantsScraper webScraper;

    @Before
    public void setUp() throws Exception {
        webScraper = new BerriesCherriesCurrantsScraper();
    }

    @Test
    public void scrap() throws Exception {
        //TODO: to be improved...
        //Given a valid url
        //When scrape method is called with this url as parameter
        String json = webScraper.scrape(BerriesCherriesCurrantsScraper.BERRIES_CHERRIES_CURRANTS_URL);

        //Then a non null json string is returned
        assertNotNull(json);
    }
}