package korbandras.financeapp.xml;

public class Datas {
    private int income;
    private int expenses;
    private int dueDate;
    private int targetSum;

    public Datas(int income, int expenses, int dueDate, int targetSum){
        this.income = income;
        this.expenses = expenses;
        this.dueDate = dueDate;
        this.targetSum = targetSum;
    }

    public int getIncome() {
        return income;
    }

    public void setIncome(int income) {
        this.income = income;
    }

    public int getExpenses() {
        return expenses;
    }

    public void setExpenses(int expenses) {
        this.expenses = expenses;
    }

    public int getDueDate() {
        return dueDate;
    }

    public void setDueDate(int dueDate) {
        this.dueDate = dueDate;
    }

    public int getTargetSum() {
        return targetSum;
    }

    public void setTargetSum(int targetSum) {
        this.targetSum = targetSum;
    }
}
