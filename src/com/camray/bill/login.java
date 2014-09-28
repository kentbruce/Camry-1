package com.camray.bill;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.prefs.Preferences;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.camray.db.DBConnection;

public class login extends JFrame{
	/**
	 * 
	 */
	Connection connection;


	public login(){
		initialize();
		Panel panel= new Panel();
		JLabel usern= new JLabel();
		final JTextField username = new JTextField();
		JLabel pass= new JLabel();
		JButton login = new JButton("Login");
		final JPasswordField password = new JPasswordField();
		GridBagLayout gbl =new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		GridBagConstraints q = new GridBagConstraints();
		GridBagConstraints c1 = new GridBagConstraints();
		GridBagConstraints q1 = new GridBagConstraints();

		GridBagConstraints l = new GridBagConstraints();
		panel.setLayout(gbl);
		usern.setText("Username");
		pass.setText("Password");
		username.setColumns(20);
		username.setToolTipText("Enter the Username here");
		username.requestFocus();
		password.setToolTipText("Enter the password here");
		c.fill = GridBagConstraints.HORIZONTAL;
		     //make this component tall
		c.weightx = 0.0;
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = 1;
		q.fill = GridBagConstraints.HORIZONTAL;
		     //make this component tall
		q.weightx = 0.0;
		q.gridwidth = 1;
		q.gridx = 0;
		q.gridy = 0;
		
		c1.fill = GridBagConstraints.HORIZONTAL;
		      //make this component tall
		c1.weightx = 0.0;
		c1.gridwidth = 1;
		c1.gridx = 1;
		c1.gridy = 0;
		q1.fill = GridBagConstraints.HORIZONTAL;
		     //make this component tall
		q1.weightx = 0.0;
		q1.gridwidth = 1;
		q1.gridx = 1;
		q1.gridy = 1;

		l.fill = GridBagConstraints.HORIZONTAL;
	     //make this component tall
	l.weightx = 0.0;
	l.gridwidth = 1;
	l.gridx = 1;
	l.gridy = 2;
login.addActionListener(new ActionListener() {
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		System.out.println("here");
	if(username.getText().isEmpty()){
		JOptionPane.showMessageDialog(null,
	    		  "Please Enter a Username", "Username?", JOptionPane.OK_OPTION);
	      
	        
	}
	if(password.getPassword().length==0){
		JOptionPane.showMessageDialog(null,
	    		  "Please Enter a Password", "Password?", JOptionPane.OK_OPTION);
	   
	}
	if(!(username.getText().isEmpty() && password.getPassword().length==0)){
		//do validation
		DBConnection db=new DBConnection();
		connection =db.acquireConnection();
		String inst_bill_itm_lst="select * from camry.user_list where user_name ='"+username.getText()+"'";
			try {
				Statement st = connection.createStatement();
				ResultSet rs = st.executeQuery(inst_bill_itm_lst);
				if (rs.next()){
					char[] pass = password.getPassword();  
					String passString = new String(pass); 
				if(passString.equals(rs.getString("password"))){
					System.out.println("verified");
			       Preferences prefs = Preferences.userRoot().node(this.getClass().getName());
			       if(username.getText().equals("admin")){
			       prefs.putBoolean("admin", true);
			    }else{
			    	 prefs.putBoolean("admin", false);
			    	 
			    }
			       //go forward
			       frame.dispose();
			    // main frame = new main();
			       
			       new reservation(0.5);
			     
			     //frame.setVisible(true);
				}else{
					JOptionPane.showMessageDialog(null,
				    		  "Login Failed ..Please Try again", "Wrong Password", JOptionPane.OK_OPTION);
				}
				}else{
					JOptionPane.showMessageDialog(null,
				    		  "Login Failed ..Please Try again", "Wrong Username", JOptionPane.OK_OPTION);
			
				}
			} catch (Exception ea) {
				// TODO Auto-generated catch block
				ea.printStackTrace();
			}
	}
	}
});
		panel.add(usern,q);
		panel.add(username,c1);
		panel.add(pass,c);
		panel.add(password,q1);
		panel.add(login,l);
		frame.add(panel);
	
	}
	JFrame frame;
	

public  void initialize() {
		

		frame=new JFrame();

		frame.setSize(400,400);
		frame.setTitle("Camry");
		//setIconImage(Toolkit.getDefaultToolkit().getImage(main.class.getResource("/com/sun/java/swing/plaf/motif/icons/DesktopIcon.gif")));

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Asigned the close operator
		/*frame.addWindowListener(new WindowAdapter()
	    {
	        @Override
	        public void windowClosing(WindowEvent e)
	        {
	           setPreference();
	            e.getWindow().dispose();
	        }
	    });*/
	frame.setVisible(true);

		}
}