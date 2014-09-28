package com.camray.bill;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.camray.db.DBConnection;

public class EditNewItem  extends JFrame implements ActionListener{
	
	JLabel itemNamelb;
	JLabel itemPricelb;
	JLabel categorylb;
	JTextField itemPricetf = new JTextField(20);
	JPanel panel;
	JButton additm;
	DBConnection db;
	Connection con;
	JComboBox category;
	JComboBox items;
	List<String> categorylist;
	Map<String,ArrayList<String>> cat_itemlist;
	
	
	public EditNewItem(){
		super();
		init();
		
		categorylist = new ArrayList<String>();
		cat_itemlist=new HashMap<String, ArrayList<String>>();
		 db=new DBConnection();
		 con=db.acquireConnection();
		
		String catquery="select distinct category_name from camry.menu_category_list ";
		String itemquery="select item_name from camry.menu where category=?";
		try {
			PreparedStatement ps = con.prepareStatement(catquery);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				String a=rs.getString("CATEGORY_NAME");
				categorylist.add(a);
				category.addItem(a);
				}
			PreparedStatement ps1 = con.prepareStatement(itemquery);
			
			for (String s : categorylist){
				ArrayList<String> itmlist=new ArrayList<String>();
				ps1.setString(1, s);
				ResultSet rs1=ps1.executeQuery();
				while(rs1.next())
				{
					String a=rs1.getString("ITEM_NAME");
					itmlist.add(a);
				}
				cat_itemlist.put(s,itmlist );
				
			}
			//category = new JComboBox(categorylist.toArray());
			con.close();
			initcombo();
			
	}catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
		category.addItemListener(new ItemListener() {
	        public void itemStateChanged(ItemEvent e) {
	        	if (e.getStateChange() == ItemEvent.SELECTED) {
	        	String sel=category.getSelectedItem().toString();
	        	ArrayList<String> list= cat_itemlist.get(sel);
	        	items.removeAllItems();
	        	for (String it:list){
	        		items.addItem(it);
	        	}
	        	}
	        	 
	        }
		});

}
	@Override
	public void actionPerformed(ActionEvent e) {
		JFrame frame=new JFrame();
				try {
					 db=new DBConnection();
					 con=db.acquireConnection();
					String selcat=category.getSelectedItem().toString();
					String itmname=items.getSelectedItem().toString();
					String pricestring=itemPricetf.getText().toString();

					
					if (!((pricestring.matches("[0-9]+[.][0-9]+")||
							pricestring.matches("[0-9]+")))){
						
						JOptionPane.showMessageDialog(frame,
							    "Please enter valid price");
						
					}else{
						float itmprice=Float.parseFloat(pricestring);
					String update="update camry.menu set item_price="+itmprice+
							" where item_name=?";
					PreparedStatement ps = con.prepareStatement(update);
					ps.setString(1, itmname);
					int i=ps.executeUpdate();
					con.close();
					
					JOptionPane.showMessageDialog(frame,
						    "Price updated successfully");
					itemPricetf.setText("0.00");
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
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
	private void addComponents(JLabel label, Component tf, JPanel p,  
            GridBagConstraints gbc)  
{  
gbc.anchor = GridBagConstraints.EAST;  
gbc.gridwidth = GridBagConstraints.RELATIVE;  
p.add(label, gbc);  
gbc.anchor = GridBagConstraints.WEST;  
gbc.gridwidth = GridBagConstraints.REMAINDER;  
p.add(tf, gbc);  
}  
	public void init(){
		setBackground(Color.LIGHT_GRAY);
		//setLayout(new GridLayout(0,1));
		setBounds(50, 50, 305, 300);
		panel=new JPanel();
		additm=new JButton("Update price");
		additm.addActionListener(this);
		category=new JComboBox();
		items =new JComboBox();
		panel = new JPanel(new GridBagLayout());  
        GridBagConstraints gbc = new GridBagConstraints();  
        //gbc.insets = new Insets(2,2,2,2);  
        addComponents(new JLabel("Category:"),category, panel, gbc);
        addComponents(new JLabel("Items:"),   items, panel, gbc);  
        
        addComponents(new JLabel("Price:"), itemPricetf, panel, gbc); 
        
        //addComponents(new JLabel("Reset User Password"),resusrpwdf, panel, gbc);
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		//c.ipady = 20;      //make this component tall
		c.ipadx=5;
		c.weightx = 0.5;
		c.gridx = 1;
		c.gridy = 3;
        panel.add(additm,c);
        add(panel);
	}
	public void initcombo(){
		String sel=category.getSelectedItem().toString();
    	ArrayList<String> list= cat_itemlist.get(sel);
    	items.removeAllItems();
    	for (String it:list){
    		items.addItem(it);
    	}
	}
	
}
