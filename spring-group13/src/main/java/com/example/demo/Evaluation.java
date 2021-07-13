package com.example.demo;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="evaluation")
public class Evaluation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer code;
	private Date ymd;
	private int user_code;
	private int per;
	private String reflection;
	private String improvement;


	public Evaluation() {

	}


	public Evaluation(Integer code, Date ymd, int user_code, int per, String reflection, String improvement) {
		this.code = code;
		this.ymd = ymd;
		this.user_code = user_code;
		this.per = per;
		this.reflection = reflection;
		this.improvement = improvement;
	}


	public Integer getCode() {
		return code;
	}


	public void setCode(Integer code) {
		this.code = code;
	}


	public Date getYmd() {
		return ymd;
	}


	public void setYmd(Date ymd) {
		this.ymd = ymd;
	}


	public int getUser_code() {
		return user_code;
	}


	public void setUser_code(int user_code) {
		this.user_code = user_code;
	}


	public int getPer() {
		return per;
	}


	public void setPer(int per) {
		this.per = per;
	}


	public String getReflection() {
		return reflection;
	}


	public void setReflection(String reflection) {
		this.reflection = reflection;
	}


	public String getImprovement() {
		return improvement;
	}


	public void setImprovement(String improvement) {
		this.improvement = improvement;
	}

}


