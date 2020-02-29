package com.pmkelebe.domain;

import java.io.IOException;

public class ItemPage extends JsoupDocumentBasedPage {
    /**
     * page representing a item details page
     *
     * @param url
     * @throws IOException
     */
    public ItemPage(String url) throws IOException {
        super(url);
    }
}
