package com.happiest_mind.dictionary_api.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.happiest_mind.dictionary_api.Property.FileUploadProperty;
import com.happiest_mind.dictionary_api.model.Word;
import com.happiest_mind.dictionary_api.repository.WordRepository;

@Service
public class FileStoreService {
	
	
	private WordRepository wordRepo;
	
	
	@Autowired
	public FileStoreService(WordRepository wordRepo) {
		super();
		this.wordRepo = wordRepo;
		
	}

	

	public String storeFile(CopyOnWriteArraySet<Word> wordCollection) {
		
		wordRepo.saveAll(wordCollection);
		
		return "store file";
		
		
			}

	
	  public List<Word> isWordStored(String word) {
	  
	  return wordRepo.findWord(word); 
	  }



	public List<Word> findAllWordsCollection() {
		Iterable<Word> l = wordRepo.findAll();
		l.forEach(v->{System.out.println(v.getWord());});
		return null;
	}
	 

}
