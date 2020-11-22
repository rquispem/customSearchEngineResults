package com.tektonlabs.customSearchEngineResults.client.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tektonlabs.customSearchEngineResults.client.ISearchClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class BingSearchClient implements ISearchClient {

    private final RestTemplate restTemplate;
    private final String url;
    private final String apiKey;

    public BingSearchClient(RestTemplate restTemplate,
                              @Value("${bing.url}") String url,
                              @Value("${bing.api.key}") String apiKey) {
        this.restTemplate = restTemplate;
        this.url = url;
        this.apiKey = apiKey;
    }

    @Override
    public long search(String query) {
        String endpoint = url + "/search?q=" + query;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Ocp-Apim-Subscription-Key", apiKey);

        HttpEntity<String> request = new HttpEntity<>("", headers);

        try {
            ResponseEntity<String> forEntity = restTemplate.exchange(endpoint, HttpMethod.GET, request, String.class);
            if (forEntity.getStatusCodeValue() == 200) {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode jsonNode = mapper.readTree(forEntity.getBody());
                String result = jsonNode.get("webPages").get("totalEstimatedMatches").asText();
                return Long.parseLong(result);
            }
        } catch (Exception ex) {
            log.error("An error occurred when retrieving results from bing search engine");
        }
        return -1;
    }
}
