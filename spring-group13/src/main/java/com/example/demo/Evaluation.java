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
	private int usercode;
	private int achieved;
	private int notachieved;
	private int per;
	private String reflection;
	private String improvement;


	public Evaluation() {

	}


	public Evaluation(Date ymd, int usercode, int achieved, int notachieved, int per, String reflection,
			String improvement) {
		this.ymd = ymd;
		this.usercode = usercode;
		this.achieved = achieved;
		this.notachieved = notachieved;
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


	public int getUsercode() {
		return usercode;
	}


	public void setUsercode(int usercode) {
		this.usercode = usercode;
	}


	public int getAchieved() {
		return achieved;
	}


	public void setAchieved(int achieved) {
		this.achieved = achieved;
	}


	public int getNotachieved() {
		return notachieved;
	}


	public void setNotachieved(int notachieved) {
		this.notachieved = notachieved;
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


