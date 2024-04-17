package com.example.ui;

import java.util.Calendar;

public class User {
    private final String id;
    private final String parentId;
    private String fullName;
    private final Calendar addDate;
    private String role;
    private String status;
    private String PIN;
    public User(String id,String fullName, String role, String parentId) {
        this.id = id;
        this.parentId = parentId;
        this.fullName = fullName;
        this.role = role;
        this.addDate = Calendar.getInstance();
        this.status = "Active";
        this.PIN = "0000";
    }
    public String getParentId(){
        return parentId;
    }
    public String getId(){
        return id;
    }
    public boolean verifyPIN(String PIN){
        return this.PIN.equals(PIN);
    }
    public void setPIN(String oldPIN, String newPIN){
        if (verifyPIN(oldPIN)){
            this.PIN = newPIN;
        }

    }
    public String getFullName(){
        return fullName;
    }
    public void setFullName(String fullName){
        this.fullName = fullName;
    }
    public Calendar getAddDate(){
        return addDate;
    }
    public String getRole(){
        return role;
    }
    public void setRole(String role){
        this.role = role;
    }
    public String getStatus(){
      return status;
    }
    public void setStatus(String status){
        this.status = status;
    }


}
