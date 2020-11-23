package com.tektonlabs.customSearchEngineResults;

import com.tektonlabs.customSearchEngineResults.delegate.SearchEngineDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CustomSearchEngineResultsApplication implements CommandLineRunner {
	@Autowired
	SearchEngineDelegate searchEngineDelegate;

	public static void main(String[] args) {
		SpringApplication.run(CustomSearchEngineResultsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		searchEngineDelegate.showSearchEngineResults();
	}
}
