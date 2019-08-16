package com.happiest_mind.dictionary_api.controller;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.server.PathParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.happiest_mind.dictionary_api.Property.FileUploadProperty;
import com.happiest_mind.dictionary_api.model.Word;
import com.happiest_mind.dictionary_api.service.FileStoreService;

@RestController
@RequestMapping("/file")
public class FileUploadRestController {

	private static final Logger logger= LoggerFactory.getLogger(FileUploadRestController.class);

	@Autowired
	private FileStoreService fileStoreService;
	private final Path fileStorageLocation;


	@Autowired
	public FileUploadRestController(FileUploadProperty fileUploadProperty) {
		super();
		this.fileStorageLocation = Paths.get(fileUploadProperty.getUploadDir()).toAbsolutePath().normalize();
		try {
			Files.createDirectories(this.fileStorageLocation);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	@PutMapping("/upload")
	public String uploadFile(@RequestParam("file") MultipartFile file) throws IOException
	{
		System.out.println("ho:  "+file.getContentType());
		//System.out.println("Upload file:  "+file.getAbsolutePath());
		CopyOnWriteArraySet<Word> wordCollection = new CopyOnWriteArraySet<>();

		try {		

			String fileName = StringUtils.cleanPath(file.getOriginalFilename());

			Path targetFileLocation = this.fileStorageLocation.resolve(fileName);

			System.out.println("file name1:  "+fileName);
			System.out.println("file name2:  "+file.getOriginalFilename());
			System.out.println("file is empty:  "+file.isEmpty());
			//Path filePath = Paths.get(file.getOriginalFilename().substring(0,file.getOriginalFilename().indexOf(".")));
			Files.readAllLines(targetFileLocation).parallelStream()
			.map(line->line.split("\\s+"))
			.flatMap(Arrays::stream)
			.parallel()
			.filter(w->w.matches("\\w+"))
			.map(String::toLowerCase)
			.forEach(word->{
				wordCollection.add(new Word(word));
			});


			fileStoreService.storeFile(wordCollection);
			//wordCollection.forEach((k,v)->{System.out.println("Key:  "+k+"  value:  "+v);});

		}
		catch(Exception e)
		{
			e.printStackTrace();

		}
		return "stored";

	}


	@PostMapping 
	public String isWordExist(@PathParam("word") String word) { 
		List<Word> isExist = fileStoreService.isWordStored(word); 
		return "We have found --->"+isExist.get(0).getWord(); 
	}
	
	@GetMapping
	public String findAllWord() {
		List<Word> words = fileStoreService.findAllWordsCollection();
		StringBuilder sb = new StringBuilder(1000);
		words.forEach(w->{System.out.println(w.getWord()); sb.append(w.getWord()).append("    ");});
		return sb.toString();
	}


}
