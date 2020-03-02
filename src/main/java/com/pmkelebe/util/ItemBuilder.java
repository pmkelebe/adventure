package com.pmkelebe.util;

import com.pmkelebe.domain.Item;
import com.pmkelebe.domain.ItemPage;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;

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
        item.setTitle(itemPage.getItemTitle());

        //get kcalPer100g
        String kcal_per_100g = itemPage.getItemKcalPer100g().replaceAll(numRegEx, "");
        if (!kcal_per_100g.isEmpty()) {
            item.setKcalPer100g(Integer.parseInt(kcal_per_100g));
        }

        //get UnitPrice
        String unitPrice = itemPage.getItemUnitPrice().replaceAll(numRegEx, "");
        if (!unitPrice.isEmpty()) {
            item.setUnitPrice(Double.parseDouble(unitPrice));
        }
        //get description
        item.setDescription(itemPage.getItemDescription());

        log.debug("scraped item: {}", item.toString());
        return item;
    }
}
