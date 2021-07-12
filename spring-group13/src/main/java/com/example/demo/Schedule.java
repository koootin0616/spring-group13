package com.example.demo;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="schedule")
public class Schedule {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer code;
	private int user_code;
	private int category_code;
	private String name;
	private Date ymd;
	private Time jikan;
	private String importance;
	private String contents;


	public Schedule() {

	}



	public Schedule(Integer code, int user_code, int category_code, String name, Date ymd, Time jikan,
			String importance, String contents) {
		this.code = code;
		this.user_code = user_code;
		this.category_code = category_code;
		this.name = name;
		this.ymd = ymd;
		this.jikan = jikan;
		this.importance = importance;
		this.contents = contents;
	}



	public Schedule(int user_code, int category_code, String name, Date ymd, Time jikan, String importance,
			String contents) {
		super();
		this.user_code = user_code;
		this.category_code = category_code;
		this.name = name;
		this.ymd = ymd;
		this.jikan = jikan;
		this.importance = importance;
		this.contents = contents;
	}



	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
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
