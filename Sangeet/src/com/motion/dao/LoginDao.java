package com.motion.dao;

public class LoginDao {

	private String idsignup;
	private String passwordu;
	private String status_message;
	private String about_me;
	private String username;
	private String name;
	private String email;
	private String mobile;

	public String getIdsignup() {
		return idsignup;
	}

	public void setIdsignup(String idsignup) {
		this.idsignup = idsignup;
	}

	public String getPasswordu() {
		return passwordu;
	}

	public void setPasswordu(String passwordu) {
		this.passwordu = passwordu;
	}

	public String getStatus_message() {
		return status_message;
	}

	public void setStatus_message(String status_message) {
		this.status_message = status_message;
	}

	public String getAbout_me() {
		return about_me;
	}

	public void setAbout_me(String about_me) {
		this.about_me = about_me;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Override
	public String toString() {
		return "LoginDao [idsignup=" + idsignup + ", passwordu=" + passwordu + ", status_message=" + status_message
				+ ", about_me=" + about_me + ", username=" + username + ", name=" + name + ", email=" + email
				+ ", mobile=" + mobile + "]";
	}

}
