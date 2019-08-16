package com.happiest_mind.dictionary_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.happiest_mind.dictionary_api.model.Word;

public interface WordRepository extends JpaRepository<Word, Long> {
	
	@Query(value = "select * from word where word=?1",nativeQuery = true)
	List<Word> findWord(String word);

}
