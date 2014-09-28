package com.camray.db;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTable;

import com.camray.bill.BillModel;

public class PassFrame extends JFrame {
	Boolean flag=false;
	JLabel passlb;
	JPanel panel;
	JPasswordField password;
	JButton ok;
	JTable tableg;
	BillModel modelg;	
	


	public PassFrame(final JFrame frames){
		
	init();
	ok.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(password.getPassword().length==0){
					JOptionPane.showMessageDialog(null,
				    		  "Please Enter a Password", "Password?", JOptionPane.OK_OPTION);
				   
		}else{
			DBConnection db=new DBConnection();
			String passdb=db.getadminpass();
			char[] pass = password.getPassword();  
			String passString = new String(pass); 
			if (passString.equals(passdb)){
				System.out.println("verified");
				frames.setPreferredSize(new Dimension(300, 300));
				frames.setVisible(true);
				dispose();
			}else{
				JOptionPane.showMessageDialog(null,
			    		  "Login Failed ..Please Try again", "Wrong Password", JOptionPane.OK_OPTION);
			
			}
			}
				
		}
	});
	
	
	}
	public void  init(){
		passlb=new JLabel("Password");
		panel=new JPanel(new FlowLayout());
		setBounds(getToolkit().getScreenSize().width/4,getToolkit().getScreenSize().height/4,
				getToolkit().getScreenSize().width/4,getToolkit().getScreenSize().height/4);
		
		ok=new JButton("OK");
		setTitle("Enter admin password");
		setVisible(true);
		this.addWindowListener(new WindowAdapter()
	    {
	        @Override
	        public void windowClosing(WindowEvent e)
	        {
	           
	            e.getWindow().dispose();
	        }
	    });
		password=new JPasswordField(10);
		panel.add(passlb);
		panel.add(password);
		panel.add(ok);
		add(panel);
	}
	public PassFrame(final int row,BillModel model){
		
		modelg=model;
		init();
		ok.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(password.getPassword().length==0){
					JOptionPane.showMessageDialog(null,
				    		  "Please Enter a Password", "Password?", JOptionPane.OK_OPTION);
				   
		}else{
			DBConnection db=new DBConnection();
			String passdb=db.getadminpass();
			char[] pass = password.getPassword();  
			String passString = new String(pass); 
			if (passString.equals(passdb)){
				System.out.println("verified");
				
				
				modelg.removebill(row);
				dispose();
			}else{
				JOptionPane.showMessageDialog(null,
			    		  "Login Failed ..Please Try again", "Wrong Password", JOptionPane.OK_OPTION);
			
			}
			}
				
		}
	});
		
	}
}