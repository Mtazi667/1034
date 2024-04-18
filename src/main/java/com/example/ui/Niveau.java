package com.example.ui;

import java.util.ArrayList;
import java.util.Map;
public class Niveau {

    private final String name;
    private String description;
    private Map<String, String> inputData;
    private ArrayList<Table> outputData;
    private int age;
    public Niveau(String name, int age, String description) {
        this.name = name;
        this.description = description;
        this.age = age;
    }
    public String getName(){
        return name;
    }
    public Integer getAge(){
        return age;
    }
    public void setAge(int age){
        this.age = age;
        ProcessInputData();
    }
    public String getDescription(){
        return description;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public Map<String, String> getInputData(){
        return inputData;
    }
    public void setInputData(Map<String, String> inputData){
        this.inputData = inputData;
        ProcessInputData();
    }
    public ArrayList<Table> getOutputData(){
        return outputData;
    }
    private String getPerformance(int score){
        if (score < 5){
            return "Needs improvement";
        } else if (score < 10){
            return "Average";
        } else {
            return "Excellent";
        }
    }
    private void processTestA(){
        outputData = new ArrayList<>();
        String[] columns = {"subtest", "key", "score", "descriptive term"};
        int EH = Integer.parseInt(inputData.get("EH"));
        int CO = Integer.parseInt(inputData.get("CO"));
        int FG = Integer.parseInt(inputData.get("FG"));
        int VC = Integer.parseInt(inputData.get("VC"));
        int FC = Integer.parseInt(inputData.get("FC"));
        String[] row1 = {"Eye-hand-coordination", "EH", String.valueOf(EH),String.valueOf(EH*age/2), getPerformance(EH)};
        String[] row2 = {"Coloring", "CO", String.valueOf(CO),String.valueOf(CO*age/2), getPerformance(CO)};
        String[] row3 = {"Finger-grasp", "FG", String.valueOf(FG),String.valueOf(FG*age/4), getPerformance(FG)};
        String[] row4 = {"Visual-closure", "VC", String.valueOf(VC),String.valueOf(VC*age/3), getPerformance(VC)};
        String[] row5 = {"Form-constancy", "FC", String.valueOf(FC),String.valueOf(FC*age/3), getPerformance(FC)};
        ArrayList<String[]> data = new ArrayList<>();
        data.add(row1);
        data.add(row2);
        data.add(row3);
        data.add(row4);
        data.add(row5);
        Table table = new Table("Subtest", data, columns);
        int VMI = EH + FG + VC ;
        int MVP = CO + FC;
        int GVP = EH+VC+FC;
        int IQ = VMI + MVP;
        String[] row6 = {"VMI", "visual-motor integration", String.valueOf(VMI),String.valueOf(VMI*age/7), getPerformance(VMI)};
        String[] row7 = {"MVP", "motor-reduced visual perception", String.valueOf(MVP),String.valueOf(MVP*age/3), getPerformance(MVP)};
        String[] row8 = {"GVP", "general visual perception", String.valueOf(GVP),String.valueOf(GVP*age/5), getPerformance(GVP)};
        String[] row9 = {"IQ", "intelligence quotient", String.valueOf(IQ),String.valueOf(IQ*age/8), getPerformance(IQ)};
        ArrayList<String[]> data2 = new ArrayList<>();
        data2.add(row6);
        data2.add(row7);
        data2.add(row8);
        data2.add(row9);
        Table table2 = new Table("Composite score", data2, columns);
        outputData.add(table);
        outputData.add(table2);
    }
    private void processTestB(){
        outputData = new ArrayList<>();
        String[] columns = {"subtest", "key", "score", "descriptive term"};
        int SIM = Integer.parseInt(inputData.get("SIM"));
        int VOC = Integer.parseInt(inputData.get("VOC"));
        int INF = Integer.parseInt(inputData.get("INF"));
        int COM = Integer.parseInt(inputData.get("COM"));
        int FC = Integer.parseInt(inputData.get("FC"));
        String[] row1 = {"similarities", "SIM", String.valueOf(SIM),String.valueOf(SIM*age/4), getPerformance(SIM)};
        String[] row2 = {"vocabulary", "VOC", String.valueOf(VOC),String.valueOf(VOC*age/2), getPerformance(VOC)};
        String[] row3 = {"information", "INF", String.valueOf(INF),String.valueOf(INF*age/1), getPerformance(INF)};
        String[] row4 = {"comprehension", "COM", String.valueOf(COM),String.valueOf(COM*age/3), getPerformance(COM)};
        String[] row5 = {"Form-constancy", "FC", String.valueOf(FC),String.valueOf(FC*age/4), getPerformance(FC)};
        ArrayList<String[]> data = new ArrayList<>();
        data.add(row1);
        data.add(row2);
        data.add(row3);
        data.add(row4);
        data.add(row5);
        Table table = new Table("Subtest", data, columns);
        int ICV = SIM + COM + COM ;
        int IVS = VOC + FC;
        int IRF = SIM+COM+FC;
        int IQ = ICV + IVS + IRF;
        String[] row6 = {"ICV", "verbal comprehension", String.valueOf(ICV),String.valueOf(ICV*age/4), getPerformance(ICV)};
        String[] row7 = {"IVS", "visual spatial", String.valueOf(IVS),String.valueOf(IVS*age/6), getPerformance(IVS)};
        String[] row8 = {"IRF", "fluid reasoning", String.valueOf(IRF),String.valueOf(IRF*age/3), getPerformance(IRF)};
        String[] row9 = {"IQ", "intelligence quotient", String.valueOf(IQ),String.valueOf(IQ*age/9), getPerformance(IQ)};
        ArrayList<String[]> data2 = new ArrayList<>();
        data2.add(row6);
        data2.add(row7);
        data2.add(row8);
        data2.add(row9);
        Table table2 = new Table("Composite score", data2, columns);
        outputData.add(table);
        outputData.add(table2);
    }
    private void ProcessInputData(){
        switch (name){
            case "Test-A":
                processTestA();
                break;
            case "Test-B":
                processTestB();
                break;
        }

    }
}
