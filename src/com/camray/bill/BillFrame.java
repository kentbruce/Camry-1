package com.camray.bill;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.prefs.Preferences;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import com.camray.db.DBConnection;
import com.camray.db.PassFrame;

public class BillFrame extends JFrame implements TreeSelectionListener,ActionListener {
		
	    private BillModel billmodel = new BillModel();
	    private JTable tablebill;
	    private TextField tableno = new TextField();
	    JTree tree;
	    Bill bill;
	    float totalamt=0;
	    MenuObject menuobj;
	    JPanel fortab=new JPanel();
	    
	   // GridLayout compbill=new GridLayout(0,1);
	    
		static Preferences pref = Preferences.userRoot().node("com.camray.bill.settings");
		private Boolean dbflag=false;
	    JPanel compbillpnl=new JPanel();
	    HashMap<String, MenuObject> itemlist;
	    JPanel header=new JPanel();
	    JPanel foot=new JPanel(new FlowLayout());
	    JPanel Tree=new JPanel();
	    JLabel tablenolb=new JLabel("Tabel No");
	    JLabel billnolb=new JLabel("Bill No");
	    JLabel datelb=new JLabel("Date");
	    JLabel totalLabel=new JLabel("Total");
	    JLabel taxLabel=new JLabel("Tax");
	    JLabel discLabel=new JLabel("Discount %");
	   
	    TextField cardamt_tf = new TextField("0.00");
	    TextField cashamt_tf = new TextField("0.00");
	    private TextField totalField = new TextField(8);
	    private TextField taxField = new TextField();
	   
	    private TextField discField = new TextField();
	    JButton addbut;
	    private TextField billno = new TextField();
	    private TextField datefield = new TextField();
	    /*  Buttons for footer for addition and print*/
	    JButton total= new JButton("Total");
	    JButton print= new JButton("Print");
	   
	    JScrollPane scrollPane;
	   JPanel fortable=new JPanel();
	    Date date;
	    SimpleDateFormat sf = new SimpleDateFormat("dd-MM-yyyy");
	    float disc_amt=0;
	    long newbillno;
	    DBConnection db;
	    Connection connection;
	    JFrame payment;
	    JPanel paypanel;
	    JRadioButton cardp;  
		JRadioButton cashp;    
		JRadioButton both;
		JButton payok;
		ButtonGroup bgroup = new ButtonGroup();
		String currtabno;
	   // GroupLayout layout = new GroupLayout();
	    
