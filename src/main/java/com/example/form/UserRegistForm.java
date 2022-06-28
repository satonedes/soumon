package com.example.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserRegistForm {
	@NotBlank(message = "名前を入力してください")
	private String name;
	@NotBlank(message = "ふりがなを入力してください")
	private String ruby;
	@NotBlank(message = "郵便番号を入力してください")
	@Pattern(regexp = "^[0-9]{3}-[0-9]{4}$", message = "郵便番号はXXX-XXXXの形式で入力してください")
	private String zipCode;
	@NotBlank(message = "住所を入力してください")
	private String address;
	@NotBlank(message = "電話番号を入力してください")
	@Pattern(regexp = "^[0-9]{2,4}-[0-9]{2,4}-[0-9]{3,4}$",message = "電話番号の形式が不正です")
	private String telephone;
	@NotBlank(message = "パスワードを入力してください")
	@Size(min = 8, max = 16, message = "パスワードは8文字以上16文字以内で設定してください")
	private String password;
	@NotBlank(message = "確認用パスワードを入力してください")
	private String password1;
	private String mailAddress;
	
	public String getMailAddress() {
		return mailAddress;
	}
	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRuby() {
		return ruby;
	}
	public void setRuby(String ruby) {
		this.ruby = ruby;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPassword1() {
		return password1;
	}
	public void setPassword1(String password1) {
		this.password1 = password1;
	}
	@Override
	public String toString() {
		return "UserRegistForm [name=" + name + ", ruby=" + ruby + ", zipCode=" + zipCode + ", address=" + address
				+ ", telephone=" + telephone + ", password=" + password + ", password1=" + password1 + "]";
	}
	
	
}
