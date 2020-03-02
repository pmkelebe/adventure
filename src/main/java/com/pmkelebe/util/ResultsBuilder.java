package com.pmkelebe.util;

import com.pmkelebe.domain.Item;
import com.pmkelebe.domain.ItemPage;
import com.pmkelebe.domain.Results;
import com.pmkelebe.domain.Total;
import lombok.extern.slf4j.Slf4j;

import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class ResultsBuilder implements Builder<Results> {

    public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.##");
    public static final double APPLIED_VAT = 0.20;

    private List<ItemPage> itemPages;

    /**
     * @param itemPages
     */
    public ResultsBuilder(List<ItemPage> itemPages) {
        this.itemPages = itemPages;
    }

    /**
     * @return Results
     */
    @Override
    public Results build() {
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

        log.debug("scraping results: {}", results.toString());
        return results;
    }

    /**
     * @param itemPage
     * @return item
     */
    private Item buildItem(ItemPage itemPage) {
        ItemBuilder builder = new ItemBuilder(itemPage);
        return builder.build();
    }

    /**
     * @param gross
     * @return
     */
    private static Double getTotalVat(Double gross) {
        Double vat = gross - (gross / (1 + APPLIED_VAT));
        return Double.parseDouble(DECIMAL_FORMAT.format(vat));
    }

    /**
     * @param items
     * @return
     */
    private static Double getTotalGross(List<Item> items) {
        Double gross = items.stream()
                .map(Item::getUnitPrice)
                .reduce(0d, (p1, p2) -> p1 + p2);
        return Double.parseDouble(DECIMAL_FORMAT.format(gross));
    }
}
