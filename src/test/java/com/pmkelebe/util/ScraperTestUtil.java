package com.pmkelebe.util;

import com.pmkelebe.domain.Item;
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
        List<Document> documents = new ArrayList<>();

        Document firstDocument = Jsoup.parseBodyFragment(getHtml(htmlFiles[0]));

        for (int i = 1; i < htmlFiles.length; i++) {
            documents.add(Jsoup.parseBodyFragment(getHtml(htmlFiles[i])));
        }
        PowerMockito.mockStatic(Jsoup.class);
        PowerMockito.when(Jsoup.connect(ArgumentMatchers.anyString())).thenReturn(connection);

        if (!documents.isEmpty()) {
            Document[] otherDocuments = documents.stream().toArray(Document[]::new);
            when(connection.get()).thenReturn(firstDocument, otherDocuments);
        } else {
            when(connection.get()).thenReturn(firstDocument);
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

    public static Item getItem(String title, Integer kcalPer100g, Double unitPrice, String description) {
        Item item = new Item();
        item.setTitle(title);
        item.setKcalPer100g(kcalPer100g);
        item.setUnitPrice(unitPrice);
        item.setDescription(description);
        return item;
    }
}
