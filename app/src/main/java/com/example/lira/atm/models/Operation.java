package com.example.lira.atm.models;

import java.io.Serializable;

public class Operation implements Serializable {

    private static final long serialVersionUID = 1L;

    public String date;
    public String name;
    public Integer money;
    public Integer balance;

    public static String RECHARGE = "Recharge";
    public static String WITHDRAW = "Withdraw";

    void setDate(String date) {
        this.date = date;
    }
    void setName(String name) { this.name = name; }
    void setMoney(Integer money) { this.money = money; }
    void setBalance(Integer balance) {
        this.balance = balance;
    }

}
