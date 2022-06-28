package com.example.domain;

import java.time.LocalDateTime;


public class RegistUrl {
	private Integer id;
	private String uniqueUrl;
	private String mailAddress;
	private LocalDateTime registDate;
	private Integer delFlg;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUniqueUrl() {
		return uniqueUrl;
	}
	public void setUniqueUrl(String uniqueUrl) {
		this.uniqueUrl = uniqueUrl;
	}
	public String getMailAddress() {
		return mailAddress;
	}
	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}
	public LocalDateTime getRegistDate() {
		return registDate;
	}
	public void setRegistDate(LocalDateTime registDate) {
		this.registDate = registDate;
	}
	public Integer getDelFlg() {
		return delFlg;
	}
	public void setDelFlg(Integer delFlg) {
		this.delFlg = delFlg;
	}
	@Override
	public String toString() {
		return "RegistUrl [id=" + id + ", uniqueUrl=" + uniqueUrl + ", mailAddress=" + mailAddress + ", registDate="
				+ registDate + ", delFlg=" + delFlg + "]";
	}
	
	
	
}