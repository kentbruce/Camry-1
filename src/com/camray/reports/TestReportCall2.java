package com.camray.reports;


import java.sql.*;
import java.util.HashMap;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import com.camray.db.DBConnection;
public class TestReportCall2 {
 
    public static void main(String[] args) {
        Connection connection = null;
        Statement statement = null;
        DBConnection db=new DBConnection();
        try {
            connection = db.acquireConnection();
            statement = connection.createStatement();
            String fromdate="2014-09-06";
            String todate="2014-09-06";
            HashMap parameterMap = new HashMap();
            parameterMap.put("Title", "CAMRY");//sending the report title as a parameter.
            parameterMap.put("sub_title", "Item wise sales report from "+fromdate+" to "+todate);
            parameterMap.put("start_date", fromdate);//sending the report title as a parameter.
            parameterMap.put("end_date",todate);//sending the report title as a parameter.
           JasperDesign jasperDesign = JRXmlLoader.load("E:\\itemreport.jrxml");
       		JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            Report rpt = new Report(parameterMap, connection,jasperDesign,jasperReport);
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
}
