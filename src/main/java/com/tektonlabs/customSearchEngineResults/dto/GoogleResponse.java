package com.tektonlabs.customSearchEngineResults.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class GoogleResponse {
    @JsonProperty("searchInformation")
    private GoogleSearchInformation searchInformation;
}
