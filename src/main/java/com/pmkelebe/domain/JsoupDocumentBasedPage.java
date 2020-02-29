package com.pmkelebe.domain;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class JsoupDocumentBasedPage {

    protected Document document;

    /**
     * @param url
     * @throws IOException
     */
    public JsoupDocumentBasedPage(String url) throws IOException {
        this.document = Jsoup.connect(url).get();
    }

    public Document getDocument() {
        return document;
    }
}
