package com.camray.bill;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.camray.db.DBConnection;

public class AddCategory extends JFrame {
	JLabel categorylb;
    JTextField categorynametf ;
    JButton add;
    JPanel panel;
    DBConnection db;
    Connection con;
    
	
	public AddCategory(){
		init();
		
	add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFrame frame=new JFrame();
				String catname=categorynametf.getText().toString();
				if(!((catname.toLowerCase().matches("[a-z]+|[a-z]+[0-9]+")))){
					
					JOptionPane.showMessageDialog(frame,
						    "Please enter a valid name \n" +
						    "should contain A-Z or 0-9 only");
					
				}else{
					String catquery="insert into camry.menu_category_list(category_name)" +
							" values ('"+catname+"')";
					Statement st;
					try {
						st = con.createStatement();
						int result=st.executeUpdate(catquery);
						JOptionPane.showMessageDialog(frame,
							    "Category added successfully");
						categorynametf.setText("");
						
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(frame,
							    "Error inserting into DB. Please contact Admin");
						e.printStackTrace();
					}
						
					
					
					
				}
				
		}
	
	});
		
		
	}
	
	public void init(){
		db=new DBConnection();
		con=db.acquireConnection();
		categorylb = new JLabel("Catgory name:");
		categorynametf = new JTextField(20);
		add = new JButton("Add");
		panel=new JPanel();
		setBounds(50, 50, 305, 300);
		panel.add(categorylb);
		panel.add(categorynametf);
		panel.add(add);
		add(panel);
	}
	

}
