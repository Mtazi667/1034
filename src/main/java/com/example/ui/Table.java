package com.example.ui;

import java.util.ArrayList;

public class Table {
    private String title;
    private final String[] columns;
    private ArrayList<String[]> data;
    public Table(String title, ArrayList<String[]> data, String[] columns) {
        this.title = title;
        this.data = data;
        this.columns = columns;
    }
    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String[] getColumns(){
        return columns;
    }

    public ArrayList<String[]> getData(){
        return data;
    }
    public void setData(ArrayList<String[]> data){
        this.data = data;
    }
    public void addRow(String[] row){
        data.add(row);
    }
    public void removeRow(int index){
        data.remove(index);
    }


}
