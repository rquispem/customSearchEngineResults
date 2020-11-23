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

If a new Search engine is required, implement the ISearchClient interface add the required logic and add the required API keys as is done for google and bing inside the **application.yml**.
```
google:
  url: https://www.googleapis.com/customsearch/v1
  api:
    key: AIzaSyAln76VKOV4e9_dXjqJ7qYLI6yDFQ9TDhs
  cx:
    key: d3e284ec17b4112c5

bing:
  url: https://api.bing.microsoft.com/v7.0
  api:
    key: 808b72a1c10946448a9ee07438de3134
```
### Results
This is a simple console app, it will check criteria input in the **application.yml** file in order to seach using client search engines.
Sample:
```
==================== Search Engine Results==================
java: bing: 132000000
.net: bing: 105000000
angular: bing: 39000000
php: bing: 10400000000
java: google: 9710000
.net: google: 379000000
angular: google: 1440000
php: google: 6
bing winner: php
google winner: .net
Total winner: php with total results of: 10400000006
==================== End Search Engine Results==================
```

### Docker
CustomSearchEngineResults is very easy to install and deploy in a Docker container.

When ready, simply use the Dockerfile to build the image.

```sh
cd customSearchEngineResults
docker build -t rquispe/search-engine .
```
This will create the search-engine image and pull in the necessary dependencies.

Once done, run the Docker image:

```sh
docker run rquispe/search-engine
```

Verify it checking the console out logs with the results.

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
 - Add jenkins pipeline
