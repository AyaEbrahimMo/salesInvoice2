package com.sales.view;

import java.awt.GridLayout;
import javax.swing.JDialog;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;

public class InvoiceDialog extends JDialog {
    private JTextField custNameField ;
    private JTextField invDateField ;
    private JLabel custNameLabl;
    private JLabel invDateLabl ;
    private JButton okBtn;
    private JButton cancleBtn;
    
    
    public InvoiceDialog (InvoiceFrame frame)
    {
        custNameLabl = new JLabel ("Customer Name ");
        custNameField = new JTextField (20);
        invDateLabl = new JLabel ("Invoice Date");
        invDateField = new JTextField (20);
        okBtn = new JButton ("Ok");
        cancleBtn = new JButton ("Cancle");
        
        okBtn.setActionCommand("createInvoiceOK");
        cancleBtn.setActionCommand("createInvoiceCancle");
        
        okBtn.addActionListener(frame.getController());
        cancleBtn.addActionListener(frame.getController());
        setLayout(new GridLayout(3,2));
        
        add (invDateLabl);
        add (invDateField);
        add (custNameLabl);
        add (custNameField);
        add (okBtn);
        add (cancleBtn);
        
        pack ();


        
        
        
    }
    
    public JTextField getCustNameField()
    {
        return custNameField;
    }
    
      public JTextField getInvDateField()
    {
        return invDateField;
    }
    
    
}
