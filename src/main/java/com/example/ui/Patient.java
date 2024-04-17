package com.example.ui;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

public class Patient {
    private final String id;
    private final String parentId;
    private final Calendar addDate;
    private String progress;
    private String fullName;
    private String gender;
    private final ArrayList<String> tests;
    private ArrayList<Test> testData;
    private int age;
    public Patient(String parentId, String id, String fullName, String gender, ArrayList<String> tests, int age) {
        this.id = id;
        this.parentId = parentId;
        this.addDate = Calendar.getInstance();
        this.fullName = fullName;
        this.gender = gender;
        this.tests = tests;
        this.progress = "In progress";
        this.age = age;
    }
    public String getId(){
        return id;
    }
    public String getParentId(){
        return parentId;
    }
    public String getFullName(){
        return fullName;
    }
    public void setFullName(String fullName){
        this.fullName = fullName;
    }
    public int getAge(){
        return age;
    }
    public String getTests(){
        // String separated by -
        String testsStr = "";
        for (String test : this.tests){
            testsStr += test;
            if (this.tests.indexOf(test) != this.tests.size()-1){
                testsStr += " - ";
            }
        }
        return testsStr;
    }
    public void setTests(ArrayList<String> tests){
        this.tests.clear();
        this.tests.addAll(tests);
    }
    public void setAge(int age){
        this.age = age;
        if (this.testData == null) {
            this.testData = new ArrayList<>();
        }
        for (Test test : testData){
            test.setAge(age);
        }
    }
    public Calendar getAddDate(){
        return addDate;
    }
    public String getGender(){
        return gender;
    }
    public void setGender(String gender){
        this.gender = gender;
    }
    public ArrayList<String> getTestsArray(){
        return tests;
    }
    public void addTest(String test){
        tests.add(test);
        testData.add(new Test(test, age, ""));
    }

    public void fillTestData(String test, Map<String, String> inputData){
        boolean found = false;
        for (Test t : testData){
            if (t.getName().equals(test)){
                t.setInputData(inputData);
                found = true;
                break;
            }
        }
        if (!found){
            Test newTest = new Test(test, age, "");
            newTest.setInputData(inputData);
            testData.add(newTest);
        }
    }
    public void removeTestData(String test){
        for (Test t : testData){
            if (t.getName().equals(test)){
                testData.remove(t);
                break;
            }
        }
    }

    public void removeTest(String test){
        tests.remove(test);
    }
    public String getProgress(){
        return progress;
    }
    public void setProgress(String progress){
        this.progress = progress;
    }

}
