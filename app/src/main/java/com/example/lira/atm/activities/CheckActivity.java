package com.example.lira.atm.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.lira.atm.models.Account;
import com.example.lira.atm.models.Operation;
import com.example.lira.atm.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

public class CheckActivity extends Activity {

    Button btnGoTop;
    TextView accBalance;
    TextView tvOperationNumber;
    TextView tvOperationDate;
    TextView tvOperationName;
    TextView tvOperationBalance;
    TextView tvOperationMoney;

    LinearLayout rlStatistics;
    ArrayList<Operation> operations;
    ArrayList<Entry> entries;
    LayoutInflater inflater;

    ScrollView mainScrollView;

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), OptionActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK); // clear stack and make activity the root of the stack
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);

        this.inflater = getLayoutInflater();

        btnGoTop = (Button) findViewById(R.id.btnGoTop);
        accBalance = (TextView) findViewById(R.id.accBalance);
        rlStatistics = (LinearLayout) findViewById(R.id.rlStatistics);
        mainScrollView = ((ScrollView) findViewById(R.id.scrollChart));

        //------------------------------------ Set money -------------------------------------------

        Integer srtMoney = Account.getInstance().getMoney();
        accBalance.setText(String.valueOf(srtMoney));

        //------------------------------------ Line Chart ------------------------------------------

        LineChart lineChart = (LineChart) findViewById(R.id.chart);

        operations = Account.getInstance().getOperationsList();
        entries = new ArrayList<>();

        for (int i = 0; i < operations.size(); i++) {
            entries.add(new Entry(operations.get(i).balance, i));
        }

        LineDataSet dataset = new LineDataSet(entries, getString(R.string.balance));
        dataset.setColor(Color.rgb(0, 215, 182));
        dataset.setCircleColor(Color.rgb(0, 215, 182));
        dataset.setLineWidth(2f);
        dataset.setDrawCircleHole(true);
        dataset.setValueTextSize(15);
        dataset.setDrawFilled(true);
        dataset.setDrawCubic(true);
        dataset.setValueTextColor(Color.rgb(0, 215, 182));

        ArrayList<String> labels = new ArrayList<>();
        for (int i = 0; i < operations.size(); i++) {
            labels.add("" + i);
        }

        LineData data = new LineData(labels, dataset);

        lineChart.setData(data);
        lineChart.animateY(3000, Easing.EasingOption.EaseInOutCubic);
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(true);
        lineChart.setDescription(getString(R.string.balance_chart));
        lineChart.setDescriptionTextSize(14f);
        lineChart.setNoDataTextDescription(getString(R.string.chart_no_data));
        lineChart.setDescriptionColor(Color.rgb(38, 50, 56));
        lineChart.setPinchZoom(true);
        lineChart.setVisibleXRangeMaximum(5);

        lineChart.setBackgroundColor(Color.TRANSPARENT); //set whatever color you prefer
        lineChart.setDrawGridBackground(false);

        // get the legend (only possible after setting data)
        Legend l = lineChart.getLegend();
        l.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
        l.setForm(Legend.LegendForm.LINE);
        l.setTextColor(Color.YELLOW);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setTextSize(12f);
        xAxis.setTextColor(Color.YELLOW);
        xAxis.setDrawGridLines(false);

        YAxis yAxis = lineChart.getAxisRight();
        yAxis.setTextSize(12f);
        yAxis.setTextColor(Color.YELLOW);

        YAxis yAxis2 = lineChart.getAxisLeft();
        yAxis2.setTextSize(12f);
        yAxis2.setTextColor(Color.YELLOW);
        yAxis2.setDrawGridLines(false);

        // statistics
        renderOperationList();

        btnGoTop.setOnClickListener(view1 -> {
            // scroll to top
            mainScrollView.fullScroll(ScrollView.FOCUS_UP);
        });
    }

    private void renderOperationList() {
        rlStatistics.removeAllViews();

        String sign;

        // reverse list
        ArrayList<Operation> operationList = new ArrayList<>();
        for (int i = operations.size() - 1; i >= 0; i--) {
            operationList.add(operations.get(i));
        }

        String sDate;
        String sBalance;
        String sName;
        String sNumb;

        int number = 0;
        int opSize = operationList.size();

        for (Operation operation : operationList) {

            View item = inflater.inflate(R.layout.item_operation, rlStatistics, false);
            tvOperationNumber = (TextView) item.findViewById(R.id.tvOperationNumber);
            tvOperationDate = (TextView) item.findViewById(R.id.tvOperationDate);
            tvOperationName = (TextView) item.findViewById(R.id.tvOperationName);
            tvOperationBalance = (TextView) item.findViewById(R.id.tvOperationBalance);
            tvOperationMoney = (TextView) item.findViewById(R.id.tvOperationMoney);

            sNumb = String.valueOf(opSize - number);
            tvOperationNumber.setText(sNumb);

            sDate = operation.date;
            tvOperationDate.setText(sDate);

            sName = operation.name;
            tvOperationName.setText(sName);

            sBalance = operation.balance.toString();
            tvOperationBalance.setText(sBalance);

            // set sign of operation
            if (operation.name.equals(Operation.WITHDRAW)) {
                sign = "-" + operation.money;
            } else {
                sign = "+" + operation.money;
            }
            tvOperationMoney.setText(sign);

            number++;

            rlStatistics.addView(item);
        }

        rlStatistics.invalidate();
    }
}
