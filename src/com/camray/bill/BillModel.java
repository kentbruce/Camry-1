package com.camray.bill;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.table.AbstractTableModel;

import com.camray.db.DBConnection;

public class BillModel extends AbstractTableModel {
	/*
     * we define index numbers for columns in table
     * in order to return the right value in getValue() method
     * and update the right value in setValue() method
     */
    private static final int COLUMN_ITEM_NO    = 0;
    private static final int COLUMN_ITEM_NAME  = 1;
    private static final int COLUMN_ITEM_QTY   = 2;
    private static final int COLUMN_ITEM_PRICE = 3;
    private static final int COLUMN_ITEM_DEL   = 4;
    
    /**
     * the list contains contacts objects
     */
    private List<Bill> listBill;
    public List<Bill> getListBill() {
		return listBill;
	}

	public void setListBill(List<Bill> listBill) {
		this.listBill = listBill;
	}
	/**
     * names for column header in table
     */
    private String[] columnNames;
     
    /**
     * Creates new instance of the model
     */
    public BillModel() {
    	
        // initializes bill list
        this.listBill = new ArrayList<Bill>();
       // define column names
        columnNames = new String[] {"Item No", "Item Name",
                "Quantity", "Price", "Delete"};
       
    }

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return listBill.size();
	}
	@Override
	 public String getColumnName(int col) {
		System.out.println("here"+columnNames[col]);
         return columnNames[col];
     }

	@Override
	public Object getValueAt(int row, int col) {
		Object value=null;
	        
	       Bill bill = listBill.get(row);
	        
	        switch (col) {
	            case COLUMN_ITEM_NAME:
	                value = bill.getItmName();
	                break;
	            case COLUMN_ITEM_NO:
	                value = bill.getItmNo();
	                break;
	            case COLUMN_ITEM_PRICE:
	                value = bill.getItmprice();
	                break;
	            case COLUMN_ITEM_QTY:
	                value = bill.getItmqnty();
	                break;
	            case COLUMN_ITEM_DEL:
	                value = bill.getDel();
	                break;
	        }
	        
	       return value;
	}
	
	 public void setValueAt(Object value, int row, int col) {
		
		 Bill bill = listBill.get(row);
	        
	        switch (col) {
	            case COLUMN_ITEM_NAME:
	                bill.setItmName(value.toString());
	                break;
	            case COLUMN_ITEM_NO:
	                bill.setItmNo((value.toString()));
	                break;
	            case COLUMN_ITEM_PRICE:
	                bill.setItmprice(Integer.parseInt(value.toString()));
	                break;
	            case COLUMN_ITEM_QTY:
	                bill.setItmqnty(Integer.parseInt(value.toString()));
	                break;
	            case COLUMN_ITEM_DEL:
	                bill.setDel((ButtonColumn) value);
	                break;
	        } 
	      fireTableCellUpdated(row, col);
	 
	 }
	
	 public void addBill(Bill bill) {
		 String num=bill.getItmNo();
		 Boolean itemfound=false;	
	for (int i=0;i<listBill.size();i++)
		 {
			  Bill a=(Bill) listBill.get(i);
			  if(a.getItmNo()==bill.getItmNo()){
				  itemfound=true;
				  listBill.get(i).setItmqnty(a.getItmqnty()+1) ;
				  break;
			  }
		 }
	if(!itemfound){
		this.listBill.add(bill);
	}	       
	      fireTableDataChanged();
	   }
	 
	 public void removebill(int rowIndex) {
	       this.listBill.remove(rowIndex);
	       fireTableDataChanged();
	   }
	 /* make changes to set the appropriate cells editable */
	 public boolean isCellEditable(int rowIndex, int columnIndex) {
		 switch(columnIndex){
		 case 3:
			 return true;
		 case 2:
			 return true; 
		 case 4:
			 return true; 
		 }
	       return false;
	   } 

}
