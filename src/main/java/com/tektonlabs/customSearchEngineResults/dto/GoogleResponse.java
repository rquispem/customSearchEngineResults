package com.tektonlabs.customSearchEngineResults.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class GoogleResponse {
    @JsonProperty("searchInformation")
    private GoogleSearchInformation searchInformation;
}
