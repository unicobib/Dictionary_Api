package com.happiest_mind.dictionary_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.happiest_mind.dictionary_api.model.Word;

public interface WordRepository extends CrudRepository<Word, Long> {
	
	@Query(value = "select word from word where word=?",nativeQuery = true)
	List<Word> findWord(String word);

}
