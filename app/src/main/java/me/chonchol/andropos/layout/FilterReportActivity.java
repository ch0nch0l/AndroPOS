package me.chonchol.andropos.layout;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import me.chonchol.andropos.R;
import me.chonchol.andropos.enums.ReportType;

public class FilterReportActivity extends AppCompatActivity {

    private Button btnStockAlertReport, btnReportByProduct;
    private RadioButton radioWeekly, radioMonthly, radioCustom;
    private LinearLayout layoutDateRange;
    private EditText inputFromDate, inputToDate;
    private FloatingActionButton btnGenerateReport;
    private int reportType;

    private String fromDate, toDate;
    private Calendar calendar;
    private DateFormat format = new SimpleDateFormat("dd-MMM-yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_report);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initializeView();

        btnGenerateReport = (FloatingActionButton) findViewById(R.id.btnGenerateReport);
        btnGenerateReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void initializeView(){
        btnStockAlertReport = findViewById(R.id.btnStockAlertReport);
        btnReportByProduct = findViewById(R.id.btnReportByProduct);
        radioWeekly = findViewById(R.id.radioWeekly);
        radioMonthly = findViewById(R.id.radioMonthly);
        radioCustom = findViewById(R.id.radioCustom);
        layoutDateRange = findViewById(R.id.layoutDateRange);
        inputFromDate = findViewById(R.id.inputFromDate);
        inputToDate = findViewById(R.id.inputToDate);

        if (getIntent().getIntArrayExtra("STOCK_REPORT").equals(ReportType.STOCK_REPORT.getValue())){
            reportType = ReportType.STOCK_REPORT.getValue();
            btnStockAlertReport.setVisibility(View.VISIBLE);
        } else if (getIntent().getIntArrayExtra("SALE_REPORT").equals(ReportType.SALE_REPORT.getValue())){
            reportType = ReportType.SALE_REPORT.getValue();
            btnStockAlertReport.setVisibility(View.GONE);
        } else if (getIntent().getIntArrayExtra("PROFIT_REPORT").equals(ReportType.PROFIT_REPORT.getValue())){
            reportType = ReportType.PROFIT_REPORT.getValue();
            btnStockAlertReport.setVisibility(View.GONE);
        }

        if (radioWeekly.isChecked()){
            //calendar.add(Calendar.DATE - 7);
            fromDate = format.format(Calendar.getInstance().getTime()).toString();
            toDate = format.format(Calendar.getInstance().getTime()).toString();

        } //else if ()

        if (radioCustom.isChecked()){
            layoutDateRange.setVisibility(View.VISIBLE);

            inputFromDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    datePicker();
                }
            });
        }

    }

    private void datePicker() {

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                inputFromDate.setText(calendar.getTime().toString());
            }

        };

        inputFromDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getApplicationContext(), date, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

}
