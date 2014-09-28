package com.camray.reports;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

import com.camray.db.DBConnection;

public class ReportFrame extends JFrame {
	JLabel reportname;
	JLabel datelb;
	JLabel to;
	JTextField itemNametf = new JTextField(20);;
	JTextField itemPricetf = new JTextField(20);
	JPanel panel;
	JButton generate;
	JComboBox reportCat;
	Connection connection;
	Statement statement;
	DBConnection db=new DBConnection();
	UtilDateModel model1 ;
	UtilDateModel model2 ;
	JDatePanelImpl datePanel1 ;
	JDatePanelImpl datePanel2 ;
	JDatePickerImpl datePicker1;
	JDatePickerImpl datePicker2;
	 GridBagConstraints gbc;
	 public ReportFrame(){
		 
		 init();
		 generate.addActionListener(new ActionListener() {
			 
			
				public void actionPerformed(ActionEvent arg0) {
					SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
					 Date selectedDate1 = (Date) datePicker1.getModel().getValue();
					 Date selectedDate2 = (Date) datePicker2.getModel().getValue();
					 if ((selectedDate1!=null) && (selectedDate2!=null))
			         {
					 String fromdate=sf.format(selectedDate1);
			         String todate=sf.format(selectedDate2);
			        
					if(reportCat.getSelectedItem().toString().equals("Bill Report")){
						try {
							
				            connection = db.acquireConnection();
				            statement = connection.createStatement();
				             HashMap parameterMap = new HashMap();
				            parameterMap.put("Title", "CAMRY");//sending the report title as a parameter.
				            parameterMap.put("sub_title", "Bill report from "+fromdate+" to "+todate);
				            parameterMap.put("start_date", fromdate);//sending the report title as a parameter.
				            parameterMap.put("end_date",todate);//sending the report title as a parameter.
				          // JasperDesign jasperDesign = JRXmlLoader.load("C:/Program Files/camry/templates/BillReport.jrxml");
				       	//JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
				           JasperReport jasperReport = (JasperReport)JRLoader.loadObject(new File("C:/Program Files/camry/templates/BillReport.jasper"));
				           Report rpt = new Report(parameterMap, connection,jasperReport);
				            rpt.setReportName("productlist"); //productlist is the name of my jasper file.
				            rpt.callReport();
				            
				            
				        } catch (Exception e) {
				            e.printStackTrace();
				        } finally {
				            try {
				                statement.close();
				                connection.close();
				            } catch (Exception e) {
				                e.printStackTrace();
				            }
				        }
						
					}else {
						 try {
							 
					            connection = db.acquireConnection();
					            statement = connection.createStatement();
					            HashMap parameterMap = new HashMap();
					            parameterMap.put("Title", "CAMRY");//sending the report title as a parameter.
					            parameterMap.put("sub_title", "Item wise sales report from "+fromdate+" to "+todate);
					            parameterMap.put("start_date", fromdate);//sending the report title as a parameter.
					            parameterMap.put("end_date",todate);//sending the report title as a parameter.
					           //JasperDesign jasperDesign = JRXmlLoader.load("C:/Program Files/camry/templates/itemreport.jrxml");
					            JasperReport jasperReport = (JasperReport)JRLoader.loadObject(new File("C:/Program Files/camry/templates/itemreport.jasper"));
					            //JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
					            Report rpt = new Report(parameterMap, connection,jasperReport);
					            rpt.setReportName("productlist"); //productlist is the name of my jasper file.
					            rpt.callReport();
					            
					            
					        } catch (Exception e) {
					            e.printStackTrace();
					        } finally {
					            try {
					                statement.close();
					                connection.close();
					            } catch (Exception e) {
					                e.printStackTrace();
					            }
					        }
						
					}
				}else{
					JFrame fr=new JFrame();
					JOptionPane.showMessageDialog(fr,
						    "Please both dates");
				}
				}
			});
	 }
public void init(){
	gbc = new GridBagConstraints(); 
	gbc.insets = new Insets(2,2,2,2);  
	panel=new JPanel();
	reportname=new JLabel("Report Type");
	datelb=new JLabel("Date");
	to=new JLabel("to");
	generate=new JButton("Generate");
	reportCat=new JComboBox<String>();
	reportCat.addItem("Bill Report");
	reportCat.addItem("Item wise Report");
	gbc.anchor = GridBagConstraints.EAST;  
	gbc.gridwidth = GridBagConstraints.RELATIVE; 
	panel.add(reportname,gbc);
	gbc.anchor = GridBagConstraints.WEST;  
	gbc.gridwidth = GridBagConstraints.REMAINDER;  
	panel.add(reportCat,gbc);
	gbc.anchor = GridBagConstraints.EAST;  
	gbc.gridwidth = GridBagConstraints.RELATIVE;  
	panel.add(datelb,gbc);
 model1 = new UtilDateModel();
	 model2 = new UtilDateModel();
	datePanel1 = new JDatePanelImpl(model1);
	datePanel2 = new JDatePanelImpl(model2);
	 datePicker1 = new JDatePickerImpl(datePanel1);
	datePicker2 = new JDatePickerImpl(datePanel2);
	panel.add(datePicker1);
	panel.add(to);
	panel.add(datePicker2);
	panel.add(generate);
	setSize(400,400);
	add(panel);
	
	
	
	
	
	
}
}
