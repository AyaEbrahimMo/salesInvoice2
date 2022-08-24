
package com.sales.model;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class LinesTableModel extends AbstractTableModel {
    private ArrayList<Line> lines;
    private String [] columns= {"No." ,"Item Name" ,"Price","Count" ,"Total"};

    public LinesTableModel() {
    }

    public LinesTableModel(ArrayList<Line> lines) {
        this.lines = lines;
    }

    public ArrayList<Line> getLines() {
        return lines;
    }
    
    
    
    
    @Override
    public int getRowCount() {
        return lines.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

     public String getColumnName(int column){
        return columns[column];
   
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Line line = lines.get(rowIndex);
        switch (columnIndex){
            case 0 : return line.getInvoice().getNum();
            case 1 : return line.getItem();
            case 2 : return line.getPrice();
            case 3 : return line.getCount();
            case 4 : return line.getLineTotal();
            default: return "";
        }
    }
    
}
