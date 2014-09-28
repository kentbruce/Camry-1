package com.camray.bill;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.camray.db.DBConnection;

public class addNewItem  extends JFrame implements ActionListener{
	
	JLabel itemNamelb;
	JLabel itemPricelb;
	JLabel categorylb;
	JTextField itemNametf = new JTextField(20);;
	JTextField itemPricetf = new JTextField(20);
	JPanel panel;
	JButton additm;
	DBConnection db;
	Connection con;
	JComboBox category;
	
	
	public addNewItem(){
		super();
		setBackground(Color.LIGHT_GRAY);
		//setLayout(new GridLayout(0,1));
		setBounds(50, 50, 305, 300);
		panel=new JPanel();
		additm=new JButton("Add Item");
		additm.addActionListener(this);
		category = new JComboBox();
		panel = new JPanel(new GridBagLayout());  
        GridBagConstraints gbc = new GridBagConstraints();  
        //gbc.insets = new Insets(2,2,2,2);  
        addComponents(new JLabel("Item Name:"),   itemNametf, panel, gbc);  
        addComponents(new JLabel("Price:"), itemPricetf, panel, gbc); 
        addComponents(new JLabel("Category:"),category, panel, gbc);
        //addComponents(new JLabel("Reset User Password"),resusrpwdf, panel, gbc);
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		//c.ipady = 20;      //make this component tall
		c.ipadx=5;
		c.weightx = 0.5;
		c.gridx = 1;
		c.gridy = 3;
        panel.add(additm,c);
		
		
		List categorylist = new ArrayList<String>();
		 db=new DBConnection();
		 con=db.acquireConnection();
		
		String catquery="select distinct category_name from camry.menu_category_list ";
		try {
			PreparedStatement ps = con.prepareStatement(catquery);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				String a=rs.getString("CATEGORY_NAME");
				categorylist.add(a);
				category.addItem(a);
			}
			
			
		
			//category.setSelectedIndex(4);
		//category.addActionListener(this);
	}catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		add(panel);

}
	@Override
	public void actionPerformed(ActionEvent e) {
		JFrame frame=new JFrame();
				try {
					 db=new DBConnection();
					 con=db.acquireConnection();
					String selcat=category.getSelectedItem().toString();
					String itmname=itemNametf.getText().toString();
					String pricestring=itemPricetf.getText().toString();
					float itmprice=Float.parseFloat(pricestring);
					if(!((itmname.toLowerCase().matches("[a-z]+|[a-z]+[0-9]+")))){
						
						JOptionPane.showMessageDialog(frame,
							    "Please enter a valid name \n" +
							    "should contain A-Z or 0-9 only");
						
					}
					else if (!((pricestring.matches("[0-9]+[.][0-9]+")||
							pricestring.matches("[0-9]+")))){
						
						JOptionPane.showMessageDialog(frame,
							    "Please enter valid price");
						
					}else{
					String catquery="insert into camry.menu(category,item_name,item_price) values ('"+selcat+"','"+itmname+"',"
							+itmprice+")";
					Statement st = con.createStatement();
						
					int result=st.executeUpdate(catquery);
					con.close();
					
					JOptionPane.showMessageDialog(frame,
						    "Item added successfully");
					itemNametf.setText("");
					itemPricetf.setText("0.00");
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(frame,
						    "Error while inserting item into DB please contact Admin");
					itemNametf.setText("");
					itemPricetf.setText("0.00");
					e1.printStackTrace();
				}
		
	}
	private void addComponents(JLabel label, JTextField tf, JPanel p,  
            GridBagConstraints gbc)  
{  
gbc.anchor = GridBagConstraints.EAST;  
gbc.gridwidth = GridBagConstraints.RELATIVE;  
p.add(label, gbc);  
gbc.anchor = GridBagConstraints.WEST;  
gbc.gridwidth = GridBagConstraints.REMAINDER;  
p.add(tf, gbc);  
}  
	private void addComponents(JLabel label, JComboBox tf, JPanel p,  
            GridBagConstraints gbc)  
{  
gbc.anchor = GridBagConstraints.EAST;  
gbc.gridwidth = GridBagConstraints.RELATIVE;  
p.add(label, gbc);  
gbc.anchor = GridBagConstraints.WEST;  
gbc.gridwidth = GridBagConstraints.REMAINDER;  
p.add(tf, gbc);  
}  
}
