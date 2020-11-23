package com.tektonlabs.customSearchEngineResults.client.impl;

import com.tektonlabs.customSearchEngineResults.client.ISearchClient;
import com.tektonlabs.customSearchEngineResults.dto.GoogleResponse;
import com.tektonlabs.customSearchEngineResults.dto.SearchResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class GoogleSearchClient implements ISearchClient {
    private final RestTemplate restTemplate;
    private final String url;
    private final String apiKey;
    private final String cxKey;

    public GoogleSearchClient(RestTemplate restTemplate,
                              @Value("${google.url}") String url,
                              @Value("${google.api.key}") String apiKey,
                              @Value("${google.cx.key}") String cxKey) {
        this.restTemplate = restTemplate;
        this.url = url;
        this.apiKey = apiKey;
        this.cxKey = cxKey;
    }

    @Override
    public Optional<SearchResult> search(String query) {
        String endpoint = url + "?fields=searchInformation(totalResults)" + "&key=" + apiKey + "&cx=" + cxKey + "&q=" + query;
        try {
            ResponseEntity<GoogleResponse> forEntity = restTemplate.getForEntity(endpoint, GoogleResponse.class);
            if (forEntity.getStatusCodeValue() == 200) {
                String totalResults = Objects.requireNonNull(forEntity.getBody()).getSearchInformation().getTotalResults();
                return Optional.of(SearchResult.builder().query(query).client("google").count(Long.parseLong(totalResults)).build());
            }
        } catch (Exception e) {
            log.error("An error occurred when retrieving results from google search engine");
        }
        return Optional.empty();
    }
}
