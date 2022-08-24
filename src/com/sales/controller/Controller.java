package com.sales.controller;

import com.sales.model.Invoice;
import com.sales.model.InvoicesTableModel;
import com.sales.model.Line;
import com.sales.model.LinesTableModel;
import com.sales.view.InvoiceDialog;
import com.sales.view.InvoiceFrame;
import com.sales.view.LineDialog;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Controller implements ActionListener ,ListSelectionListener{

    private InvoiceFrame frame;
    private InvoiceDialog invoiceDialog;
    private LineDialog lineDialog;
    public Controller (InvoiceFrame frame)
    {
        this.frame = frame;
    }

    public Controller(LineDialog lineDialog) {
        this.lineDialog = lineDialog;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand =e.getActionCommand();
        System.out.println("Action:" +actionCommand);
        switch(actionCommand)
        {
            case "Load File":
                loadFile();
             break;
             case "Save File":
                 saveFile();
               break;
             case "Create New Invoice":
                 createNewInvoice();
              break;
             case "Delete Invoice":
                 deleteInvoice();
             break;
             case "Create New Item":
                 createNewItem();
            break;
             case "Delete Item":
                 deleteItem();
            break;
             case "createInvoiceOK" :
                  createInvoiceOK();
                 break;
             case "createInvoiceCancle" :
                 createInvoiceCancle();
                 break;
             case "createLineOK" :
                 createLineOK();
                 break;
             case "createLineCancel" :
                 createLineCancel();
                 break;
                 
                     
        }
    }
     @Override
    public void valueChanged(ListSelectionEvent e) {
        int selectedIndex=frame.getInvoiceTable().getSelectedRow();
        if (selectedIndex != -1)
        {
        System.out.println("you have sleceted row :" + selectedIndex);
        Invoice currentInvoice =frame.getInvoices().get(selectedIndex);
        frame.getInvoiceNumLabel().setText(""+ currentInvoice.getNum());
        frame.getInvoiceDateLabel().setText(currentInvoice.getDate());
        frame.getCustomerNameLabel().setText(currentInvoice.getCustomerName());
        frame.getInvoiceTotalLabel().setText("" +currentInvoice.getInvoiceTotal());
        LinesTableModel linesTableModel = new LinesTableModel(currentInvoice.getLines());
        frame.getLineTable().setModel(linesTableModel);
        linesTableModel.fireTableDataChanged();
        }
        
    }
    

    private void loadFile() {
        JFileChooser jfc = new JFileChooser();
        try
        {
        int result = jfc.showOpenDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION)
        {
           File headerFile = jfc.getSelectedFile();
           Path headerPath = Paths.get(headerFile.getAbsolutePath());
           List<String> headerLines =Files.readAllLines(headerPath);
           System.out.println("Invoices have been read");
           //1,22-11-2020,ali
           //2,13-10-2021,Saleh
           ArrayList<Invoice> invoicesArray = new ArrayList<>();

           for(String headerLine: headerLines)
           {
               String []  headerParts  = headerLine.split(",");
               int invoiceNum = Integer.parseInt(headerParts[0]);
               String invoiceDate = headerParts[1];
               String customerName =headerParts[2];
//               
            Invoice invoice = new Invoice(invoiceNum,invoiceDate,customerName );
            invoicesArray.add(invoice);
           }
            System.out.println("Check Points ");
          
            result =jfc.showOpenDialog(frame);
           if (result == JFileChooser.APPROVE_OPTION)
           {
               File lineFile =jfc.getSelectedFile();
               Path linePath = Paths.get(lineFile.getAbsolutePath());
               List<String> lineLines = Files.readAllLines(linePath);
               System.out.println("Lines have been read");
               for(String lineLine : lineLines)
               {
                  String lineParts[] =lineLine.split(",");
                  int invoiceNum=Integer.parseInt(lineParts[0]) ;
                  String itemName =lineParts[1];
                  double itemPrice=Double.parseDouble(lineParts[2]) ;
                  int count =Integer.parseInt(lineParts[3]);
                  Invoice inv = null;
                  for (Invoice invoice : invoicesArray)
                  {
                      if (invoice.getNum() == invoiceNum )
                      {
                          inv = invoice;
                          break;
                      }
                  }
        Line line = new Line (itemName , itemPrice ,count ,inv) ;
        inv.getLines().add(line);
               }
                System.out.println("check points");
           }
           frame.setInvoices(invoicesArray);
           InvoicesTableModel invoicesTableModel = new InvoicesTableModel(invoicesArray);
           frame.setInvoicesTableModel(invoicesTableModel);
           frame.getInvoiceTable().setModel(invoicesTableModel);
           frame.getInvoicesTableModel().fireTableDataChanged();
        }
        }
        
        
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }

    private void saveFile()  {
       ArrayList<Invoice> invoices = frame.getInvoices();
       String headers = "";
       String lines = "";
       for (Invoice invoice : invoices)
       {
           String invCSV = invoice.getAsCSV();
           headers +=invCSV;
           headers += "\n";
           for (Line line : invoice.getLines())
           {
               String lineCSV =line.getAsCSV();
               lines +=lineCSV;
               lines +="\n";
           }
           
       }
       System.out.println("Check Out");
       try{
       JFileChooser jfc = new JFileChooser ();
      int result = jfc.showSaveDialog(frame);
      if (result == JFileChooser.APPROVE_OPTION ){
          File headerFile = jfc.getSelectedFile();
          FileWriter hfw = new FileWriter (headerFile);
          hfw.write(headers);
          hfw.flush();
          hfw.close();
          
          result = jfc.showSaveDialog(frame);
          if (result == JFileChooser.APPROVE_OPTION ){
              File lineFile =jfc.getSelectedFile();
              FileWriter lfw = new FileWriter (lineFile);
                lfw.write(lines);
                lfw.flush();
                lfw.close();
          
          
          }
          
          }
          
      }
       catch (Exception ex){}
    }

    private void createNewInvoice() {
        invoiceDialog = new InvoiceDialog (frame);
        invoiceDialog.setVisible(true);
        
        
        
    }

    private void deleteInvoice() {
        int selectedRow = frame.getInvoiceTable().getSelectedRow();
        if (selectedRow != -1)
        {
            frame.getInvoices().remove(selectedRow);
            frame.getInvoicesTableModel().fireTableDataChanged();
        }
    }

    private void createNewItem() {
        lineDialog = new LineDialog (frame);
        lineDialog.setVisible(true);
        
       
    }

    private void deleteItem() {
       
       int selectedRow = frame.getLineTable().getSelectedRow();
        if (selectedRow != -1)
        {
//            Invoice invoice = frame.getInvoices().get(selectedInv);
//            invoice.getLines().remove(selectedRow);
//            LinesTableModel linesTableModel = new LinesTableModel (invoice.getLines());
//            frame.getLineTable().setModel(linesTableModel);
            LinesTableModel linesTableModel = (LinesTableModel) frame.getLineTable().getModel();
            linesTableModel.getLines().remove(selectedRow);
            linesTableModel.fireTableDataChanged();
            frame.getInvoicesTableModel().fireTableDataChanged();

            
           
        }
    }

    private void createInvoiceOK() {
        String date =invoiceDialog.getInvDateField().getText();
        String customer =invoiceDialog.getCustNameField().getText();
        int num =frame.getNextInvoiceNum();
        Invoice invoice = new Invoice (num , date , customer);
        frame.getInvoices().add(invoice);
        frame.getInvoicesTableModel().fireTableDataChanged();
        invoiceDialog.setVisible(false);
        invoiceDialog.dispose();
        invoiceDialog = null ;
    }

    private void createInvoiceCancle() {
     invoiceDialog.setVisible(false);
     invoiceDialog.dispose();
     invoiceDialog = null;

    }

    private void createLineOK() {
       String item = lineDialog.getItemNameField().getText();
       String countStr =lineDialog.getItemCountField().getText();
       String priceStr =lineDialog.getItemPriceField().getText();
       int count = Integer.parseInt(countStr);
       double price = Double.parseDouble(priceStr);
       int selectedInvoice = frame.getInvoiceTable().getSelectedRow();
       if (selectedInvoice != -1){
           Invoice invoice = frame.getInvoices().get(selectedInvoice);
           Line line = new Line (item , price ,count , invoice);
           invoice.getLines().add(line);
           LinesTableModel linesTableModel = (LinesTableModel) frame.getLineTable().getModel();
            linesTableModel.fireTableDataChanged();
            frame.getInvoicesTableModel().fireTableDataChanged();

           
       
       }
        
        lineDialog.setVisible(false);
        lineDialog.dispose();
        lineDialog = null ;
       
    }

    private void createLineCancel() {
        lineDialog.setVisible(false);
        lineDialog.dispose();
        lineDialog = null ;
    }
    
}
