package com.example.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.RegistUrl;
import com.example.domain.Users;
import com.example.form.SubmitMailForm;
import com.example.form.UserRegistForm;
import com.example.form.UserRegistKeyForm;
import com.example.service.RegistUrlService;
import com.example.service.UserConfirmService;

import ch.qos.logback.core.joran.util.beans.BeanUtil;

@Controller
@RequestMapping("/regist")
public class UserConfirmController {

	@Autowired
	private MailSender mailSender;
	@Autowired
	private UserConfirmService service;
	
	@Autowired
	private RegistUrlService registUrlService;
	
	@Autowired
	private HttpSession session;
	
	@ModelAttribute
	public UserRegistForm setupForm() {
		return new UserRegistForm();
	}

	@ModelAttribute
	public SubmitMailForm setupForm1() {
		return new SubmitMailForm();
	}

	@RequestMapping("")
	public String index(SubmitMailForm form, Model model) {
		return "regist_url";
	}

	@RequestMapping("/submit")
	public String submit(@Validated SubmitMailForm form,  BindingResult rs, Model model) {
		if(rs.hasErrors()) {
			return "regist_url";
		}
		
		List<RegistUrl> urlList = service.findByEmail(form.getMailAddress());
		List<Users> userList = service.findByUser(form.getMailAddress());
		
		if (urlList != null) {
			model.addAttribute("errorMessage", "24時間以内に発行済みのURLが既にあります");
			return "redirect:/regist";

		} else if (urlList == null) {
			session.setAttribute("mailAddress", form.getMailAddress());

			if (userList != null) {
				return "submitConfirm";
			} else if (userList == null) {
				String uniqueUrl = UUID.randomUUID().toString();
				SimpleMailMessage msg = new SimpleMailMessage();
				try {
					msg.setFrom("coffeeShopMaster2022@mail.com");
					msg.setTo(form.getMailAddress());
					msg.setSubject("登録用URLの送付");
					msg.setText("Hogehogeシステム、新規ユーザー登録依頼を受け付けました。\n" + "以下のURLから本登録処理を行ってください。\n" + "\n"
							+ "Hogehogeシステム、ユーザー登録URL\n" + "http://localhost:8080/regist/userRegist?key="
							+ uniqueUrl + "※上記URLの有効期限は24時間以内です\n");

					mailSender.send(msg);
					
					RegistUrl regist = new RegistUrl();
					regist.setMailAddress(form.getMailAddress());
					regist.setUniqueUrl(uniqueUrl);
					service.insert(regist);
				} catch (MailException e) {
					e.printStackTrace();
				}
			}
		}
		return "submitConfirm";
	}

	  @RequestMapping("/userRegist")
		public String userRegist(UserRegistKeyForm userRegistKeyform) {
		  // 受け取ったkeyが regist_urlテーブルに存在しているかをチェックする
		  RegistUrl registUrl = registUrlService.findByKey(userRegistKeyform.getKey());
		  session.setAttribute("key", userRegistKeyform.getKey());
		  LocalDateTime nowDateTime = LocalDateTime.now();
		  if (registUrl != null) {
			  LocalDateTime deadline = registUrl.getRegistDate().plusHours(24);
			 
			  if (nowDateTime.isBefore(deadline)) {			  // TODO: 24じかん以内かチェック
				  // OKなら
				  return "userRegist";
			  } else {
				  //エラー画面
				  return "jaka";
			  }
		  }
		  // エラー画面
		  return "jaka";
	  }

	@RequestMapping("/complete")
	public String complete (@Validated UserRegistForm form, BindingResult result, UserRegistKeyForm userRegistKeyform) {
	//	Users findusers = service.findByEmail(form.getMailAddress());
		 String key = (String)session.getAttribute("key");
		 
		 
		if(!form.getPassword1().isEmpty() && !form.getPassword().isEmpty() && !form.getPassword().equals(form.getPassword1())){
			result.rejectValue("password1", "", "パスワードが一致していません");
		}
		
		if (result.hasErrors()) {
		return "userRegist";
		}
		RegistUrl registUrl = registUrlService.findByKey(key);
		System.out.println(registUrl);
		Users user = new Users();
		BeanUtils.copyProperties(form, user);
		user.setMailAddress(registUrl.getMailAddress());
		service.insertUsers(user);
//		service.updateUser(user.getMailAddress());
		service.updateUrl(user.getMailAddress());
		return "registConfirm";
	}
}
