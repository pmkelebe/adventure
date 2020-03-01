package com.pmkelebe.domain;

import org.hamcrest.CoreMatchers;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Jsoup.class})
public class JsoupDocumentBasedPageTest {
    Connection connection = mock(Connection.class);
    Document mockDocument = mock(Document.class);

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testConstructorUsingValidUrl() throws IOException {
        //Given a valid url string
        String validUrl = "https://www,sainsbury.co.uk";
        PowerMockito.mockStatic(Jsoup.class);
        PowerMockito.when(Jsoup.connect(ArgumentMatchers.any())).thenReturn(connection);
        when(connection.get()).thenReturn(mockDocument);

        //When creating a page using it as a parameter
        JsoupDocumentBasedPage page = new JsoupDocumentBasedPage(validUrl);

        //Then Jsoup library is asked to connect at the valid url location
        PowerMockito.verifyStatic(Jsoup.class);
        Jsoup.connect(validUrl);

        //And the created page contains the correct document object.
        assertThat(page.getDocument(), CoreMatchers.is(mockDocument));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorUsingInvalidUrl() throws Exception {
        //Given an invalid url string
        //When creating a page using it as a parameter
        //Then an IllegalArgumentException is thrown
        new JsoupDocumentBasedPage("invalid");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorUsingEmptyUrl() throws Exception {
        //Given an empty url string
        //When creating a page using it as a parameter
        //Then an IllegalArgumentException is thrown
        new JsoupDocumentBasedPage("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorUsingNullUrl() throws Exception {
        //Given a nul url string
        //When creating a page using it as a parameter
        //Then an IllegalArgumentException is thrown
        new JsoupDocumentBasedPage(null);
    }
}