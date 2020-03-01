package com.pmkelebe.webscraper;

import com.pmkelebe.domain.Item;
import com.pmkelebe.domain.ItemListPage;
import com.pmkelebe.domain.ItemPage;
import com.pmkelebe.domain.Results;
import com.pmkelebe.domain.Total;
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

        //get list of items
        List<Item> items = itemPages.stream()
                .map(BerriesCherriesCurrantsScraper::extractItem)
                .collect(Collectors.toList());

        // get total gross
        Double gross = getTotalGross(items);

        Results res = new Results();
        res.setItems(items);

        Total tot = new Total();
        tot.setGross(gross);
        tot.setVat(getTotalVat(gross));
        res.setTotal(tot);

        return res;
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
    static final Function<ItemListPage, List<ItemPage>> ITEM_PAGE_LIST_CREATION_FUNCTION = (ItemListPage itemListPage) -> {
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

    private static Item extractItem(ItemPage itemPage) {
        final String numRegEx = "[^0-9.?]+";
        Document document = itemPage.getDocument();
        Item item = new Item();
        Element element1 = document.selectFirst("div.productTitleDescriptionContainer h1");
        item.setTitle(element1.text());
        Element element4 = document.selectFirst("table.nutritionTable > tbody > tr:nth-child(2)");

        String kcal_per_100g = null;
        if (null != element4) {
            kcal_per_100g = element4.selectFirst("td").text();
        }

        if (null != kcal_per_100g) {
            item.setKcalPer100g(Integer.parseInt(kcal_per_100g.replaceAll(numRegEx, "")));
        }

        Element element2 = document.selectFirst("p.pricePerUnit");
        item.setUnitPrice(Double.parseDouble(element2.text().replaceAll(numRegEx, "")));

        String description = null;
        Elements elements = document.select("#information > productcontent > htmlcontent > div:nth-child(2) > p");
        if (null == elements || elements.isEmpty()) {
            elements = document.select("#mainPart > div:nth-child(1) > div.memo > p");
        }
        if (null == elements || elements.isEmpty()) {
            elements = document.select("#mainPart > div.itemTypeGroupContainer.productText:nth-child(1) > div.itemTypeGroup > p");
        }

        if (null != elements && !elements.isEmpty()) {
            for (Element element : elements) {
                if (!element.text().isEmpty()) {
                    description = element.text();
                    break;
                }
            }
        }
        item.setDescription(description);

        return item;
    }
}
