package com.company;

import java.util.*;
import java.io.*;
import java.text.*;

public class Main{
    public static void main(String[] args) throws IOException{
        FileWriter fw = new FileWriter("totalyNotHackingStopLooking.txt");
        PrintWriter pw = new PrintWriter(fw);
        pw.println("Name:    Deposit:");
        FileWriter fileLog = new FileWriter("notALogStopLooking.txt");
        PrintWriter printLog = new PrintWriter(fileLog);
        NumberFormat fmt = NumberFormat.getNumberInstance();
        fmt.setMinimumFractionDigits(2);
        fmt.setMaximumFractionDigits(2);
        ArrayList<bankAccount> aryLst = new ArrayList<>();
        ArrayList<bankAccount> deposits = new ArrayList<>();
        String name;

        do{
            Scanner kbReader = new Scanner(System.in);
            System.out.print("Please enter the name to whom the account belongs. (Exit to end the program) ");
            name = kbReader.nextLine();
            name = name.toLowerCase();
            if(name.equals("debug")){
                printLog.println(getTimeStamp() + ": Debug mode entered.");
                System.out.print("Would you like to retrieve a listing of Deposits, Accounts, or Drain an account? ");
                System.out.println("Please input Deposit, Accounts or Drain");
                String action = kbReader.nextLine();
                action = action.toLowerCase();
                if(action.equals("deposit")){
                    Collections.sort(deposits);
                    for(int i = 0; i < deposits.size(); i++){
                        System.out.println((i + 1) + ": " + deposits.get(i).name + " - $" + fmt.format(deposits.get(i).balance));
                    }
                    printLog.println(getTimeStamp() + ": Deposits listed from greatest to least");
                }
                else if(action.equals("account")){
                    Collections.sort(aryLst);
                    for(int i = 0; i < aryLst.size(); i++){
                        System.out.println((i + 1) + ": " + aryLst.get(i).name + " - $" + fmt.format(aryLst.get(i).balance));
                    }
                    printLog.println(getTimeStamp() + ": Accounts listed from greatest to least");
                }
                if(action.equals("drain")){
                    bankAccount max = aryLst.get(0);
                    for(int i = 1; i < aryLst.size(); i++){
                        if(aryLst.get(i).balance > max.balance)
                            max = aryLst.get(i);
                    }
                    System.out.println("Where would you like to put the $" + fmt.format(max.balance) + " drained from " + max.name + "?");
                    String newName = kbReader.nextLine();
                    bankAccount theAccount = null;
                    for(int i = 0; i < aryLst.size(); i++){
                        if(aryLst.get(i).name.equals(newName)){
                            theAccount = aryLst.get(i);
                            break;
                        }
                    }
                    try{
                        theAccount.deposit(max.balance);
                        printLog.println(getTimeStamp() + ": $" + fmt.format(max.balance) + " drained into the account " + newName);
                    }catch(NullPointerException e){
                        theAccount = new bankAccount(newName, max.balance);
                        printLog.println(getTimeStamp() + ": Account " + name + " created.");
                        printLog.println(getTimeStamp() + ": $" + fmt.format(max.balance) + " drained into the account " + newName);
                        aryLst.add(theAccount);
                    }
                    max.balance = 0;
                }
            }else if(!name.equals("exit")){
                bankAccount theAccount = null;
                for(int i = 0; i < aryLst.size(); i++){
                    if(aryLst.get(i).name.equals(name)){
                        theAccount = aryLst.get(i);
                        printLog.println(getTimeStamp() + ": Account " + name + " accessed");
                        break;
                    }
                }
                System.out.print("Please enter the amount of the deposit ");
                double amount = kbReader.nextDouble();
                deposits.add(new bankAccount(name, amount));
                try{
                    theAccount.deposit(amount);
                    printLog.println(getTimeStamp() + ": $" + fmt.format(amount) + " deposited into account " + name);
                }catch(NullPointerException e){
                    theAccount = new bankAccount(name, amount);
                    printLog.println(getTimeStamp() + ": Account " + name + " created");
                    printLog.println(getTimeStamp() + ": $" + fmt.format(amount) + " deposited into account " + name);
                    aryLst.add(theAccount);
                }
            }
        }while(!name.equals("exit"));

        printLog.println(getTimeStamp() + ": Account entry logged out");
        printLog.println("Largest account values are:");
        Collections.sort(aryLst);
        for(bankAccount ba : aryLst)
            pw.println(ba.name + "  " + ba.balance);
        pw.close();
        fw.close();
        printLog.close();
        fileLog.close();

        bankAccount ba = aryLst.get(0);
        double maxBalance = ba.balance;
        String largAcco = ba.name;
        for(int i = 1; i < aryLst.size(); i++){
            ba = aryLst.get(i);
            if(ba.balance > maxBalance){
                maxBalance = ba.balance;
                largAcco = ba.name;
            }
        }
        System.out.println("The account with the largest balance belongs to " + largAcco);
        System.out.println("They have $" + fmt.format(maxBalance));
    }

    public static String getTimeStamp(){
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date current = new Date();
        return sdfDate.format(current);
    }
}