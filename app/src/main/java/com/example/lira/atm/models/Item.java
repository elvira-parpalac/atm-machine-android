package com.example.lira.atm.models;

public class Item {

    public Integer denomination;
    public Integer currentValue;

    Item(Integer denomination, Integer currentValue){
        this.denomination = denomination;
        this.currentValue = currentValue;
    }
}
