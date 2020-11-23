# customSearchEngineResults console app
It is a simple console app that queries search engines and compares how many results they
return.

### Installation
customSearchEngineResults requires: 
- [JDK.11](https://openjdk.java.net/projects/jdk/11/) + to run.
- IDE with lombok plugin for [Eclipse](https://projectlombok.org/setup/eclipse) or [Intellij idea](https://plugins.jetbrains.com/plugin/6317-lombok)
-  [Maven](https://maven.apache.org/download.cgi)

Clone the repo and run the app
```sh
$ cd your_dev_directory
$ git clone https://github.com/rquispem/customSearchEngineResults.git
$ mvn clean package
$ mvn spring-boot:run
```

Or install and run it
```sh
$ mvn clean install
$ java -jar your_project_path/target/customSearchEngineResults-0.0.1-SNAPSHOT.jar
```

### Configuration
In order to set up new criterias, just add them inside the **application.yml** file located in:
`/src/main/resources/application.yml`
Sample:
```
search:
  criterias:
    - java
    - .net
    - angular
    - php
```    

If a new Search engine is required, implement the ISearchClient interface and add the required API keys.

### Results
This is a simple console app, it will check criteria input in the **application.yml** file in order to seach using client search engines.
Sample:

### Plugins

customSearchEngineResults uses Jacoco plugin for code coverage.
To check code coverage go to
```sh
$ your_project_path/target/site/jacoco/index.html
```

### APIs used
- [Google Search Engine API](https://developers.google.com/custom-search/v1/using_rest)
- [Bing Search Engine API](https://docs.microsoft.com/en-us/azure/cognitive-services/bing-web-search/quickstarts/java)
 
### Considerations
- Google Custom Search JSON API allows 100 search queries per day for free
- Bing Search API v7 allows 1,000 transactions free per month

### Todos

 - Write MORE Tests
 - Code refactor in delegate
 - Create a docker image
 - Add jenkins pipeline
