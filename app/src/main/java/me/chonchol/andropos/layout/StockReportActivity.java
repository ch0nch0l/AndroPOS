package me.chonchol.andropos.layout;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StockReportActivity extends AppCompatActivity {

    private RadioGroup radGrpReportType;
    private RadioButton radioStockAlert, radioProductWise, radioWeekly, radioMonthly, radioCustom;
    private LinearLayout layoutDateRange;
    private EditText inputFromDate, inputToDate;
    private FloatingActionButton btnGenerateReport;
    private int reportType;
    private ViewDialog viewDialog;

    private String fromDate, toDate;
    private Calendar calendar = Calendar.getInstance();
    private Date currentTime = Calendar.getInstance().getTime();
    private DateFormat format = new SimpleDateFormat("dd-MMM-yyyy");

    private ReportGenerator reportGenerator = new ReportGenerator();
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_report);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initializeView();

        btnGenerateReport = (FloatingActionButton) findViewById(R.id.btnGenerateReport);
        btnGenerateReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewDialog = new ViewDialog(StockReportActivity.this);
                viewDialog.show();

                switch (reportType) {
                    case 1:
                        generateStockAlertReport();
                        break;
                    case 2:
                        generateProductWiseReport();
                        break;
                    case 3:
                        generateStockReportByDate(fromDate, toDate);
                        break;
                    case 4:
                        generateStockReportByDate(fromDate, toDate);
                        break;
                    case 5:
                        generateStockReportByDate(fromDate, toDate);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void generateProductWiseReport() {

    }

    private void generateStockAlertReport() {
        List<Stock> stockList = new ArrayList<>();

        apiService = ApiClient.getClient(ClientSharedPreference.getClientUrl(getApplicationContext())).create(ApiService.class);
        apiService.getStockAlertStockList(5).enqueue(new Callback<List<Stock>>() {
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

    private void generateStockReportByDate(String fromDate, String toDate) {

        List<Stock> stockList = new ArrayList<>();

        apiService = ApiClient.getClient(ClientSharedPreference.getClientUrl(getApplicationContext())).create(ApiService.class);
        apiService.getStockListByDate(fromDate, toDate).enqueue(new Callback<List<Stock>>() {
            @Override
            public void onResponse(Call<List<Stock>> call, Response<List<Stock>> response) {
                if (response.isSuccessful()) {
                    for (Stock stock : response.body()) {
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
        radGrpReportType = findViewById(R.id.radGrpReportType);
        radioStockAlert = findViewById(R.id.radioStockAlert);
        radioProductWise = findViewById(R.id.radioProductWise);
        radioWeekly = findViewById(R.id.radioWeekly);
        radioMonthly = findViewById(R.id.radioMonthly);
        radioCustom = findViewById(R.id.radioCustom);
        layoutDateRange = findViewById(R.id.layoutDateRange);
        inputFromDate = findViewById(R.id.inputFromDate);
        inputToDate = findViewById(R.id.inputToDate);

        DatePickerDialog.OnDateSetListener fromDatePicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                inputFromDate.setText(format.format(calendar.getTime()));

            }
        };

        DatePickerDialog.OnDateSetListener toDatePicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                inputToDate.setText(format.format(calendar.getTime()));

            }
        };

        radGrpReportType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.radioStockAlert) {
                    layoutDateRange.setVisibility(View.GONE);
                    reportType = ReportType.STOCK_ALERT_REPORT.getValue();
                } else if (i == R.id.radioProductWise) {
                    layoutDateRange.setVisibility(View.GONE);
                    reportType = ReportType.PRODUCT_WISE_REPORT.getValue();
                } else if (i == R.id.radioWeekly) {
                    layoutDateRange.setVisibility(View.GONE);
                    reportType = ReportType.WEEKLY_REPORT.getValue();
                    fromDate = format.format(addDays(currentTime, 7));
                    toDate = format.format(currentTime);
                } else if (i == R.id.radioMonthly) {
                    layoutDateRange.setVisibility(View.GONE);
                    reportType = ReportType.MONTHLY_REPORT.getValue();
                    fromDate = format.format(addDays(currentTime, Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH)));
                    toDate = format.format(currentTime);
                } else if (i == R.id.radioCustom) {
                    layoutDateRange.setVisibility(View.VISIBLE);
                    reportType = ReportType.CUSTOM_REPORT.getValue();

                    inputFromDate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            new DatePickerDialog(StockReportActivity.this, fromDatePicker, calendar.get(Calendar.YEAR),
                                    calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
                        }
                    });

                    inputToDate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            new DatePickerDialog(StockReportActivity.this, toDatePicker, calendar.get(Calendar.YEAR),
                                    calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
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


}
