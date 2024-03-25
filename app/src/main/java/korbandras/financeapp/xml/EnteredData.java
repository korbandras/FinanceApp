package korbandras.financeapp.xml;

import android.content.Intent;

import korbandras.financeapp.pages.Loading;
import korbandras.financeapp.pages.NewData;

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
