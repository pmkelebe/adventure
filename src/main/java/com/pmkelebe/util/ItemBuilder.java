package com.pmkelebe.util;

import com.pmkelebe.domain.Item;
import com.pmkelebe.domain.ItemPage;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@Slf4j
public class ItemBuilder implements Builder<Item> {
    private ItemPage itemPage;

    /**
     * @param itemPage
     */
    public ItemBuilder(ItemPage itemPage) {
        this.itemPage = itemPage;
    }

    @Override
    public Item build() {
        final String numRegEx = "[^0-9.?]+";
        Document document = itemPage.getDocument();
        Item item = new Item();

        //get title
        Element titleElement = document.selectFirst("div.productTitleDescriptionContainer h1");
        item.setTitle(titleElement.text());

        //get kcalPer100g
        Element kcalPer100gElement = document.selectFirst("table.nutritionTable > tbody > tr:nth-child(2)");
        String kcal_per_100g = null;
        if (null != kcalPer100gElement) {
            kcal_per_100g = kcalPer100gElement.selectFirst("td").text();
        }

        if (null != kcal_per_100g) {
            item.setKcalPer100g(Integer.parseInt(kcal_per_100g.replaceAll(numRegEx, "")));
        }

        //get UnitPrice
        Element unitPriceElement = document.selectFirst("p.pricePerUnit");
        item.setUnitPrice(Double.parseDouble(unitPriceElement.text().replaceAll(numRegEx, "")));

        //get description
        String description = null;
        //Check first here...
        Elements decriptionElements = document.select("#information > productcontent > htmlcontent > div:nth-child(2) > p");
        if (null == decriptionElements || decriptionElements.isEmpty()) {
            //If not found, then check here...
            decriptionElements = document.select("#mainPart > div:nth-child(1) > div.memo > p");
        }
        if (null == decriptionElements || decriptionElements.isEmpty()) {
            //... then here !
            decriptionElements = document.select("#mainPart > div.itemTypeGroupContainer.productText:nth-child(1) > div.itemTypeGroup > p");
        }

        //If the description is spread over multiple lines, scrape only the first 'non empty' line.
        if (null != decriptionElements && !decriptionElements.isEmpty()) {
            for (Element element : decriptionElements) {
                if (!element.text().isEmpty()) {
                    description = element.text();
                    break;
                }
            }
        }
        item.setDescription(description);

        log.debug("scraped item: {}", item.toString());
        return item;
    }
}
