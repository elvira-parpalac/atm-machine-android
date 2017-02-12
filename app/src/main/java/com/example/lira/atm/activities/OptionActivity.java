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

    LinearLayout checkLayer;
    LinearLayout rechargeLayer;
    LinearLayout withdrawLayer;

    @Override
    public void onBackPressed() {
        openQuitDialog();
    }

    private void openQuitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.AlertDialogCustom));
        builder.setTitle(R.string.alert_dialog_title);
        builder.setMessage(R.string.txt_exit);
        builder.setPositiveButton(R.string.btn_ok, (dialog, which) -> super.onBackPressed());
        builder.setNegativeButton(R.string.btn_no, (dialog, which) -> dialog.cancel());
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