	public BillFrame(String tabno) {
		super();
		currtabno=tabno;
		
		
        this.setBounds(0,0,getToolkit().getScreenSize().width,getToolkit().getScreenSize().height);
       // this.setIconImage(Toolkit.getDefaultToolkit().getImage(main.class.getResource("/com/sun/java/swing/plaf/motif/icons/DesktopIcon.gif")));
		setTitle("Camry");
        db=new DBConnection();
		connection =db.acquireConnection();
		
		tableno.setText(tabno);
		tableno.setEditable(false);
		compbillpnl.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		/* payment panels*/
		payment=new JFrame();
		payok=new JButton("OK");
		 cardp   = new JRadioButton("Card"  , true);
		 cashp    = new JRadioButton("Cash"   , false);
		 both = new JRadioButton("Both", false);
		 bgroup.add(cardp);
		 bgroup.add(cashp);
		 bgroup.add(both);
		 paypanel=new JPanel(new GridBagLayout());
		 
		GridBagConstraints q = new GridBagConstraints();

		 q.fill = GridBagConstraints.HORIZONTAL;
			q.ipady = 10;      //make this component tall
			q.weightx = 0.0;
			q.gridwidth = 1;
			q.gridx = 0;
			q.gridy = 1;
			paypanel.add(cardp,q);
	
			q.gridy = 2;
		 paypanel.add(cashp,q);
		q.gridy =3;
		 paypanel.add(both,q);
		q.gridx=1;
		
		 paypanel.add(payok,q);
		 payment.setBounds(50, 50, 350,150);
		 payment.add(paypanel);

		 payment.setTitle("Pay in Cash/Card?");
		addbut=new JButton();
		setBackground(Color.LIGHT_GRAY);
		setLayout(new GridLayout(0,2));
		date = new Date();
		discField.setText("0.0");
		datefield.setEditable(false);
		taxField.setEditable(false);
		datefield.setText(sf.format(date));
		
		header.setLayout(new FlowLayout());
		Dimension d=new Dimension(2000, 2000);
		billno.setEditable(false);
		header.add(billnolb);
		header.add(billno);
		header.add(tablenolb);
		header.add(tableno);
		header.add(datelb);
		header.add(datefield);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 40;      //make this component tall
		c.weightx = 0.5;
		c.gridx = 1;
		c.gridy = 0;
		compbillpnl.add(header,c);
		foot.add(discLabel);
		foot.add(discField);
		
		foot.add(taxLabel);
		foot.add(taxField);
		foot.add(totalLabel);
		foot.add(totalField);
		foot.add(total);
		foot.add(print);
		
		tablebill = new JTable();
		header.setPreferredSize(d);
		total.addActionListener(this);
		print.addActionListener(this);
		
		payok.addActionListener(this);
		scrollPane = new JScrollPane(tablebill);
		scrollPane.setPreferredSize(new Dimension(600,900));
		fortable.add(scrollPane);
		tablebill.setPreferredSize(new Dimension(600,900));
		tablebill.setForeground(Color.black);
		tablebill.setPreferredScrollableViewportSize(tablebill.getPreferredSize());
		//tablebill.setPreferredSize(preferredSize)
		tablebill.setFillsViewportHeight(true);
		tablebill.setColumnSelectionAllowed(true);
		tablebill.setCellSelectionEnabled(true);
		tablebill.setBackground(Color.WHITE);
		 tablebill.setModel(billmodel);
		 //fortable.add(tablebill);
		ButtonColumn buttonColumn = new ButtonColumn(tablebill,billmodel,  4);
	//	Panel center=new Panel(new FlowLayout());
		taxField.setText((pref.getFloat("tax", (float) 5.00))+"");//set with prefs

		PanelNodes nodes = new PanelNodes();
		
       JPanel f = new JPanel();
       f.setBounds(getToolkit().getScreenSize().width/2, 0, getToolkit().getScreenSize().width/2,getToolkit().getScreenSize().height);
       //f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       f.add(nodes.getContent());
       itemlist =((PanelNodes)nodes).getItemlist();
		tree=((PanelNodes) nodes).getvTree();
		addbut=((PanelNodes) nodes).getAddbut();
		System.out.println(""+f.getWidth());
		
		tree.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				TreePath path = tree.getSelectionPath();
        		DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode)path.getLastPathComponent();
        		PanelNode userObject = (PanelNode)selectedNode.getUserObject();
        		System.out.println("mouse:  "+selectedNode);
        	if((menuobj=itemlist.get(userObject.getText()))!=null){
        		bill=new Bill(menuobj.getItemno(),menuobj.getItemname(),1,menuobj.getItemprice());
        		billmodel.addBill(bill);
        		//code to add to the list for table data saving
        		
        		System.out.println("mouse:  "+bill);
        	}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}
			@Override
			public void mouseExited(MouseEvent e) {
			}
			@Override
			public void mousePressed(MouseEvent e) {
			}
			@Override
			public void mouseReleased(MouseEvent e) {
			}
		});
		
		 //when the window is closed bill is inserted into the database
	    addWindowListener(new WindowAdapter()
	    {
	        @Override
	        public void windowClosing(WindowEvent e)
	        {
	        	try {
	        		if(dbflag){
	        		
	        	List<Bill> billList=billmodel.getListBill(); 
	       		insert_bill(billList);
	       		insert_bill_itm_lst(newbillno,billList);
	       		reservation.table_bill_list.remove(Integer.parseInt(currtabno));
	       		reservation.table_billno.remove(Integer.parseInt(currtabno));
	       		 connection.close();
	        		}
	        		else{
	        			
	            			ArrayList<Bill> newlist =(ArrayList<Bill>) billmodel.getListBill();
	            			reservation.table_bill_list.put(Integer.parseInt(currtabno), newlist);
	            			reservation.table_billno.put(Integer.parseInt(currtabno), billno.getText().toString());
	            		
	            		
	        		}
	       	} catch (SQLException e1) {
	       		// TODO Auto-generated catch block
	       		e1.printStackTrace();
	       	}
	            
	        }
	    });
       
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 400;      //make this component tall
		c.weightx = 0.0;
		c.gridwidth = 3;
		c.gridx = 0;
		c.gridy = 1;
		compbillpnl.add(fortable,c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 0;       //reset to default
		c.weighty = 1.0;   //request any extra vertical space
		c.anchor = GridBagConstraints.PAGE_END; //bottom of space
		c.insets = new Insets(10,0,0,0);  //top padding
		c.gridx = 1;       //aligned with button 2
		c.gridwidth = 2;   //2 columns wide
		c.gridy = 2;  
		compbillpnl.add(foot,c);
		 add(compbillpnl);
		 add(f);
		 //saved data
		 if((!reservation.table_bill_list.isEmpty())&& 
				 reservation.table_bill_list.containsKey(Integer.parseInt(tabno))){
			 billno.setText(reservation.table_billno.get(tabno));
			 ArrayList<Bill> billist=reservation.table_bill_list.get(Integer.parseInt(tabno));
			 for (Bill value :billist ){
				 billmodel.addBill(value);
			 }
			 billno.setText(reservation.table_billno.get(Integer.parseInt(tabno)));
			 newbillno=Long.parseLong(reservation.table_billno.get(Integer.parseInt(tabno)));
		 }else{
			 newbillno=getbillno();
			 billno.setText(Long.toString(newbillno));
			 
		 }
		nodes.registerUI();

      }
