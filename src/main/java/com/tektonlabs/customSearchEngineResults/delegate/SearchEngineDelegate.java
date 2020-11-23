package com.tektonlabs.customSearchEngineResults.delegate;

import com.tektonlabs.customSearchEngineResults.client.ISearchClient;
import com.tektonlabs.customSearchEngineResults.dto.SearchResult;
import com.tektonlabs.customSearchEngineResults.property.AppProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
public class SearchEngineDelegate {

    private final List<ISearchClient> searchClients;
    private final AppProperties appProperties;

    @Autowired
    public SearchEngineDelegate(List<ISearchClient> searchClients, AppProperties appProperties) {
        this.searchClients = searchClients;
        this.appProperties = appProperties;
        validateInputs(searchClients, appProperties);
    }

    public void showSearchEngineResults() {
        Map<String, Long> queryCountsMap = new HashMap<>();
        Map<String, SearchResult> resultMap = new HashMap<>();
        System.out.println("\n\n\n\n");
        System.out.println("==================== Search Engine Results==================");
        searchClients.forEach(
                searchEngineClient -> {
                    showPartialResults(resultMap, queryCountsMap, searchEngineClient);
                }
        );
        showWinnerByClientAndTotal(resultMap);
        showWinnerTotal(queryCountsMap);
        System.out.println("==================== End Search Engine Results==================");
    }

    private void showWinnerTotal(Map<String, Long> queryCountsMap) {
        long countWinner = 0;
        String queryWinner = null;
        for(Map.Entry<String, Long> results : queryCountsMap.entrySet()) {
            if (results.getValue() > countWinner) {
                countWinner = results.getValue();
                queryWinner = results.getKey();
            }
        }
        System.out.println("Total winner: " + queryWinner + " with total results of: " + countWinner);

    }

    private void showPartialResults(Map<String, SearchResult> resultMap,
                                    Map<String, Long> queryCountsMap, ISearchClient iSearchClient) {
        String queryWinner = null;
        long countWinner = 0;
        SearchResult searchResultWinner = null;
        for (String criteria : appProperties.getCriterias()) {
            Optional<SearchResult> search = iSearchClient.search(criteria);
            if (search.isPresent()) {
                SearchResult searchResult = search.get();
                if (queryCountsMap.containsKey(searchResult.getQuery())) {
                    long newValue = queryCountsMap.get(searchResult.getQuery()) + searchResult.getCount();
                    queryCountsMap.replace(searchResult.getQuery(),newValue);
                } else {
                    queryCountsMap.put(searchResult.getQuery(), searchResult.getCount());
                }
                if (searchResult.getCount() > countWinner) {
                    countWinner = searchResult.getCount();
                    queryWinner = searchResult.getQuery();
                    searchResultWinner = searchResult;
                }
                System.out.println(searchResult.getQuery() + ": " + searchResult.getClient() + ": "
                        + searchResult.getCount());
            }
        }
        resultMap.put(queryWinner, searchResultWinner);
    }

    private void showWinnerByClientAndTotal(Map<String, SearchResult> resultMap) {
        long maxCounter = 0;
        for(Map.Entry<String, SearchResult> results : resultMap.entrySet()) {
            if (results.getValue().getCount() > maxCounter) {
                maxCounter = results.getValue().getCount();
            }
            System.out.println(results.getValue().getClient() + " winner: " + results.getValue().getQuery());
        }
    }

    private void validateInputs(List<ISearchClient> searchClients, AppProperties appProperties) {
        if (searchClients == null || searchClients.isEmpty()) {
            log.error("No client providers found");
            throw new IllegalArgumentException("No search client providers found");
        }

        if (appProperties == null || appProperties.getCriterias().isEmpty()) {
            log.error("No criteria found, add them inside application.yml file");
            throw new IllegalArgumentException("No criteria found, add them inside application.yml file");
        }
    }

}
