package com.company;

public class bankAccount implements Comparable{
    public String name;
    public double balance;

    public bankAccount(String nm, double amt){
        name = nm;
        balance = amt;
    }

    public int compareTo(Object otherObject){
        bankAccount otherAccount = (bankAccount) otherObject;
        int retValue;
        if(balance > otherAccount.balance)
            retValue = -1;
        else{
            if(balance < otherAccount.balance)
                retValue = 1;
            else
                retValue = 0;
        }
        return retValue;
    }

    public void deposit(double dp){
        balance = balance + dp;
    }

    public void withdraw(double wd){
        balance = balance - wd;
    }



}