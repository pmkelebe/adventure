### WebScraper

This is a Java console application that scrapes the Sainsbury’s grocery site’s - Berries, Cherries, Currants page and returns a JSON array of all the products on the page

The link used is: https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/webapp/wcs/stores/servlet/gb/groceries/berries-cherries-currants6039.html

##### Example Json
 ```
{
  "results": [
    {
      "title": "Sainsbury's Strawberries 400g",
      "kcal_per_100g": 33,
      "unit_price": 1.75,
      "description": "by Sainsbury's strawberries"
    },
    {
      "title": "Sainsbury's Blueberries 200g",
      "kcal_per_100g": 45,
      "unit_price": 1.75,
      "description": "by Sainsbury's blueberries"
    },
    {
      "title": "Sainsbury's Cherry Punnet 200g",
      "kcal_per_100g": 52,
      "unit_price": 1.5,
      "description": "Cherries"
    }

  ],
  "total": {
    "gross": 5.00,
    "vat": 0.83
  }
}
```
#### Dependencies 
- Jsoup 1.12.2
- jackson-databind 2.10.5.1
- lombok 1.18.12
- slf4j-api 2.0.0-alpha1
- logback-classic 1.3.0-alpha5
- junit 4.13.1
- mockito-core 3.3.0
- hamcrest-library 2.2
- powermock-api-mockito-common 1.7.4
- powermock-api-mockito2 2.0.5
- powermock-module-junit4 2.0.5
- jsonassert 1.5.0


#### Project Structure
- src/main/java - All application classes.
  - src/main/java/com/pmkelebe/ - Application class to start the application.
  - src/main/java/com/pmkelebe/webscraper/ - WebScraper interface and BerriesCherriesCurrantsScraper implementation.
  - src/main/java/com/pmkelebe/domain/ - All the application domain classes including page objects using Jsoup.
  - src/main/java/com/pmkelebe/util -

- src/main/resources - All resources used in main application.

- src/test/java - All test classes
  - src/test/java/com/pmkelebe/webscraper - Test for webScraper class, 
  - src/test/java/com/pmkelebe/domain/ - Test for domain classes.
  - src/test/java/com/pmkelebe/util - Tests for util classes and also there is ScraperTestUtil to help for testing 

- src/test/resources - All resources (html files) used in tests.

#### Building

Clone locally the project by using this command
- git clone https://github.com/pmkelebe/adventure.git

Build the app locally from the project root folder (/adventure)
- mvn -U package

#### Running Tests

To run all the test run the following maven goal under /adventure 
- mvn -U test

#### Running App

To run the app, use the following command
- java -jar target/webscraper-1.0-SNAPSHOT.jar

The jar file has all the dependencies. You can copy this from /target folder to any location and then use this command:
- java -jar webscraper-1.0-SNAPSHOT.jar

#### Running App in a Docker container
 
There is a Dockerfile in the project root folder /adventure
- Build project
  - mvn -U package
- Build  Docker image by running the command below; use your own chosen image name.
 Note that the dot is required at the end of the command.
  - docker build -t <your_image_name> . 
- Start your container 
  - docker run  <your_image_name>
