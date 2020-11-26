package com.tektonlabs.customSearchEngineResults.delegate;

import com.tektonlabs.customSearchEngineResults.client.ISearchClient;
import com.tektonlabs.customSearchEngineResults.dto.SearchResult;
import com.tektonlabs.customSearchEngineResults.exception.RequiredConfigurationException;
import com.tektonlabs.customSearchEngineResults.property.AppProperties;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class SearchEngineDelegateTest {

    private List<ISearchClient> searchClients;
    private AppProperties appProperties;
    private SearchEngineDelegate searchEngineDelegate;

    @Test
    void showSearchEngineResults() {
        appProperties = createCriteria();
        searchClients = createClients();
        searchEngineDelegate = new SearchEngineDelegate(searchClients, appProperties);
        searchEngineDelegate.showSearchEngineResults();
    }

    @Test
    void shouldReturnRequiredConfigurationExceptionWhenNoClientsFound() {
        appProperties = createCriteria();
        RequiredConfigurationException exception = assertThrows(RequiredConfigurationException.class, () -> {
            searchEngineDelegate = new SearchEngineDelegate(searchClients, appProperties);
        });
        assertEquals("No search client providers found", exception.getMessage());
    }

    @Test
    void shouldReturnRequiredConfigurationExceptionWhenNoCriteriaFound() {
        searchClients = createClients();
        RequiredConfigurationException exception = assertThrows(RequiredConfigurationException.class, () -> {
            searchEngineDelegate = new SearchEngineDelegate(searchClients, appProperties);
        });
        assertEquals("No criteria found, add them inside application.yml file", exception.getMessage());
    }

    private List<ISearchClient> createClients() {
        ISearchClient client1 = createIClient("google", 10, "java");
        ISearchClient client2 = createIClient("bing", 100, "java");
        return  Arrays.asList(client1, client2);
    }

    private AppProperties createCriteria() {
        String criteria = "java";
        AppProperties appProperties = new AppProperties();
        appProperties.setCriterias(Collections.singletonList(criteria));
        return appProperties;
    }

    private ISearchClient createIClient(String client, long count, String query) {
        SearchResult searchResult = SearchResult.builder().client(client).count(count).query(query).build();
        return queryString -> Optional.of(searchResult);
    }
}