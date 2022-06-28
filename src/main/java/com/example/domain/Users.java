package com.example.domain;

import java.sql.Timestamp;

public class Users {
	Integer id;
	String name;
	String ruby;
	String mailAddress;
	String zipCode;
	String address;
	String telephone;
	String password;
	Timestamp registDate;
	Timestamp updateUrl;
	Integer delFlg;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getMailAddress() {
		return mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
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

	public Timestamp getRegistDate() {
		return registDate;
	}

	public void setRegistDate(Timestamp registDate) {
		this.registDate = registDate;
	}

	public Timestamp getUpdateUrl() {
		return updateUrl;
	}

	public void setUpdateUrl(Timestamp updateUrl) {
		this.updateUrl = updateUrl;
	}

	public Integer getDelFlg() {
		return delFlg;
	}

	public void setDelFlg(Integer delFlg) {
		this.delFlg = delFlg;
	}

	@Override
	public String toString() {
		return "Users [id=" + id + ", name=" + name + ", ruby=" + ruby + ", mailAddress=" + mailAddress + ", zipCode="
				+ zipCode + ", address=" + address + ", telephone=" + telephone + ", password=" + password
				+ ", registDate=" + registDate + ", updateUrl=" + updateUrl
				+ ", delFlg=" + delFlg + "]";
	}
	

}
