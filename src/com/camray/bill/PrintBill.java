package com.camray.bill;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.JTable;

public class PrintBill{
    JTable jTable1;
    Bill bill;
    int tabno;
    long billnum;
    float tax;
    List<Bill> billist;
    float totalamt;
    float disc;
    PrintBill(){
    
    }
    
    PrintBill(List<Bill> billist,long billnum,int tabno,float tax,float disc){
        String columns[] = new String[] {"Name","Quantity","Rate","Amount"};
        this.billist=billist;
        this.billnum=billnum;
        this.tabno=tabno;
        this.tax=tax;
        this.disc=disc;
        
       // jTable1 = new JTable(new ExampleTableModel(data,columns));
    }
    public static void main(String h[]){
        PrintBill t=new PrintBill();
        t.printThisBill();
    }
    public void printThisBill() {
       // TableModel mod = jTable1.getModel();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        DateFormat timeFormat = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        String Date = dateFormat.format(date);
        String Time = timeFormat.format(date);

         String Header = 
        "****************Camray************\n"
        + "Date: "+Date+"     Table No: "+tabno+"\n"
        + "Bill no: "+billnum+"\n"
        + "----------------------------------------\n"
        + "Name          Qty    Rate     Amt\n"
        + "----------------------------------------\n";
        String billstring = Header;
        
        /*do{
            String itmname =     ""+ mod.getValueAt(i, 0);
            String qty =      ""+mod.getValueAt(i, 1);
            String rate =     ""+mod.getValueAt(i, 2);
            String amount =   ""+mod.getValueAt(i, 3);  
            if(name.length() > 15){
                name = name.substring(0, 15)+"  ";
            }   
            rate = rate;
            //String items = name+"\t"+qty+"\t"+rate+"\t"+amount+"\n";
            String items = name+"\t"+qty+"\t"+rate+"\t"+amount+"\n";
            billstring = billstring+ items;
            amt1=Double.parseDouble(amount)+amt1;
            i++;
        }while(i <= billist.size()-1);*/
        for (Bill value : billist ){
        	String itmname =     ""+value.getItmName();
        	String qty =      ""+value.getItmqnty();
        	String rate =     ""+value.getItmprice();
        	String amt =      ""+(value.getItmprice()*value.getItmqnty());
        	if(itmname.length() > 15){
        		itmname = itmname.substring(0, 15)+"  ";
            } 
        	else{
        		for (int i=0; i<(15-itmname.length());i++)
        		itmname=itmname.concat(" ");
        	}
        	String items = itmname+"\t"+qty+"\t"+rate+"\t"+amt+"\n";
    		totalamt=totalamt+Float.parseFloat(amt);
    		billstring = billstring+ items;
    	}
        float taxamt=(totalamt*tax/100);
        float discamt=totalamt*disc/100;
        totalamt=totalamt+taxamt-discamt;
       billstring=billstring + "------------------------------------------\n";
        String amt=    "\nTotal Amount = "+totalamt+"\n"
        + "Tax :"+tax+"%  =  " +taxamt+"\n"
        + "Discount :"+disc+"%  =  " +discamt+"\n"
        + "*********************************\n"
        + "Thank you. \n";
        billstring = billstring+amt;
        System.out.println(billstring);
        printCard(billstring);
        //dispose();
    }
    public static void printCard(final String billstring ){
        Printable contentToPrint = new Printable(){
            @Override
            public int print(Graphics graphics, PageFormat pageFormat, int page) throws PrinterException {
                if (page > 0) {
                    return NO_SUCH_PAGE;
                }
                pageFormat.setOrientation(PageFormat.LANDSCAPE);
                Graphics2D g2d = (Graphics2D)graphics.create();
                g2d.setPaint(Color.black);
                g2d.setFont(new Font("Arial", Font.BOLD, 10));
                g2d.translate(pageFormat.getImageableX(),      pageFormat.getImageableX());
                g2d.drawString(billstring, 0, 0); 
                return PAGE_EXISTS;
            }   
        };

        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPrintable(contentToPrint);
        try {
            job.print();
        } catch (PrinterException e) {
            System.err.println(e.getMessage());
        }
    }   
}
