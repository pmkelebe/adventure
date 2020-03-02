package com.pmkelebe.domain;

import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;
import java.util.function.Function;

public class ItemListPage extends JsoupDocumentBasedPage {
    public static final String ITEM_ANCHOR_CSS = "h3 a";

    /**
     * page representing a item list (berries, cherries, currants search result) page
     *
     * @param url
     * @throws IOException
     */
    public ItemListPage(String url) throws IOException {
        super(url);
    }

    public List<ItemPage> getItemPages(final Function<ItemListPage, List<ItemPage>> createItemPageList) throws Exception {
        return createItemPageList.apply(this);
    }

    public Elements getListOfItemElements() {
        return document.getElementsByClass("productNameAndPromotions");
    }
}
