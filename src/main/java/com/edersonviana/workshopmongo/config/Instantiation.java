package com.edersonviana.workshopmongo.config;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.edersonviana.workshopmongo.domain.Post;
import com.edersonviana.workshopmongo.domain.User;
import com.edersonviana.workshopmongo.dto.AuthorDTO;
import com.edersonviana.workshopmongo.dto.CommentDTO;
import com.edersonviana.workshopmongo.repository.PostRepository;
import com.edersonviana.workshopmongo.repository.UserRepository;

@Configuration
public class Instantiation implements CommandLineRunner{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PostRepository postRepository;
	
	@Override
	public void run(String... args) throws Exception {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		
		userRepository.deleteAll();
		postRepository.deleteAll();
		
		User eder = new User(null, "Eder Viana", "eder@gmail.com");
		User alex = new User(null, "Alex Green", "alex@gmail.com");
		User bob = new User(null, "Bob Grey", "bob@gmail.com");
		
		userRepository.saveAll(Arrays.asList(eder, alex, bob));
		
		Post post1 = new Post(null, sdf.parse("02/01/2025"), "Partiu Viagem!", "Vou viajar para Fortaleza. Abraços!!", new AuthorDTO(eder));
		Post post2 = new Post(null, sdf.parse("03/01/2025"), "Bom dia!", "Acordei em paz hoje!", new AuthorDTO(eder));
		
		CommentDTO c1 = new CommentDTO("Boa viagem mano!", sdf.parse("02/01/2025"), new AuthorDTO(alex));
		CommentDTO c2 = new CommentDTO("Aproveite", sdf.parse("03/01/2025"), new AuthorDTO(bob));
		CommentDTO c3 = new CommentDTO("Tenha um ótimo dia!", sdf.parse("03/01/2025"), new AuthorDTO(alex));
		
		post1.getComments().addAll(Arrays.asList(c1, c2));
		post2.getComments().addAll(Arrays.asList(c3));
		
		postRepository.saveAll(Arrays.asList(post1, post2));
		
		eder.getPosts().addAll(Arrays.asList(post1, post2));
		
		userRepository.save(eder);
		
		
	}
}
