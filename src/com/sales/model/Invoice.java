package com.sales.model;

import java.util.ArrayList;

public class Invoice {
    private int num ;
    private String date;
    private String customerName;
    private ArrayList<Line> lines ;
 

    public Invoice() { }

    public Invoice(int num, String date, String customerName) {
        this.num = num;
        this.date = date;
        this.customerName = customerName;
    }
    public double getInvoiceTotal()
    {
        double total =0.0;
        for (Line line : getLines())
        {
            total += line.getLineTotal();
        }
        return total;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<Line> getLines() {
        if (lines == null)
        {
            lines = new ArrayList<>();
        }
        return lines;
    }

    @Override
    public String toString() {
        return "Invoice{" + "num=" + num + ", date=" + date + ", customerName=" + customerName + '}';
    }
    
    public String getAsCSV ()
    {
        return num + "," + date + "," + customerName;
    }
    
    
    
    
}
