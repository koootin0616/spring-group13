package com.example.demo;

import javax.persistence.Entity;

@Entity
public class Schedule {
	private String name;
	private int jikan;
	private String contents;
	private int category_code;

	public Schedule() {

	}

	public Schedule(String name, int jikan, String contents, int category_code) {
		super();
		this.name = name;
		this.jikan = jikan;
		this.contents = contents;
		this.category_code = category_code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getJikan() {
		return jikan;
	}

	public void setJikan(int jikan) {
		this.jikan = jikan;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public int getCategory_code() {
		return category_code;
	}

	public void setCategory_code(int category_code) {
		this.category_code = category_code;
	}


}
