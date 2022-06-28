package com.example.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class SubmitMailForm {
	@NotBlank(message="メールアドレスを入力してください")
	@Email(message = "メールアドレスの形式が不正です")
	private String mailAddress;

	public String getMailAddress() {
		return mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	@Override
	public String toString() {
		return "SubmitMailForm [mailAddress=" + mailAddress + "]";
	}
	
	
}
