package korbandras.financeapp.xml;


public class EnteredData {
    public String income;
    public String expenses;
    public String dueDate;
    public String targetSum;

    public EnteredData(String income, String expenses, String dueDate, String targetSum){
        this.income = income;
        this.expenses = expenses;
        this.dueDate = dueDate;
        this.targetSum = targetSum;
    }
}
