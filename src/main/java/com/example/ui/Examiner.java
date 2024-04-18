package com.example.ui;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

public class Examiner {

    private final String id;
    private final String parentId;
    private final Calendar addDate;
    private String progress;
    private String fullName;
    private String gender;
    private final ArrayList<String> niveaux;
    private ArrayList<Niveau> niveauData;
    private int age;
    public Examiner(String parentId, String id, String fullName, String gender, ArrayList<String> niveaux, int age) {
        this.id = id;
        this.parentId = parentId;
        this.addDate = Calendar.getInstance();
        this.fullName = fullName;
        this.gender = gender;
        this.niveaux = niveaux;
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
    public String getNiveaux(){
        // String separated by -
        String niveauxStr = "";
        for (String niveau : this.niveaux){
            niveauxStr += niveau;
            if (this.niveaux.indexOf(niveau) != this.niveaux.size()-1){
                niveauxStr += " - ";
            }
        }
        return niveauxStr;
    }
    public void setNiveaux(ArrayList<String> niveaux){
        this.niveaux.clear();
        this.niveaux.addAll(niveaux);
    }
    public void setAge(int age){
        this.age = age;
        if (this.niveauData == null) {
            this.niveauData = new ArrayList<>();
        }
        for (Niveau niveau : niveauData){
            niveau.setAge(age);
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
    public ArrayList<String> getNiveauxArray(){
        return niveaux;
    }
    public void addNiveau(String niveau){
        niveaux.add(niveau);
        niveauData.add(new Niveau(niveau, age, ""));
    }

    public void fillNiveauData(String niveau, Map<String, String> inputData){
        boolean found = false;
        for (Niveau t : niveauData){
            if (t.getName().equals(niveau)){
                t.setInputData(inputData);
                found = true;
                break;
            }
        }
        if (!found){
            Niveau newNiveau = new Niveau(niveau, age, "");
            newNiveau.setInputData(inputData);
            niveauData.add(newNiveau);
        }
    }
    public void removeNiveauData(String niveau){
        for (Niveau t : niveauData){
            if (t.getName().equals(niveau)){
                niveauData.remove(t);
                break;
            }
        }
    }

    public void removeNiveau(String niveau){
        niveaux.remove(niveau);
    }
    public String getProgress(){
        return progress;
    }
    public void setProgress(String progress){
        this.progress = progress;
    }

}
