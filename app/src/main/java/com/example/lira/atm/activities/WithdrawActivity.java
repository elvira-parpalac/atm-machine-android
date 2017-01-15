package com.example.lira.atm.activities;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lira.atm.models.Account;
import com.example.lira.atm.models.Denominations;
import com.example.lira.atm.models.Item;
import com.example.lira.atm.models.Operation;
import com.example.lira.atm.R;


public class WithdrawActivity extends Activity {

    Button btnGoBack, btnWithdrawMoney;
    TextView errorTxt;
    LayoutInflater inflater;
    Denominations denominations;
    LinearLayout linLayout;

    public int userSum;
    public int lastPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw);

        this.inflater = getLayoutInflater();

        btnGoBack = (Button) findViewById(R.id.btnGoBack);
        btnWithdrawMoney = (Button) findViewById(R.id.btnWithdrawMoney);
        errorTxt = (TextView) findViewById(R.id.denominationTxt);

        linLayout = (LinearLayout) findViewById(R.id.linLayout);

        userSum = getIntent().getExtras().getInt("sum");

        denominations = new Denominations();
        denominations.setSum(userSum);

        renderList();

        btnWithdrawMoney.setEnabled(true);

        View.OnClickListener btnClk = v -> {
            switch (v.getId()) {
                case R.id.btnGoBack:
                    Intent intent = new Intent(getApplicationContext(), OptionActivity.class);
                    startActivity(intent);
                    break;

                case R.id.btnWithdrawMoney:
                    Integer oldBalance = Account.getInstance().getMoney();
                    Integer newBalance = oldBalance - userSum;

                    Account.getInstance().setMoney(newBalance);

                    Account.getInstance().addOperation(Operation.WITHDRAW, userSum, newBalance);

                    confirmDialog();
                    break;
            }
        };

        btnGoBack.setOnClickListener(btnClk);
        btnWithdrawMoney.setOnClickListener(btnClk);

    }

    private void confirmDialog() {
        Intent intent = new Intent(getApplicationContext(), CheckActivity.class);

        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.AlertDialogCustom));
        builder.setTitle("Info");
        builder.setMessage("You successfully withdraw money!");
        builder.setPositiveButton("OK", (dialog, which) -> {
            startActivity(intent);
        });
        builder.show();
    }


    private void renderList() {
        linLayout.removeAllViews();

        denominations.values();
        int position = 0;

        for (Item listItem : denominations.values) {

            View item = inflater.inflate(R.layout.item, linLayout, false);
            EditText editText = (EditText) item.findViewById(R.id.numberBox);
            TextView denomTxt = (TextView) item.findViewById(R.id.denomTxt);
            TextView tvResult = (TextView) item.findViewById(R.id.tvResult);

            String denom = listItem.denomination.toString();
            denomTxt.setText(denom);

            String currentValue = "";
            if (listItem.currentValue != 0) {
                currentValue = String.valueOf(listItem.currentValue);
            }

            editText.setText(currentValue);
            editText.addTextChangedListener(new WithdrawActivity.NumberBoxListener(position));
            if (position == lastPosition) {
                editText.requestFocus();
            }

            Integer res = listItem.denomination * listItem.currentValue;
            String s = "= " + res;
            tvResult.setText(s);

            linLayout.addView(item);
            position++;
        }

        linLayout.invalidate();

    }

    class NumberBoxListener implements TextWatcher {

        int position;

        NumberBoxListener(int position) {
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            String s = editable.toString();

            if (s.isEmpty()) {
                s = "0";
            }

            int t = Integer.parseInt(s);

            denominations.createWishList(denominations.values.get(position).denomination, t);
            lastPosition = position;
            renderList();
        }
    }
}
