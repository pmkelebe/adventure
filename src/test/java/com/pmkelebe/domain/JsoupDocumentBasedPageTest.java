package com.pmkelebe.domain;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;


public class JsoupDocumentBasedPageTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorUsingInvalidUrl() throws IOException {
        //Given an invalid url string
        //When creating a page using it as a parameter
        //Then an IllegalArgumentException is thrown
        new JsoupDocumentBasedPage("invalid");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorUsingEmptyUrl() throws IOException {
        //Given an empty url string
        //When creating a page using it as a parameter
        //Then an IllegalArgumentException is thrown
        new JsoupDocumentBasedPage("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorUsingNullUrl() throws IOException {
        //Given a nul url string
        //When creating a page using it as a parameter
        //Then an IllegalArgumentException is thrown
        new JsoupDocumentBasedPage(null);
    }
}