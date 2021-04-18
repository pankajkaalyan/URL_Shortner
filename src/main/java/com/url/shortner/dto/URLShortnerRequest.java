package com.url.shortner.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class URLShortnerRequest {

	private String url;
	private LocalDateTime expiryDate;

}
