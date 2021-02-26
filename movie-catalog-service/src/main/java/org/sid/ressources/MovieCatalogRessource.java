package org.sid.ressources;


import java.util.List;
import java.util.stream.Collectors;

import org.sid.model.CatalogeItem;
import org.sid.model.Movie;

import org.sid.model.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
@RequestMapping("cataloge")
public class MovieCatalogRessource {

	@Autowired
	private RestTemplate restTemplate;

	@RequestMapping("/{userId}")
	public List<CatalogeItem> gatCatalog(@PathVariable String userId){
		

		UserRating ratings=restTemplate.getForObject("http://localhost:8083/ratingsdata/users/"+userId,UserRating.class);
		
	
		
		 return ratings.getUserRating().stream().map(rating->{
		Movie movie= restTemplate.getForObject("http://localhost:8082/movies/"+rating.getMovieId(),Movie.class);
			 
		
		 return new CatalogeItem(movie.getName(), "Test", rating.getRating());
		 }).collect(Collectors.toList());
		
		
	}
}
