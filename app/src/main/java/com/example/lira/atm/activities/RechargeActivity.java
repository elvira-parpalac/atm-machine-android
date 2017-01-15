package com.example.lira.atm.activities;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.widget.Button;
import android.widget.EditText;

import com.example.lira.atm.models.Account;
import com.example.lira.atm.models.Operation;
import com.example.lira.atm.R;

public class RechargeActivity  extends Activity{

    Button btnRecharge;
    EditText moneyBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);

        btnRecharge = (Button) findViewById(R.id.btnRecharge);
        btnRecharge.setEnabled(true);

        moneyBox = (EditText) findViewById(R.id.moneyBox);

        btnRecharge.setOnClickListener(view1 -> {

            String str = moneyBox.getText().toString();

            if(!str.isEmpty()) {

                Integer amount = Integer.parseInt(str);

                Integer newBalance = Account.getInstance().getMoney() + amount;
                Account.getInstance().setMoney(newBalance);

                Account.getInstance().addOperation(Operation.RECHARGE, amount, newBalance);

                Intent intent = new Intent(this, CheckActivity.class);
                startActivity(intent);

            }else{
                errorDialog("The field is empty! Please, enter the amount.");
            }
        });

    }

    private void errorDialog(String m) {
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.AlertDialogCustom));
        builder.setTitle("Info");

        builder.setMessage(m);

        builder.setPositiveButton("OK", null);
        builder.show();
    }
}
