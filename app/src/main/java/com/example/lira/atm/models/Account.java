package com.example.lira.atm.models;

import com.example.lira.atm.utils.Serialization;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Account implements Serializable {

    private static Account mInstance = null;
    private Integer money;
    private ArrayList<Operation> operationList = new ArrayList<>();

    private Account() {
        this.money = 1500;
    }

    public static Account getInstance() {
        if (mInstance == null) {
            mInstance = new Account();
        }
        return mInstance;
    }
    public Integer getMoney() {
        return this.money;
    }
    public ArrayList<Operation> getOperationsList() {
        return this.operationList;
    }

    public static void setInstance(Account acc) {
        mInstance = acc;
    }
    public void setMoney(Integer value) {
        money = value;
    }

    public void addOperation(String operationName, Integer operationMoney, Integer balance) {

        Operation operation = new Operation();

        Calendar calendar = Calendar.getInstance();
        String date = DateFormat.getDateTimeInstance().format(calendar.getTime());

        operation.setDate(date);
        operation.setMoney(operationMoney);
        operation.setName(operationName);
        operation.setBalance(balance);
        operationList.add(operation);

        // serialization
        Serialization.saveObject(Account.getInstance());
    }
}
