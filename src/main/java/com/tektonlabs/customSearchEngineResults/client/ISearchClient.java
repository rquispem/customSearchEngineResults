package com.tektonlabs.customSearchEngineResults.client;

import com.tektonlabs.customSearchEngineResults.dto.SearchResult;

import java.util.Optional;

public interface ISearchClient {
    Optional<SearchResult> search(String query);
}
