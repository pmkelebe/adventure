package com.pmkelebe.webscraper;

import com.pmkelebe.domain.Results;

public class BerriesCherriesCurrantsScraper extends WebScraper {
    public static final String BERRIES_CHERRIES_CURRANTS_URL = SITE_ROOT_URL + "/webapp/wcs/stores/servlet/gb/groceries/berries-cherries-currants6039.html";

    /**
     * @param url
     * @return
     * @throws Exception
     */
    @Override
    protected Results getResults(String url) throws Exception {
        //TODO: add custom code for getting scraping results for berries, cherries and currants items
        return null;
    }
}
