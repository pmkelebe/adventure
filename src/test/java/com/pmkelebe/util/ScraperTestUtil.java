package com.pmkelebe.util;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.mockito.ArgumentMatchers;
import org.powermock.api.mockito.PowerMockito;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ScraperTestUtil {

    /**
     * @param htmlFiles
     * @throws IOException
     */
    public static void prepareMocksForTest(String... htmlFiles) throws IOException {

        Connection connection = mock(Connection.class);
        List<Document> itemPages = new ArrayList<>();

        Document itemListPage = Jsoup.parseBodyFragment(getHtml(htmlFiles[0]));

        for (int i = 1; i < htmlFiles.length; i++) {
            itemPages.add(Jsoup.parseBodyFragment(getHtml(htmlFiles[i])));
        }
        PowerMockito.mockStatic(Jsoup.class);
        PowerMockito.when(Jsoup.connect(ArgumentMatchers.anyString())).thenReturn(connection);

        if (!itemPages.isEmpty()) {
            Document[] documents = itemPages.stream().toArray(Document[]::new);
            when(connection.get()).thenReturn(itemListPage, documents);
        } else {
            when(connection.get()).thenReturn(itemListPage);
        }
    }

    public static String getHtml(String fileName) throws IOException {
        String htmlFragment = "";
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());

        //if file is found
        if (file.exists()) {
            //Read File Content
            htmlFragment = new String(Files.readAllBytes(file.toPath()));
        }
        return htmlFragment;
    }
}
