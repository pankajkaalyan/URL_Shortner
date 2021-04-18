package com.url.shortner.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.url.shortner.entity.URL;

@Repository
public interface URLShortnerRepository extends JpaRepository<URL,Long>{

	List<URL> findByShortUrl(String shortUrlString);

	void deleteByShortUrl(String shortUrlString);

}
