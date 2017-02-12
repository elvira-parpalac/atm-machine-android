package com.example.lira.atm.activities;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.widget.Button;
import android.widget.EditText;

import com.example.lira.atm.models.Account;
import com.example.lira.atm.R;

public class MoneyActivity  extends Activity{

    Button btnSubmit;
    EditText userMoneyBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw_money);

        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnSubmit.setEnabled(true);

        userMoneyBox = (EditText) findViewById(R.id.userMoneyBox);

        btnSubmit.setOnClickListener(view1 -> {

            String str = userMoneyBox.getText().toString();

            if(!str.isEmpty()){
                int t = Integer.parseInt(str);

                if(t <= Account.getInstance().getMoney()){
                    Intent intent = new Intent(getApplicationContext(), WithdrawActivity.class);
                    intent.putExtra("sum", t);
                    startActivity(intent);
                } else {
                    errorDialog(getString(R.string.error_withdraw_money));
                }
            }else{
                errorDialog(getString(R.string.error_field_empty));
            }

        });

    }

    private void errorDialog(String m) {
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.AlertDialogCustom));

        builder.setTitle(R.string.alert_dialog_title);
        builder.setMessage(m);

        builder.setPositiveButton(R.string.btn_ok, null);
        builder.show();
    }

}
