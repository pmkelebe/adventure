package com.pmkelebe.webscraper;

import com.pmkelebe.domain.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BerriesCherriesCurrantsScraper extends WebScraper {
    public static final String BERRIES_CHERRIES_CURRANTS_URL = SITE_ROOT_URL + "/webapp/wcs/stores/servlet/gb/groceries/berries-cherries-currants6039.html";

    /**
     * custom method to generate scraping results
     *
     * @param url
     * @return
     * @throws Exception
     */
    @Override
    protected Results getResults(String url) throws Exception {

        //get the landing page with the list of items to scrape
        ItemListPage itemListPage = new ItemListPage(url);

        // From all items links in 'itemListPage', get all item pages
        List<ItemPage> itemPages = itemListPage.getItemPages(ITEM_PAGE_LIST_CREATION_FUNCTION);

        //TODO: move the below code to a ResultsBuilder
        //get list of items
        List<Item> items = itemPages.stream()
                .map(this::buildItem)
                .collect(Collectors.toList());

        // get total gross
        Double gross = getTotalGross(items);

        Results results = new Results();
        results.setItems(items);

        Total tot = new Total();
        tot.setGross(gross);
        tot.setVat(getTotalVat(gross));
        results.setTotal(tot);

        return results;
    }

    private static Double getTotalVat(Double gross) {
        Double vat = gross - (gross / (1 + APPLIED_VAT));
        return Double.parseDouble(DECIMAL_FORMAT.format(vat));
    }

    private static Double getTotalGross(List<Item> items) {
        Double gross = items.stream()
                .map(Item::getUnitPrice)
                .reduce(0d, (p1, p2) -> p1 + p2);
        return Double.parseDouble(DECIMAL_FORMAT.format(gross));
    }

    //
    final Function<ItemListPage, List<ItemPage>> ITEM_PAGE_LIST_CREATION_FUNCTION = (ItemListPage itemListPage) -> {
        List<ItemPage> itemPages = new ArrayList<>();
        Document document = itemListPage.getDocument();
        Elements elements = document.getElementsByClass("productNameAndPromotions");
        for (Element element : elements) {
            Element linkElement = element.selectFirst("h3 a");
            String itemLink = SITE_ROOT_URL + "/" + linkElement.attributes().get("href").replaceAll("\\.\\./", "");
            try {
                itemPages.add(new ItemPage(itemLink));
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Could not scan items");
            }
        }
        return itemPages;
    };
}
