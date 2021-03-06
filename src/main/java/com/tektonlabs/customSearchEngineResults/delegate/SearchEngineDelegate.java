package com.tektonlabs.customSearchEngineResults.delegate;

import com.tektonlabs.customSearchEngineResults.client.ISearchClient;
import com.tektonlabs.customSearchEngineResults.dto.SearchResult;
import com.tektonlabs.customSearchEngineResults.exception.RequiredConfigurationException;
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

    private final List<ISearchClient> searchClientsEngine;
    private final AppProperties appProperties;

    @Autowired
    public SearchEngineDelegate(List<ISearchClient> searchClientsEngine, AppProperties appProperties) {
        this.searchClientsEngine = searchClientsEngine;
        this.appProperties = appProperties;
        validateInputs(searchClientsEngine, appProperties);
    }

    /**
     * Starts clients in order to search for criteria in order to show results
     */
    public void showSearchEngineResults() {
        // This map will contain all the criteria with is total count.
        Map<String, Long> criteriaAccumulatorMap = new HashMap<>();
        // This map will contain the winner result by client, Google Bing ..etc
        Map<String, SearchResult> winnerByClientResultMap = new HashMap<>();
        System.out.println("\n\n\n\n");
        System.out.println("==================== Search Engine Results==================");
        searchClientsEngine.forEach(
                searchEngineClient -> showResultsByClientAndCriteria(winnerByClientResultMap, criteriaAccumulatorMap, searchEngineClient)
        );
        showWinnerByClient(winnerByClientResultMap);
        showWinnerTotal(criteriaAccumulatorMap);
        System.out.println("==================== End Search Engine Results==================");
    }

    private void showResultsByClientAndCriteria(Map<String, SearchResult> winnerByClientResultMap,
                                                Map<String, Long> criteriaAccumulatorMap, ISearchClient iSearchClient) {
        String queryWinner = null;
        long countWinner = 0;
        SearchResult searchResultWinner = null;
        for (String criteria : appProperties.getCriterias()) {
            Optional<SearchResult> search = iSearchClient.search(criteria);
            if (search.isPresent()) {
                SearchResult searchResult = search.get();
                saveCriteriaSum(criteriaAccumulatorMap, searchResult);
                if (searchResult.getCount() > countWinner) {
                    countWinner = searchResult.getCount();
                    queryWinner = searchResult.getQuery();
                    searchResultWinner = searchResult;
                }
                System.out.println(searchResult.getQuery() + ": " + searchResult.getClient() + ": "
                        + searchResult.getCount());
            }
        }
        // Save the criteria with more results by client
        winnerByClientResultMap.put(queryWinner, searchResultWinner);
    }

    /*
     * It saves the sum of the criteria to be compared in the total result and know who is the winner
     */
    private void saveCriteriaSum(Map<String, Long> criteriaAccumulatorMap, SearchResult searchResult) {
        if (criteriaAccumulatorMap.containsKey(searchResult.getQuery())) {
            long newValue = criteriaAccumulatorMap.get(searchResult.getQuery()) + searchResult.getCount();
            criteriaAccumulatorMap.replace(searchResult.getQuery(),newValue);
        } else {
            criteriaAccumulatorMap.put(searchResult.getQuery(), searchResult.getCount());
        }
    }


    private void showWinnerByClient(Map<String, SearchResult> winnerByClientResultMap) {
        for(Map.Entry<String, SearchResult> results : winnerByClientResultMap.entrySet()) {
            System.out.println(results.getValue().getClient() + " winner: " + results.getValue().getQuery());
        }
    }

    private void showWinnerTotal(Map<String, Long> criteriaAccumulatorMap) {
        long countWinner = 0;
        String queryWinner = null;
        for(Map.Entry<String, Long> results : criteriaAccumulatorMap.entrySet()) {
            if (results.getValue() > countWinner) {
                countWinner = results.getValue();
                queryWinner = results.getKey();
            }
        }
        System.out.println("Total winner: " + queryWinner + " with total results of: " + countWinner);

    }

    /*
     * It makes sure there is at least one client and criteria in order to start otherwise an
     * RequiredConfigurationException is thrown
     */
    private void validateInputs(List<ISearchClient> searchClients, AppProperties appProperties) {
        if (searchClients == null || searchClients.isEmpty()) {
            log.error("No client providers found");
            throw new RequiredConfigurationException("No search client providers found");
        }

        if (appProperties == null || appProperties.getCriterias().isEmpty()) {
            log.error("No criteria found, add them inside application.yml file");
            throw new RequiredConfigurationException("No criteria found, add them inside application.yml file");
        }
    }

}
