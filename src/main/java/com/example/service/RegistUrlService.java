package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.RegistUrl;
import com.example.repository.RegistUrlRepository;

@Service
public class RegistUrlService {
	
	@Autowired
	private RegistUrlRepository registUrlRepository;

	public RegistUrl findByKey(String key) {
		return registUrlRepository.findByKey(key);
	}
}
