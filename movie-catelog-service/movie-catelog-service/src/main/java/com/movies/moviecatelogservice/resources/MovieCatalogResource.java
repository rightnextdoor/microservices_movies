package com.movies.moviecatelogservice.resources;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersUriSpec;

import com.movies.moviecatelogservice.models.CatalogItem;
import com.movies.moviecatelogservice.models.Movie;
import com.movies.moviecatelogservice.models.Rating;



@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private WebClient.Builder webClientBuilder;
	
	@RequestMapping("/{userId}")
	public List<CatalogItem> getCatalog(@PathVariable("userId")String userId){
		
		List<Rating> ratings = Arrays.asList(
				new Rating("1234", 4),
				new Rating("5678", 3)
				);
		return ratings.stream().map(rating -> {
			Movie movie = restTemplate.getForObject("http://localhost:8082/movies/" + rating.getMovieId(), Movie.class);
			/*
			Movie movie = webClientBuilder.build()
				.get()
				.uri("http://localhost:8082/movies/" + rating.getMovieId())
				.retrieve()
				.bodyToMono(Movie.class)
				.block();
			*/
			return new CatalogItem(movie.getName(), "Test", rating.getRating());
			})
		.collect(Collectors.toList());
		
	}

}
