package com.camray.bill;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.prefs.Preferences;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.text.DateFormatter;

import com.camray.db.PassFrame;
import com.camray.reports.Report;
import com.camray.reports.ReportFrame;

public class reservation extends JFrame
{
	static  Map<Integer,ArrayList<Bill>> table_bill_list=new HashMap<Integer, ArrayList<Bill>>();
    static Map<Integer,String> table_billno=new HashMap<Integer, String>();
	@SuppressWarnings("deprecation")
	public reservation(double d) {
		table=pref.getInt("table", 40);
		time(d);
		initialize();
		prefs = Preferences.userRoot().node(this.getClass().getName());
	    
	    Calendar calendar = Calendar.getInstance();
        SpinnerDateModel model = new SpinnerDateModel();
        model.setValue(calendar.getTime());
        spinner =  new JSpinner(model);

        JSpinner.DateEditor editor = new JSpinner.DateEditor(spinner, "dd/MM/yyyy hh:mm:ss a");
        DateFormatter formatter = (DateFormatter)editor.getTextField().getFormatter();
        formatter.setAllowsInvalid(false); // this makes what you want
        formatter.setOverwriteMode(true);

        spinner.setEditor(editor);        
        GridLayout grid= new GridLayout(0,1);
		GridLayout gridLayout = new GridLayout(0,10);
		Panel p0=new Panel();
		Panel p1=new Panel();
		Panel big = new Panel();
		p0.setLayout(gridLayout);
		GridBagLayout gbl =new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		GridBagConstraints q = new GridBagConstraints();
		p1.setLayout(gbl);
		
		ActionListener menuListener = new ActionListener() {
		      public void actionPerformed(ActionEvent event) {
		       if( event.getActionCommand().equals("Reserve")){
		    	  state[selectedButton]=2;
		    	  Date dt=(Date)spinner.getValue();
		    	  datearr[selectedButton]= dt; 
		    	  @SuppressWarnings("deprecation")
				int hr=dt.getHours();
		    	  @SuppressWarnings("deprecation")
				int min = dt.getMinutes();
		    	  btnNewButton[selectedButton].setText(selectedButton+1+" "+hr+":"+min);
		       }
		       if( event.getActionCommand().equals("Occupied")){
		    	   state[selectedButton]=1;
		    	   btnNewButton[selectedButton].setBackground(new Color(16776960));
		    	   btnNewButton[selectedButton].setText(selectedButton+1+"");
		    	   datearr[selectedButton]=null;
		       }
		       if( event.getActionCommand().equals("Free")){
		    	   state[selectedButton]=0;
		    	   btnNewButton[selectedButton].setBackground(new Color(65280));
		    	   btnNewButton[selectedButton].setText(selectedButton+1+"");
		    	   datearr[selectedButton]=null;
		       }
		       if( event.getActionCommand().equals("Bill")){
		    	//go to billing page
		    	   System.out.println("billing");
		    	   //if colour not equal to red
		    	   if(state[selectedButton]!=0){
		    		   
		    	   
		    	   btnNewButton[selectedButton].setBackground(new Color(16776960));  
		    	   state[selectedButton]=1;
		    	   datearr[selectedButton]=null;
		    	   btnNewButton[selectedButton].setText(selectedButton+1+"");
		    	   BillFrame billframe=new BillFrame((selectedButton+1)+"");
		    	   billframe.setVisible(true);
		    	   }else
		    	   {
		    		   JFrame fr=new JFrame();
						JOptionPane.showMessageDialog(fr,
							    "Please change the to Occupied");
		    	   }
		       }
		      }
		    };
		   
		  JMenuItem item;
		  popupl=new JPopupMenu();
		    popupl.add(item = new JMenuItem("Reserve"));
		    
		    item.addActionListener(menuListener);
		    popupl.add(item = new JMenuItem("Occupied"));
		    item.addActionListener(menuListener);
		    popupl.add(item = new JMenuItem("Free"));
		    item.addActionListener(menuListener);
		    
		  JMenuItem it;
		  popupr = new JPopupMenu();
		  popupr.add(it=new JMenuItem("Bill"));
		  it.addActionListener(menuListener);
		  
		    JButton clear=new JButton("Clear");
		    clear.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					      int result = JOptionPane.showConfirmDialog(null,
					    		  "Do You Want To Clear All Tables?", "Clear?", JOptionPane.YES_NO_OPTION);
					      
					        if (result == JOptionPane.YES_OPTION) {
					        	  
							   for(int i=0;i<table;i++){
								btnNewButton[i].setText(i+1+"");
								btnNewButton[i].setBackground(new Color(65280));
								datearr[i]=null;
								state[i]=0;
							}
					        } else if (result == JOptionPane.NO_OPTION) {
					          System.out.println("Do nothing");
					        }
					   
				}
			});
		    
		    
			/*	for (int i=0;i<(table/4);i++){*/
		    
		    if( !prefs.getBoolean("first", false)){
					for(int j=0; j<table;j++){
						final int arr=j;
					 btnNewButton[arr] = new JButton(arr+1+"");
					 
					      
					     btnNewButton[arr].setBackground(new Color(65280));
					 state[arr]=0;
					 
					p0.add(btnNewButton[arr]/*, gbc_btnNewButton*/);
					
					btnNewButton[arr].addMouseListener( new MouseAdapter() {
		                    public void mousePressed(MouseEvent ae) {
		                    	
		                    	if((ae.getModifiers()& InputEvent.BUTTON1_MASK)!=0){
		                    	
		                    	frame.revalidate();
		                        popupl.show(btnNewButton[arr], btnNewButton[arr].getWidth()/2, btnNewButton[arr].getHeight()/2);
		                        selectedButton=arr;
		                    	
		                    	}
		                    	if((ae.getModifiers() & InputEvent.BUTTON3_MASK)!=0){ 
		                    		
			                    	frame.revalidate();
			                        popupr.show(btnNewButton[arr], btnNewButton[arr].getWidth()/2, btnNewButton[arr].getHeight()/2);
			                        selectedButton=arr;
		                    	}
		                    }
		                } );
					 
//					}
					}
					prefs.putBoolean("first", true);
				}else{
					for(int j=0; j<table;j++){
						final int arr=j;
					 btnNewButton[arr] = new JButton(arr+1+"");
					 
					 switch (prefs.getInt(arr+"", 0)){
					 
					 case 0:  btnNewButton[arr].setBackground(new Color(65280));
					 			state[arr]=0;
					 			break;
					 case 1: btnNewButton[arr].setBackground(new Color(16776960));
					 			state[arr]=1;
					 			break;
					 case 2: Date date=new Date();
					 		date.setTime(prefs.getLong(table+arr+"", 0));
					 		btnNewButton[arr].setText(arr+1+" "+date.getHours()+":"+date.getMinutes());
					 		state[arr]=2;
					 		datearr[arr]=date;
					 		break;
					 }
					    
					 
					p0.add(btnNewButton[arr]/*, gbc_btnNewButton*/);
					
					btnNewButton[arr].addMouseListener( new MouseAdapter() {
	                    public void mousePressed(MouseEvent ae) {
		                    	if((ae.getModifiers() & InputEvent.BUTTON1_MASK)!=0){
			                    	
			                    	frame.validate();
			                        popupl.show(btnNewButton[arr], btnNewButton[arr].getWidth()/2, btnNewButton[arr].getHeight()/2);
			                        selectedButton=arr;
			               
			                    	}
			                     if((ae.getModifiers() & InputEvent.BUTTON3_MASK)!=0){

				                    	frame.validate();
				                        popupr.show(btnNewButton[arr], btnNewButton[arr].getWidth()/2, btnNewButton[arr].getHeight()/2);
			                    	selectedButton=arr;
			                     }
		                    }
		                } );
					 
//					}
					}
				}
					c.fill = GridBagConstraints.HORIZONTAL;
					c.ipady = 10;      //make this component tall
					c.weightx = 0.0;
					c.gridwidth = 1;
					c.gridx = 0;
					c.gridy = 1;
					q.fill = GridBagConstraints.HORIZONTAL;
					q.ipady = 10;      //make this component tall
					q.weightx = 0.0;
					q.gridwidth = 1;
					q.gridx = 0;
					q.gridy = 0;
			p1.add(spinner,q);
			p1.add(clear,c);
			
			big.setLayout(grid);
			big.add(p0);
			big.add(p1);
				frame.add(big);
		}
	
	Thread time;
	int selectedButton;
	static Preferences pref = Preferences.userRoot().node("com.camray.bill.settings");

	static int table= pref.getInt("table",40); // can change this no.
	final JButton btnNewButton[]= new JButton[table];
	final Date[] datearr= new Date[table];
	JSpinner spinner = new JSpinner();
	JPopupMenu popupr;
	JPopupMenu popupl;
	JFrame frame = new JFrame(); //Assigned the JFrame name
	public static void main(String[] args){
		EventQueue.invokeLater(new Runnable(){
		
			public void run(){
				try{
					login loginframe=new login();
					
					//new reservation(0.5);
					
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		});
	}
	
	 Timer timer;

    public void time(double seconds) {
        timer = new Timer();  //At this line a new Thread will be created
        timer.scheduleAtFixedRate(new checkTask(),1000 ,(int)(seconds*1000)); //delay in milliseconds
    }

    class checkTask extends TimerTask {

        @SuppressWarnings("deprecation")
		@Override
        public void run() {
        	Date d=new Date();
            Date d45=new Date();
            d45.setMinutes(d45.getMinutes()+45);
       for(int i =0;i<table;i++){
    	 
    	   if(datearr[i] != null){
    		   try{
    		  if(datearr[i].compareTo(d45)<0){
    			  btnNewButton[i].setBackground(new Color(16711680));
    		   //System.out.println(d45.getMinutes()+":"+d45.getSeconds()+" "+i +" "+datearr[i].getMinutes()+":"+datearr[i].getSeconds());
    		   
    		  }
    		 /* if(datearr[i].compareTo(d)<0){
    			  btnNewButton[i].setBackground(new Color(16776960));
    		   System.out.println(d45.getMinutes()+":"+d45.getSeconds()+" "+i +" "+datearr[i].getMinutes()+":"+datearr[i].getSeconds());
    		   
    		  }*/
    		   }catch(Exception e){
    			   
    		 } 
    	   }
       }
        }
    }

    private Preferences prefs;
    int[] state= new int[table];
    public void setPreference() {
      // This will define a node in which the preferences can be stored
      prefs = Preferences.userRoot().node(this.getClass().getName());
     
      // First we will get the values
      // Define a boolean value
      // Define a string with default "Hello World
      // Define a integer with default 50

String[] btn=new String[table];
String[] time=new String[table];
for(int i=0;i<table;i++){
	btn[i]=i+"";
	time[i]=table+i+"";
	prefs.putInt(btn[i], state[i]);
	if(datearr[i]!=null){
	prefs.putLong(time[i],datearr[i].getTime());
}else{
	prefs.putLong(time[i],0);
}
}
      // now set the values
      

      // Delete the preference settings for the first value
      
    }
    
	public  void initialize() {
		//setIconImage(Toolkit.getDefaultToolkit().getImage(main.class.getResource("/com/sun/java/swing/plaf/motif/icons/DesktopIcon.gif")));
		

		frame=new JFrame();
		frame.setTitle("Camry");
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(0,0,getToolkit().getScreenSize().width,getToolkit().getScreenSize().height);
		
		

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Asigned the close operator
		frame.addWindowListener(new WindowAdapter()
	    {
	        @Override
	        public void windowClosing(WindowEvent e)
	        {
	        	 /*int result = JOptionPane.showConfirmDialog(null,
			    		  "Do you want to shutdown the application?", "Shutdown?", JOptionPane.YES_NO_OPTION);
			      
			        if (result == JOptionPane.YES_OPTION) {	
	           setPreference();
	            e.getWindow().dispose();
			        }*/
	        }
	    });
	frame.setVisible(true);
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		JMenuItem mntmbill = new JMenuItem("New Bill");
		JMenuItem mntmsett = new JMenuItem("Settings");
		JMenuItem mntmreport = new JMenuItem("Reports");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setPreference();
				frame.dispose();
			}
		});
		mntmbill.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(table_bill_list.containsKey(0)){
					table_bill_list.remove(0);
					table_billno.remove(0);
				}
				
				BillFrame bill=new BillFrame("0");
				bill.setVisible(true);
			}
		});
		mntmsett.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				settings sett=new settings();
				
			}
		});
		mntmreport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ReportFrame report=new ReportFrame();
				report.setVisible(true);
			}
		});
		mnFile.add(mntmbill);
		mnFile.add(mntmsett);
		mnFile.add(mntmreport);
		mnFile.add(mntmExit);
		
		
		JMenu mnAdd = new JMenu("Add");
		menuBar.add(mnAdd);
		
		JMenuItem mnAddItem = new JMenuItem("Add Item");
		JMenuItem mnEditItem = new JMenuItem("Edit Item");
		JMenuItem mnaddcat = new JMenuItem("Add Category");
		
		mnAddItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFrame addItemframe=new addNewItem();
				 Preferences prefl = Preferences.userRoot().node("com.camray.bill.login$1");
				 if(!prefl.getBoolean("admin", false)){
				 PassFrame pframe=new PassFrame(addItemframe);
				 }else
				 {
					 addItemframe.setPreferredSize(new Dimension(300, 300));
					 addItemframe.setVisible(true); 
				 }
			}
		});
		mnEditItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFrame EditNewItem=new EditNewItem();
				Preferences prefl = Preferences.userRoot().node("com.camray.bill.login$1");
				 if(!prefl.getBoolean("admin", false)){
				 PassFrame pframe=new PassFrame(EditNewItem);
				 }else{
					 EditNewItem.setPreferredSize(new Dimension(300, 300));
					 EditNewItem.setVisible(true); 
				 }
			}
		});
		
		mnaddcat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFrame AddCategory=new AddCategory();
				Preferences prefl = Preferences.userRoot().node("com.camray.bill.login$1");
				 if(!prefl.getBoolean("admin", false)){
				 PassFrame pframe=new PassFrame(AddCategory);
				 }else{
					 AddCategory.setPreferredSize(new Dimension(300, 300));
					 AddCategory.setVisible(true); 
				 }
			}
		});
		mnAdd.add(mnAddItem);
		mnAdd.add(mnEditItem);
		mnAdd.add(mnaddcat);
		
		JMenu mnAbout = new JMenu("About");
		menuBar.add(mnAbout);
	
		}
	
	
}
