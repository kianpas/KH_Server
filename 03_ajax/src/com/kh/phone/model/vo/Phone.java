package com.kh.phone.model.vo;

import java.sql.Date;

public class Phone {
 
	private String name;
	private int amount;
	private Date date;
	public Phone() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Phone(String name, int amount, Date date) {
		super();
		this.name = name;
		this.amount = amount;
		this.date = date;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	@Override
	public String toString() {
		return name + "," + amount + "," + date;
	}
	
	
}
