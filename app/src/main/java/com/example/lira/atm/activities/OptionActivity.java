package com.example.lira.atm.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.LinearLayout;

import com.example.lira.atm.R;

public class OptionActivity extends Activity {

    LinearLayout checkLayer, rechargeLayer, withdrawLayer;

    @Override
    public void onBackPressed() {
        openQuitDialog();
    }

    private void openQuitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.AlertDialogCustom));
        builder.setTitle("Info");
        builder.setMessage("You successfully withdraw money!");
        builder.setPositiveButton("OK", (dialog, which) -> {
            finish();
            System.exit(0);
        });
        builder.setNegativeButton("NO", (dialog, which) -> dialog.cancel());
        builder.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        checkLayer = (LinearLayout) findViewById (R.id.checkLayout);
        rechargeLayer = (LinearLayout) findViewById (R.id.rechargeLayout);
        withdrawLayer = (LinearLayout) findViewById (R.id.withdrawLayout);


        View.OnClickListener layoutClick = view -> {
            Intent intent = null;
            switch(view.getId()) {
                case R.id.checkLayout:
                    intent = new Intent(this, CheckActivity.class);
                    break;
                case R.id.rechargeLayout:
                    intent = new Intent(this, RechargeActivity.class);
                    break;
                case R.id.withdrawLayout:
                    intent = new Intent(this, MoneyActivity.class);
                    break;
                default:
                    break;
            }
            startActivity(intent);
        };

        checkLayer.setOnClickListener(layoutClick);
        rechargeLayer.setOnClickListener(layoutClick);
        withdrawLayer.setOnClickListener(layoutClick);
    }
}
