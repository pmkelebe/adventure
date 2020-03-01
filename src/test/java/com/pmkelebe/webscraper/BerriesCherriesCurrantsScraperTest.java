package com.pmkelebe.webscraper;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Jsoup.class})
public class BerriesCherriesCurrantsScraperTest {
    private BerriesCherriesCurrantsScraper webScraper;
    private String validUrl = BerriesCherriesCurrantsScraper.BERRIES_CHERRIES_CURRANTS_URL;
    Connection connection = mock(Connection.class);
    Document document = mock(Document.class);
    Elements elements = mock(Elements.class);

    @Before
    public void setUp() throws Exception {

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
        PowerMockito.when(Jsoup.connect(ArgumentMatchers.any())).thenReturn(connection);
        when(connection.get()).thenThrow(IOException.class);

        webScraper.scrape(validUrl);
        //Then IOException is thrown
    }

    @Test
    public void scrap() throws Exception {
        //TODO: to be improved...
        //Given a valid url
        PowerMockito.mockStatic(Jsoup.class);
        PowerMockito.when(Jsoup.connect(ArgumentMatchers.any())).thenReturn(connection);
        when(connection.get()).thenReturn(document);
        when(document.getElementsByClass("productNameAndPromotions")).thenReturn(elements);
        //When scrape method is called with this url as parameter
        //String json = webScraper.scrape(validUrl);
        //Then a non null json string is returned
        // assertNotNull(json);
    }
}