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
import android.widget.RadioGroup;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import es.dmoral.toasty.Toasty;
import me.chonchol.andropos.R;
import me.chonchol.andropos.enums.ReportType;
import me.chonchol.andropos.helper.ViewDialog;
import me.chonchol.andropos.model.QuotationList;
import me.chonchol.andropos.model.Sale;
import me.chonchol.andropos.model.Stock;
import me.chonchol.andropos.report.ReportGenerator;
import me.chonchol.andropos.rest.ApiClient;
import me.chonchol.andropos.rest.ApiService;
import me.chonchol.andropos.sharedpref.ClientSharedPreference;
import me.chonchol.andropos.sharedpref.LoginSharedPreference;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilterReportActivity extends AppCompatActivity {

    private Button btnStockAlertReport, btnReportByProduct;
    private RadioGroup radGrpReportType;
    private RadioButton radioWeekly, radioMonthly, radioCustom;
    private LinearLayout layoutDateRange;
    private EditText inputFromDate, inputToDate;
    private FloatingActionButton btnGenerateReport;
    private DatePickerDialog.OnDateSetListener date;
    private int reportType;
    private ViewDialog viewDialog;

    private String fromDate, toDate;
    private Calendar calendar;
    private Date currentTime = Calendar.getInstance().getTime();
    private DateFormat format = new SimpleDateFormat("dd-MMM-yyyy");

    private ReportGenerator reportGenerator = new ReportGenerator();
    private ApiService apiService;

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
                viewDialog = new ViewDialog(FilterReportActivity.this);
                viewDialog.show();
                if (reportType == ReportType.STOCK_REPORT.getValue()){
                    generateStockReport(fromDate, toDate);
                } else if (reportType == ReportType.SALE_REPORT.getValue()){
                    generateSaleReport(fromDate, toDate);

                } else if (reportType == ReportType.PROFIT_REPORT.getValue()){
                    generateProfitReport(fromDate, toDate);
                }
            }
        });
    }

    private void generateProfitReport(String fromDate, String toDate) {

    }

    private void generateSaleReport(String fromDate, String toDate) {
        List<Sale> saleList = new ArrayList<>();
        List<QuotationList> quotationLists = new ArrayList<>();

        apiService = ApiClient.getClient(ClientSharedPreference.getClientUrl(getApplicationContext())).create(ApiService.class);
        apiService.getSaleListByDate(fromDate, toDate).enqueue(new Callback<List<Sale>>() {
            @Override
            public void onResponse(Call<List<Sale>> call, Response<List<Sale>> response) {
                if (response.isSuccessful()){
                    for (Sale sale: response.body()){
                        saleList.add(sale);
                    }
                    reportGenerator.generateSaleReport(getApplicationContext(), saleList);
                    viewDialog.hide();
                }
            }

            @Override
            public void onFailure(Call<List<Sale>> call, Throwable t) {
                viewDialog.hide();
                Toasty.error(getApplicationContext(), "Sale report generation failed.!", Toast.LENGTH_SHORT, true).show();
            }
        });
    }

    private void generateStockReport(String fromDate, String toDate) {

        List<Stock> stockList = new ArrayList<>();

        apiService = ApiClient.getClient(ClientSharedPreference.getClientUrl(getApplicationContext())).create(ApiService.class);
        apiService.getStockListByDate(fromDate, toDate).enqueue(new Callback<List<Stock>>() {
            @Override
            public void onResponse(Call<List<Stock>> call, Response<List<Stock>> response) {
                if (response.isSuccessful()){
                    for (Stock stock: response.body()){
                        stockList.add(stock);
                    }
                    reportGenerator.generateStockReport(getApplicationContext(), stockList);
                    viewDialog.hide();
                }
            }

            @Override
            public void onFailure(Call<List<Stock>> call, Throwable t) {
                viewDialog.hide();
                Toasty.error(getApplicationContext(), "Stock report generation failed.!", Toast.LENGTH_SHORT, true).show();
            }
        });
    }

    private void initializeView() {
        btnStockAlertReport = findViewById(R.id.btnStockAlertReport);
        btnReportByProduct = findViewById(R.id.btnReportByProduct);
        radGrpReportType = findViewById(R.id.radGrpReportType);
        radioWeekly = findViewById(R.id.radioWeekly);
        radioMonthly = findViewById(R.id.radioMonthly);
        radioCustom = findViewById(R.id.radioCustom);
        layoutDateRange = findViewById(R.id.layoutDateRange);
        inputFromDate = findViewById(R.id.inputFromDate);
        inputToDate = findViewById(R.id.inputToDate);

        if (getIntent().getIntArrayExtra("STOCK_REPORT").equals(ReportType.STOCK_REPORT.getValue())) {
            reportType = ReportType.STOCK_REPORT.getValue();
            btnStockAlertReport.setVisibility(View.VISIBLE);
        } else if (getIntent().getIntArrayExtra("SALE_REPORT").equals(ReportType.SALE_REPORT.getValue())) {
            reportType = ReportType.SALE_REPORT.getValue();
            btnStockAlertReport.setVisibility(View.GONE);
        } else if (getIntent().getIntArrayExtra("PROFIT_REPORT").equals(ReportType.PROFIT_REPORT.getValue())) {
            reportType = ReportType.PROFIT_REPORT.getValue();
            btnStockAlertReport.setVisibility(View.GONE);
        }

        radGrpReportType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.radioWeekly) {
                    layoutDateRange.setVisibility(View.GONE);
                    fromDate = format.format(addDays(currentTime, 7));
                    toDate = format.format(currentTime);
                } else if (i == R.id.radioMonthly) {
                    layoutDateRange.setVisibility(View.GONE);
                    fromDate = format.format(addDays(currentTime, Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH)));
                    toDate = format.format(currentTime);
                } else if (i == R.id.radioCustom) {
                    layoutDateRange.setVisibility(View.VISIBLE);

                    inputFromDate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            setFromDate();
                            fromDate = inputFromDate.getText().toString();
                        }
                    });

                    inputToDate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            setToDate();
                            toDate = inputToDate.getText().toString();
                        }
                    });
                }
            }
        });
    }


    public Date addDays(Date date, int days) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, days);

        return calendar.getTime();
    }

    public void setFromDate(){
        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                inputFromDate.setText(format.format(calendar.getTime()));
            }
        };
    }

    public void setToDate(){
        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                inputToDate.setText(calendar.getTime().toString());
            }
        };
    }
}