@Override
public void valueChanged(TreeSelectionEvent arg0) {
	// TODO Auto-generated method stub
	
}
@Override
public void actionPerformed(ActionEvent e) {
	String checkcash=cashamt_tf.getText().toString();
	String checkcard=cardamt_tf.getText().toString();
	List<Bill> billList=billmodel.getListBill();
	if (e.getSource()==total)
	{
		if(billList.isEmpty()){
			JFrame frame=new JFrame();
			JOptionPane.showMessageDialog(frame,
				    "Please add items to the bill");
		}else{
			totalamt=0;
			float taxperc;
			float discount;
			//Iterator i=itemlist.entrySet().iterator();
			for (Bill value :billList ){
				int qty=value.getItmqnty();
				float price=value.getItmprice();
				totalamt+=qty*price;
			}
			
			String checkdisc=discField.getText().toString();
			
			if (!((checkdisc.matches("[0-9]+[.][0-9]+"))||checkdisc.matches("[0-9]+"))){
				JFrame frame=new JFrame();
				JOptionPane.showMessageDialog(frame,
					    "Please enter valid Discount");
			}
			
			else {
			taxperc=Float.parseFloat(taxField.getText().toString())/100;
			discount=Float.parseFloat(discField.getText().toString())/100;
			totalamt=totalamt+(totalamt*(taxperc));
			disc_amt=totalamt*(discount);
			totalamt=totalamt-(totalamt*(discount));
			totalamt=(float)Math.ceil(totalamt);
			System.out.println(totalamt);
			totalField.setText(Float.toString(totalamt));
			cashamt_tf.setText(totalamt+"");
			payment.setVisible(true);
				
			}
	}
}
	else if(e.getSource()==print){
//check condition to calc total
		if(totalamt==0){
			JFrame frame=new JFrame();
			JOptionPane.showMessageDialog(frame,
				    "Calculate the Bill amount before printing");
			
		}
		else{
		 
	//printing job
			dbflag=true;
	PrintBill p=new PrintBill(billList,newbillno,
				Integer.parseInt(tableno.getText().toString()),
				Float.parseFloat(taxField.getText().toString()),
				Float.parseFloat(discField.getText().toString()));
	        p.printThisBill();
		 
		}
     
	}else if(e.getSource()==payok){
		if(cashp.isSelected()){
			cashamt_tf.setText(totalamt+"");
			payment.dispose();
			
		}else if(cardp.isSelected()){
			cashamt_tf.setText("0.0");
			cardamt_tf.setText(totalamt+"");
			payment.dispose();
			
		}else
		{
			final JFrame bothf=new JFrame();
			JPanel bothpanel=new JPanel(new FlowLayout());
			final JTextField cash=new JTextField(15);
			final JTextField card=new JTextField(15);
			JButton ok=new JButton("OK");
			JLabel cashl=new JLabel("Cash:");
			JLabel cardl=new JLabel("Card:");
			bothpanel.add(cashl);
			bothpanel.add(cash);
			bothpanel.add(cardl);bothpanel.add(card);bothpanel.add(ok);
			bothf.add(bothpanel);
			bothf.setBounds(50,50,500,75);
			bothf.setTitle("Enter Card and Cash Amounts");
			System.out.println("payment");
			payment.dispose();
			Preferences pref=Preferences.userRoot().node("com.camray.bill.login$1");
			if(!pref.getBoolean("admin", true)){
			PassFrame pass=new PassFrame(bothf);
			}else{
				bothf.setPreferredSize(new Dimension(300, 300));
				bothf.setVisible(true);
			}
					ok.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							final String cashst=cash.getText().toString();
							final String cardst=card.getText().toString();
							
							 if(!((cashst.matches("[0-9]+[.][0-9]+")||
									 cashst.matches("[0-9]+")))){
								JFrame frame=new JFrame();
								JOptionPane.showMessageDialog(frame,
									    "Please enter valid cash amount");
							}
							else if(!((cardst.matches("[0-9]+[.][0-9]+")||
									cardst.matches("[0-9]+")))){
								JFrame frame=new JFrame();
								JOptionPane.showMessageDialog(frame,
									    "Please enter valid card amount");
							}
							else if(!(Float.parseFloat(cashst)+Float.parseFloat(cardst)==totalamt)){
								JFrame frame=new JFrame();
								JOptionPane.showMessageDialog(frame,
									    "cash amount+card amount should be equal to the total amount");
							}else{
								cardamt_tf.setText(cardst+"");
								cashamt_tf.setText(cashst+"");
								bothf.dispose();
							}
					}
					});
		
		}
	}
	
}
//inserting into bill_itm_list_table
public void insert_bill_itm_lst(long billno,List<Bill> list){
	for (Bill value : list){
		//temp
		
		int qty=value.getItmqnty();
		String itmno=value.getItmNo();
		String inst_bill_itm_lst="INSERT INTO camry.bill_item_list " +
            "VALUES " + "(" + billno+","+itmno+","+qty+")";
		try {
			Statement st = connection.createStatement();
			int rs = st.executeUpdate(inst_bill_itm_lst);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
}
}
public void insert_bill(List<Bill> list){
		float cash=Float.parseFloat(cashamt_tf.getText().toString());
		float card=Float.parseFloat(cardamt_tf.getText().toString());
		float disc=Float.parseFloat(discField.getText().toString());
		long billno = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		String inst_bill_itm_lst="INSERT INTO camry.bill(bill_no,amount,bill_date,card_amt,cash_amt,disc_amt) " +
            "VALUES " + "("+newbillno+"," +totalamt+",'"+sdf.format(date)+"',"+card+"," +
            		cash+ ","+disc_amt +")";
		try {
			Statement st = connection.createStatement();
			int rs = st.executeUpdate(inst_bill_itm_lst);
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
}	
public long getbillno(){
	long billno = 0;
	try{
	String getbillno="SELECT NEXT VALUE FOR bill_no_seq";
	Statement stmt = connection.createStatement();
	//VALUES NEXTVAL FOR bill_no_seq
	ResultSet rs1 = stmt.executeQuery("VALUES NEXTVAL FOR camry.bill_no_seq");
	//ResultSet rs1 =ps.executeQuery();
	while(rs1.next())
	{
	billno =rs1.getLong(1);
	}
	}
	catch(SQLException e){
		
	}
	return billno;
	
}
/*public boolean bill_exits(){
	
	
	try{
	String getbillno="select bill_no from camry.bill where bill_no=?";
	PreparedStatement st = connection.prepareStatement(getbillno);
	st.setLong(1,newbillno);
	ResultSet rs1 =st.executeQuery();
	while(rs1.next()){
	return true; //bill already exists
	}
	}
	catch(SQLException e){
		
	}
	return false;
	
}*/
}
	

