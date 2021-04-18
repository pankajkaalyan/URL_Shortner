package com.url.shortner.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
public class URL {

	@GeneratedValue
	@Id
	private long id;
	@NonNull
	private String originalUrl;
	@NonNull
	private String shortUrl;
	@NonNull
	private LocalDateTime expiryDate;
	
	
	
}
