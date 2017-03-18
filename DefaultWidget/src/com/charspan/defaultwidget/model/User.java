package com.charspan.defaultwidget.model;

public class User {
	private String userName;
	private String passWord;
	private char sex;
	private String city;
	private String hobby;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public char getSex() {
		return sex;
	}

	public void setSex(char sex) {
		this.sex = sex;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getHobby() {
		return hobby;
	}

	public void setHobby(String hobby) {
		this.hobby = hobby;
	}

	public User(String userName, String passWord, char sex, String city,
			String hobby) {
		super();
		this.userName = userName;
		this.passWord = passWord;
		this.sex = sex;
		this.city = city;
		this.hobby = hobby;
	}

	public String toString() {
		return this.userName + "," + this.passWord + "," + this.sex + ","
				+ this.city + "," + this.hobby;
	}
}
	
	
	
