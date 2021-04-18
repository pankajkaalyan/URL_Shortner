package com.url.shortner.service;

import java.util.List;

import com.url.shortner.dto.URLShortnerRequest;
import com.url.shortner.dto.URLShortnerResponse;
import com.url.shortner.entity.URL;

public interface URLShortnerService {

	URLShortnerResponse generateShortURL(URLShortnerRequest request);

	List<URL> getOriginalURL(String shortUrlString);

	void delete(String shortUrlString);

}
