package com.url.shortner.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class URLShortnerResponse {

	private String originalUrl;
	private String shortUrl;
	private LocalDateTime expiryDate;
	
}
