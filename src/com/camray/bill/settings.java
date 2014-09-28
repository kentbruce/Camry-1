package com.camray.bill;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.prefs.Preferences;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.camray.db.DBConnection;

public class settings extends JFrame{
	JTextField tbl;
	 JTextField tx;
	 JTextField resadpwdf;
	 JTextField resusrpwdf;
	 DBConnection db;
	 Connection con;
	 Preferences prefl;
	public settings(){
		initialize();
	       prefl = Preferences.userRoot().node("com.camray.bill.login$1");
	        db=new DBConnection();
	       con=db.acquireConnection();

		prefs = Preferences.userRoot().node(this.getClass().getName());
		
		 
		  tbl=new JTextField(15);
		 tbl.setText(prefs.getInt("table", 40)+"");
		  tx=new JTextField(15);
		 tx.setText(prefs.getInt("tax", 5)+"");
		 resadpwdf=new JTextField(15);
		  resusrpwdf=new JTextField(15);
		  if(!prefl.getBoolean("admin", false)){
			  tbl.setEditable(false);
			  tx.setEditable(false);
			  resadpwdf.setEditable(false);
			  resusrpwdf.setEditable(false);
		  }
		 JPanel panel = new JPanel(new GridBagLayout());  
	        GridBagConstraints gbc = new GridBagConstraints();  
	        gbc.insets = new Insets(2,2,2,2);  
	        
	        //gbc.weighty = 1.0;                 // allows vertical dispersion  
	        addComponents(new JLabel("Number of Tables:"),   tbl, panel, gbc);  
	        addComponents(new JLabel("Tax:"), tx, panel, gbc); 
	        addComponents(new JLabel("Reset Admin Password:"),resadpwdf, panel, gbc);
	        addComponents(new JLabel("Reset User Password"),resusrpwdf, panel, gbc);
	        
		 JButton ok=new JButton("OK");
		 ok.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				frame.dispose();
			}
		});
		 JButton cancel=new JButton("Cancel");
		 cancel.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					frame.dispose();
				}
			});
		 JButton Apply =new JButton("Apply");
		 Apply.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				// TODO Auto-generated method stub
				      int result = JOptionPane.showConfirmDialog(null,
				    		  "Apply Changes?", "Apply?", JOptionPane.YES_NO_OPTION);
				      
				        if (result == JOptionPane.YES_OPTION) {
				        	//db
				        	String adminp=resadpwdf.getText().toString()+"";
				        	String otherp=resusrpwdf.getText().toString()+"";
				        	
				        	if(!adminp.isEmpty()){
				        	if(!(adminp.matches("[a-zA-Z0-9]+"))){
								
								JOptionPane.showMessageDialog(frame,
									    "Please enter a valid admin password \n" +
									    "should contain A-Z or 0-9 or a-z only");
				        	}else{
				        		String update="update camry.user_list set password='"+adminp+
										"' where user_name=?";
								PreparedStatement ps;
								try {
									ps = con.prepareStatement(update);
									ps.setString(1, "admin");
									int i=ps.executeUpdate();
									con.close();
								} catch (SQLException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								
				        	}	
							}
				        	if(!otherp.isEmpty() ){
				        		String newp=prefl.get("user","");
					        	if(!(otherp.matches("[a-zA-Z0-9]+"))){
									
									JOptionPane.showMessageDialog(frame,
										    "Please enter a valid password for regular user \n" +
										    "should contain A-Z or 0-9 or a-z only");
					        	}else{
					        		con=db.acquireConnection();
					        		String update="update camry.user_list set password='"+otherp+
											"' where user_name=?";
									PreparedStatement ps;
									try {
										ps = con.prepareStatement(update);
										ps.setString(1, "user");
										int i=ps.executeUpdate();
										con.close();
									} catch (SQLException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
									
					        	}
									
								}
				        	setPreference();
				        } else if (result == JOptionPane.NO_OPTION) {
				          System.out.println("Do nothing");
				        }
				   
			
			}
		});
		 panel.add(Apply);
		 panel.add(ok);
		 panel.add(cancel);
		 frame.add(panel);
		 
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
	public static void main(String[] args){
		EventQueue.invokeLater(new Runnable(){
		
			public void run(){
				try{
					new settings();
					
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		});
	}
	
	JFrame frame;
    private Preferences prefs;
    public void setPreference() {
        // This will define a node in which the preferences can be stored
        prefs = Preferences.userRoot().node(this.getClass().getName());
       
        // First we will get the values
        // Define a boolean value
        // Define a string with default "Hello World
        // Define a integer with default 50
prefs.putInt("table",Integer.parseInt(tbl.getText()));
prefs.putFloat("tax",Float.parseFloat(tx.getText()));


        // now set the values
        

        // Delete the preference settings for the first value
        
      }
public  void initialize() {
		

		frame=new JFrame();
frame.setTitle("Settings");
		frame.setSize(400,400);

		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Asigned the close operator
		frame.addWindowListener(new WindowAdapter()
	    {
	        @Override
	        public void windowClosing(WindowEvent e)
	        {
	        	
	            e.getWindow().dispose();
	        }
	    });
	frame.setVisible(true);

		}
}