package com.url.shortner.service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.google.common.hash.Hashing;
import com.url.shortner.dao.URLShortnerRepository;
import com.url.shortner.dto.URLShortnerRequest;
import com.url.shortner.dto.URLShortnerResponse;
import com.url.shortner.entity.URL;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class URLShortnerServiceImpl implements URLShortnerService {

	private final URLShortnerRepository urlShortnerRepository;

	@Override
	public URLShortnerResponse generateShortURL(URLShortnerRequest request) {

		String encodedUrl = getEncodedUrl(request);
		URL url = new URL(request.getUrl(),encodedUrl,request.getExpiryDate());
		URLShortnerResponse response = save(url);
		return response;

	}

	public URLShortnerResponse save(URL url) {
		url.setExpiryDate(url.getExpiryDate().plusMinutes(60));
		url = urlShortnerRepository.save(url);
		return new URLShortnerResponse(url.getOriginalUrl(),url.getShortUrl(),url.getExpiryDate());
	}
	
	public String getEncodedUrl(URLShortnerRequest request) {

		LocalDateTime time = null;
		if (request.getExpiryDate()==null)
			time = LocalDateTime.now();
		else
			time = request.getExpiryDate();
		
		request.setExpiryDate(time);
		return Hashing.murmur3_32().hashString(request.getUrl().concat(time.toString()), StandardCharsets.UTF_8)
				.toString();

	}

	@Override
	public List<URL> getOriginalURL(String shortUrlString) {		
		return urlShortnerRepository.findByShortUrl(shortUrlString);
	}

	@Override
	public void delete(String shortUrlString) {
		urlShortnerRepository.deleteByShortUrl(shortUrlString);
	}
}
