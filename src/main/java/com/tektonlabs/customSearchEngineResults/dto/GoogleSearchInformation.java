package com.tektonlabs.customSearchEngineResults.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class GoogleSearchInformation {
    private String totalResults;
}
