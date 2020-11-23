package com.tektonlabs.customSearchEngineResults.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class SearchResult {
    private String query;
    private String client;
    private long count;
}
