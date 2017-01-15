package com.example.lira.atm.models;

import java.util.ArrayList;
import java.util.List;

public class Denominations {

    private int total;

    public ArrayList<Item> values = new ArrayList<>();
    private List<Item> wishList = new ArrayList<>();

    public Denominations() {
        int[] denoms = {1000, 500, 200, 100, 50, 20, 10, 5, 1};
        for (int denom : denoms) {
            values.add(new Item(denom, 0));
        }
    }

    public void setSum(int z) {
        this.total = z;
    }

    public List<Item> values() {
        int sum = total;
        System.out.println("Wish List");
        for (Item item : wishList){
            sum -= item.currentValue * item.denomination;
        }

        if (sum < 0) {
            wishList.clear();
        } else {
            int x, y;

            System.out.println("Denomination List");
            for(Item value : values) {


                int s = search(value.denomination);

                if (s == -1) {

                    // divide sum by denomination, find the number of entries, take the entire part
                    x = (int) Math.floor(sum / value.denomination);

                    // multiply the number if entries by denomination
                    y = x * value.denomination;
                    sum = sum - y;
                } else {
                    x = s;
                }
                value.currentValue = x; // от суммы отнимаем полученное число, чтоб продолжить

            }
            // Если сумма все еще равна нулю после того, как разбросались значения, т.е. пользователь захотел что-то изменить и теперь недостает
            if (sum != 0) {
                values.get(values.size() - 1).currentValue += sum;
            }
        }
        return values;
    }

    private int search(int denom) {
        int a = -1;
        for (int i = 0; i < wishList.size(); i++) {
            if (wishList.get(i).denomination.equals(denom)) {
                a = wishList.get(i).currentValue;
                break;
            }
        }
        return a;
    }

    public void createWishList(int den, int wish) {

        int position = -1;
        for (int i = 0; i < wishList.size(); i++) {
            if (wishList.get(i).denomination.equals(den)) {
                position = i;
                break;
            }
        }

        if (position > -1) {
            if (wish == 0) {
                wishList.remove(position);
            } else
                wishList.get(position).currentValue = wish;
        } else {
            wishList.add(new Item(den, wish));
        }

        for (int i = 0; i < wishList.size(); i++) {
            System.out.println("w_den: " + wishList.get(i).denomination + " w_size: " + wishList.get(i).currentValue);
        }
    }
}
