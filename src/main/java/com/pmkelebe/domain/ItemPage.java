package com.pmkelebe.domain;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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

    public String getItemTitle() {
        Element titleElement = document.selectFirst("div.productTitleDescriptionContainer h1");

        if (null != titleElement) {
            return titleElement.text();
        }
        return "";
    }

    public String getItemKcalPer100g() {
        Element kcalPer100gElement = document.selectFirst("table.nutritionTable > tbody > tr:nth-child(2)");

        if (null != kcalPer100gElement) {
            return kcalPer100gElement.selectFirst("td").text();
        }
        return "";
    }

    public String getItemUnitPrice() {
        Element unitPriceElement = document.selectFirst("p.pricePerUnit");

        if (null != unitPriceElement) {
            return unitPriceElement.text();
        }
        return "";
    }

    public String getItemDescription() {
        String description = "";
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
        return description;
    }


}
