package com.pmkelebe.util;

import com.pmkelebe.domain.Item;
import com.pmkelebe.domain.ItemPage;
import org.jsoup.Jsoup;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Jsoup.class})
public class ItemBuilderTest {

    private Item strawberries400;
    private ItemPage strawberries400Page;

    private ItemBuilder itemBuilder;


    @Before
    public void setUp() throws Exception {
        ScraperTestUtil.prepareMocksForTest("strawberries400.html");
        strawberries400Page = new ItemPage("validUrl");
        itemBuilder = new ItemBuilder(strawberries400Page);
        strawberries400 = getItem("Sainsbury's Strawberries 400g", 33, 1.75,
                "by Sainsbury's strawberries");


    }

    @Test
    public void build() throws IOException {
        //Given a 'strawberries 400g' item page
        //And a ItemBuilder instantiated with that page as a constructor param
        //See: #setUp()

        //When build is invoked
        Item builtItem = itemBuilder.build();

        //Then
        assertThat(builtItem, notNullValue());
        assertThat(strawberries400, is(builtItem));
    }

    //-
    private Item getItem(String title, Integer kcalPer100g, Double unitPrice, String description) {
        Item item = new Item();
        item.setTitle(title);
        item.setKcalPer100g(kcalPer100g);
        item.setUnitPrice(unitPrice);
        item.setDescription(description);
        return item;
    }
}