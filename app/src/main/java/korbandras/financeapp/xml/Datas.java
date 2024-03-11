package korbandras.financeapp.xml;

public class Datas {
    private int id;
    private String income;
    private String expenses;
    private String dueDate;
    private String targetSum;

    public Datas(String income, String expenses, String dueDate, String targetSum){
        this.income = income;
        this.expenses = expenses;
        this.dueDate = dueDate;
        this.targetSum = targetSum;
    }

    public Datas(int id, String income, String expenses, String dueDate, String targetSum){
        this.id = id;
        this.income = income;
        this.expenses = expenses;
        this.dueDate = dueDate;
        this.targetSum = targetSum;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    public String getExpenses() {
        return expenses;
    }

    public void setExpenses(String expenses) {
        this.expenses = expenses;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getTargetSum() {
        return targetSum;
    }

    public void setTargetSum(String targetSum) {
        this.targetSum = targetSum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
