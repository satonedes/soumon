package com.example.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.example.domain.RegistUrl;
import com.example.domain.Users;
import com.example.repository.UserConfirmRepository;


@Service
@Transactional
public class UserConfirmService {
	@Autowired
	private MailSender mailSender;

	@Autowired
	private HttpSession session;
	
	@Autowired
	private UserConfirmRepository repository;
	
	public List<RegistUrl> findByEmail(String email) {
		return repository.findByEmail(email);
	}
	
	public List<Users> findByUser(String email) {
		return repository.findByUser(email);
	}
	
	public void insert(RegistUrl regist) {
		repository.insert(regist);
	}
	public void insertUsers(Users user) {
		repository.insertUsers(user);
	}
//	public void updateUser(String mailAddress) {
//		repository.updateUser(mailAddress);
//	}
	public void updateUrl(String mailAddress) {
		repository.updateUrl(mailAddress);
	}

}
