package com.camray.bill;

import javax.swing.JButton;

public class Bill {
	
	
	String itmNo;
	String itmName;
	int itmqty;
	float itmprice;
	ButtonColumn del;
	public Bill() {
	}
	public Bill(String itmNo,String itmName,int itmqty,float d){
		this.itmName=itmName;
		this.itmNo=itmNo;
		this.itmprice=d;
		this.itmqty=itmqty;
	}
	public String getItmNo() {
		return itmNo;
	}
	public void setItmNo(String itmNo) {
		this.itmNo = itmNo;
	}
	public String getItmName() {
		return itmName;
	}
	public void setItmName(String itmName) {
		this.itmName = itmName;
	}
	public int getItmqnty() {
		return itmqty;
	}
	public void setItmqnty(int itmqnty) {
		this.itmqty = itmqnty;
	}
	public 	float getItmprice() {
		return itmprice;
	}
	public void setItmprice(int itmprice) {
		this.itmprice = itmprice;
	}
	public ButtonColumn getDel() {
		return del;
	}
	public void setDel(ButtonColumn buttonColumn) {
		this.del = buttonColumn;
	}

}
