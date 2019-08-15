package com.happiest_mind.dictionary_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.happiest_mind.dictionary_api.Property.FileUploadProperty;

@SpringBootApplication
@EnableConfigurationProperties({
	FileUploadProperty.class
})
public class DictionaryApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(DictionaryApiApplication.class, args);
	}

}
