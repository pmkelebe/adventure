###WebScraper

This is a Java console application that scrapes the Sainsbury’s grocery site’s - Berries, Cherries, Currants page and returns a JSON array of all the products on the page

The link used is: https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/webapp/wcs/stores/servlet/gb/groceries/berries-cherries-currants6039.html
####Dependencies 
- Jsoup 1.12.2
- jackson-databind 2.10.2
- lombok 1.18.12
- slf4j-api 2.0.0-alpha1
- logback-classic 1.3.0-alpha5
- junit 4.13
- mockito-core 3.3.0
- hamcrest-library 2.2
- powermock-api-mockito-common 1.7.4
- powermock-api-mockito2 2.0.5
- powermock-module-junit4 2.0.5
- jsonassert 1.5.0


####Project Structure
- src/main/java - All application classes.
  - src/main/java/com/pmkelebe/ - Application class to start the application.
  - src/main/java/com/pmkelebe/webscraper/ - WebScraper interface and BerriesCherriesCurrantsScraper implementation.
  - src/main/java/com/pmkelebe/domain/ - All the application domain classes including page objects using Jsoup.
  - src/main/java/com/pmkelebe/util -

- src/main/resources - All resources used in main application.

- src/test/java - All test classes
  - src/test/java/com/pmkelebe/webscraper - Test for webScraper class, 
  - src/test/java/com/pmkelebe/domain/ - Test for domain classes.
  - src/test/java/com/pmkelebe/util - ScraperTestUtil class to help for testing 

- src/test/resources - All resources (html files) used in tests.

####Building

Clone locally the project by using this command
- git clone https://github.com/pmkelebe/adventure.git

Build the app locally from the project root folder (/adventure)
- mvn -U package

####Running Tests

To run all the test run the following maven goal under /adventure 
- mvn -U test

####Running App

To run the app, use the following command
- java -jar target/webscraper-1.0-SNAPSHOT.jar

The jar file has all the dependencies. You can copy this from /target folder to any location and then use this command:
- java -jar webscraper-1.0-SNAPSHOT.jar 