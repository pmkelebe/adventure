package com.pmkelebe.util;

import com.pmkelebe.domain.Item;
import com.pmkelebe.domain.ItemPage;
import com.pmkelebe.domain.Results;
import org.hamcrest.CoreMatchers;
import org.jsoup.Jsoup;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;


@RunWith(PowerMockRunner.class)
@PrepareForTest({Jsoup.class})
public class ResultsBuilderTest {

    ResultsBuilder resultsBuilder;
    private Item strawberries400;
    private ItemPage strawberries400Page;

    private Item blueberries200;
    private ItemPage blueberries200Page;

    private List<ItemPage> itemPages;
    private Double expectedGross;
    private Double expectedVat;

    @Before
    public void setUp() throws Exception {
        ScraperTestUtil.prepareMocksForTest("strawberries400.html", "blueberries200.html");

        strawberries400Page = new ItemPage("validUrlStrawberries400");
        strawberries400 = ScraperTestUtil.getItem("Sainsbury's Strawberries 400g", 33, 1.75,
                "by Sainsbury's strawberries");

        blueberries200Page = new ItemPage("validUrlBlueberries200");
        blueberries200 = ScraperTestUtil.getItem("Sainsbury's Blueberries 200g", 45, 1.75,
                "by Sainsbury's blueberries");

        itemPages = new ArrayList<>();
        itemPages.add(strawberries400Page);
        itemPages.add(blueberries200Page);

        expectedGross = strawberries400.getUnitPrice() + blueberries200.getUnitPrice();
        expectedVat = expectedGross - (expectedGross / (1 + ResultsBuilder.APPLIED_VAT));

        resultsBuilder = new ResultsBuilder(itemPages);

    }

    @Test
    public void build() {
        //Given a ResultBuilder
        //And a list of item pages ('strawberries 400g' page and 'blueberries 300g' item page)

        //When build is invoked
        Results builtResults = resultsBuilder.build();

        //Then the correct results are produced.
        assertThat(builtResults, notNullValue());
        assertThat(builtResults.getItems(), notNullValue());
        assertThat(builtResults.getItems().size(), is(2));
        assertThat(builtResults.getItems(), CoreMatchers.hasItems(strawberries400, blueberries200));

        assertThat(builtResults.getTotal(), notNullValue());
        assertThat(builtResults.getTotal().getGross(), is(expectedGross));
        assertThat(builtResults.getTotal().getVat(),
                is(Double.parseDouble(ResultsBuilder.DECIMAL_FORMAT.format(expectedVat))));


    }

    //-
}