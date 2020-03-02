package com.pmkelebe.webscraper;

import com.pmkelebe.util.ScraperTestUtil;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.skyscreamer.jsonassert.JSONAssert;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Jsoup.class})
public class BerriesCherriesCurrantsScraperTest {

    private BerriesCherriesCurrantsScraper webScraper;
    private String validUrl = BerriesCherriesCurrantsScraper.BERRIES_CHERRIES_CURRANTS_URL;
    private Connection mockConnection = mock(Connection.class);

    private static final String EXPECTED_JSON = "{" +
            "  \"results\" : [ " +
            "{" +
            "    \"title\" : \"Sainsbury's Strawberries 400g\"," +
            "    \"kcal_per_100g\" : 33," +
            "    \"unit_price\" : 1.75," +
            "    \"description\" : \"by Sainsbury's strawberries\"" +
            " }, " +
            "{" +
            "    \"title\" : \"Sainsbury's Blueberries 200g\"," +
            "    \"kcal_per_100g\" : 45," +
            "    \"unit_price\" : 1.75," +
            "    \"description\" : \"by Sainsbury's blueberries\"" +
            " } " +
            "]," +
            "  \"total\" : {" +
            "    \"gross\" : 3.5," +
            "    \"vat\" : 0.58" +
            "  }" +
            "}";

    @Before
    public void setUp() {
        webScraper = new BerriesCherriesCurrantsScraper();
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwIllegalArgumentExceptionForEmptyURL() throws Exception {
        webScraper.scrape("");
    }

    @Test(expected = IOException.class)
    public void throwIOExceptionWhenJsoupConnectionOnError() throws Exception {
        //Given a valid url
        //And an error occurs when connection get() call is made
        PowerMockito.mockStatic(Jsoup.class);
        PowerMockito.when(Jsoup.connect(ArgumentMatchers.anyString())).thenReturn(mockConnection);
        when(mockConnection.get()).thenThrow(IOException.class);

        webScraper.scrape(validUrl);
        //Then IOException is thrown
    }

    @Test
    public void scrape() throws Exception {
        //Given a valid url
        ScraperTestUtil.prepareMocksForTest("itemList.html", "strawberries400.html", "blueberries200.html");

        //When scrape method is called with this url as parameter
        String ResultsJsonString = webScraper.scrape(validUrl);

        //Then a non null json string  is returned
        assertNotNull(ResultsJsonString);

        //And the correct json is returned
        JSONAssert.assertEquals(EXPECTED_JSON, ResultsJsonString, true);

    }
}