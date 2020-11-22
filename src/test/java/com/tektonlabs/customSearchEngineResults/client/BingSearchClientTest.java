package com.tektonlabs.customSearchEngineResults.client;

import com.tektonlabs.customSearchEngineResults.client.impl.BingSearchClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class BingSearchClientTest {

    ISearchClient bingSearchClient;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${bing.url}") private String url;
    @Value("${bing.api.key}") private String apiKey;

    @BeforeEach
    void setUp() {
        bingSearchClient = new BingSearchClient(restTemplate, url, apiKey);
    }

    @Test
    public void whenSearchNewWordThenReturnEngineResults() {
        long resultQuery = bingSearchClient.search("java");
        assertTrue(resultQuery > 0);
    }
}
