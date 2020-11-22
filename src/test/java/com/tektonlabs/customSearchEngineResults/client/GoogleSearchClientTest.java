package com.tektonlabs.customSearchEngineResults.client;

import com.tektonlabs.customSearchEngineResults.client.impl.GoogleSearchClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class GoogleSearchClientTest {

    ISearchClient googleSearchClient;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${google.url}") private String url;
    @Value("${google.api.key}") private String apiKey;
    @Value("${google.cx.key}") private String cxKey;

    @BeforeEach
    void setUp() {
        googleSearchClient = new GoogleSearchClient(restTemplate, url, apiKey, cxKey);
    }

    @Test
    public void whenSearchNewWordThenReturnEngineResults() {
        long resultQuery = googleSearchClient.search("java");
        assertTrue(resultQuery > 0);
    }
}
