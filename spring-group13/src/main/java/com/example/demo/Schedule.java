package com.example.demo;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="schedule")
public class Schedule {
	@Id
	private int user_code;
	private int category_code;
	private String name;
	private Date ymd;
	private Time jikan;
	private String importance;
	private String contents;


	public Schedule() {

	}

	public Schedule(String name, Time jikan, String contents, int category_code) {
		this.name = name;
		this.jikan = jikan;
		this.contents = contents;
		this.category_code = category_code;
	}



	public Schedule(String name, Time jikan, String importance, String contents) {
		this.name = name;
		this.jikan = jikan;
		this.importance = importance;
		this.contents = contents;
	}

	public int getUser_code() {
		return user_code;
	}

	public void setUser_code(int user_code) {
		this.user_code = user_code;
	}

	public int getCategory_code() {
		return category_code;
	}

	public void setCategory_code(int category_code) {
		this.category_code = category_code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getYmd() {
		return ymd;
	}

	public void setYmd(Date ymd) {
		this.ymd = ymd;
	}

	public Time getJikan() {
		return jikan;
	}

	public void setJikan(Time jikan) {
		this.jikan = jikan;
	}

	public String getImportance() {
		return importance;
	}

	public void setImportance(String importance) {
		this.importance = importance;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}



}
