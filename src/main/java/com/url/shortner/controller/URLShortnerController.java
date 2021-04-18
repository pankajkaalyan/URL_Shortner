package com.url.shortner.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.url.shortner.dto.URLShortnerErrorResponse;
import com.url.shortner.dto.URLShortnerRequest;
import com.url.shortner.dto.URLShortnerResponse;
import com.url.shortner.entity.URL;
import com.url.shortner.service.URLShortnerService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("urlShortner/v1/app")
@RequiredArgsConstructor
public class URLShortnerController {

	private final URLShortnerService urlShortnerService;
	private final UrlValidator urlValidator;
	
	@PostMapping
	public ResponseEntity<?> generateShortURL(@RequestBody URLShortnerRequest request){
		
		if(request==null) {
			return new ResponseEntity<>(new URLShortnerErrorResponse(400,"Request payload is missing."),HttpStatus.BAD_REQUEST);
		}
		
		if(request.getUrl()==null) {
			return new ResponseEntity<>(new URLShortnerErrorResponse(400,"URL is missing."),HttpStatus.BAD_REQUEST);
		}
		 
		if(!urlValidator.isValid(request.getUrl())) {
			return new ResponseEntity<>(new URLShortnerErrorResponse(400,"URL is invalid."),HttpStatus.BAD_REQUEST);
		}
		
		if(request.getExpiryDate()!=null) {
			if(request.getExpiryDate().isBefore(LocalDateTime.now())) {
				return new ResponseEntity<>(new URLShortnerErrorResponse(400,"Expiry DateTime must be greater than the current DateTime."),HttpStatus.BAD_REQUEST);
			}
		}
		
		URLShortnerResponse response = urlShortnerService.generateShortURL(request);
		return new ResponseEntity<>(response,HttpStatus.CREATED);
	}
	
	@GetMapping("/{shortUrlString}")
	public ResponseEntity<?> redirectToOriginalUrl(@PathVariable String shortUrlString,HttpServletResponse response) throws IOException{

		List<URL> list = urlShortnerService.getOriginalURL(shortUrlString);
		
		/**
		 * 		If there is no link available in database.
		 */
		if(list.size()==0)
			return new ResponseEntity<>("Link doesn't not exist or it has been removed for the database.", HttpStatus.NOT_FOUND);
		
		URL url = list.get(0);
		
		/**
		 * 		If link has already expired, we need to delete the link from the DB.
		 */
		if(url.getExpiryDate().isBefore(LocalDateTime.now())) {
			urlShortnerService.delete(shortUrlString);
			return new ResponseEntity<>("Link has expired and it will be removed from database.", HttpStatus.OK);
		}
		
		response.sendRedirect(url.getOriginalUrl());
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
