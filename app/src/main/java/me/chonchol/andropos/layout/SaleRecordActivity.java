package me.chonchol.andropos.layout;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import me.chonchol.andropos.R;
import me.chonchol.andropos.adapter.SaleRecordAdapter;
import me.chonchol.andropos.helper.ViewDialog;
import me.chonchol.andropos.model.QuotationList;
import me.chonchol.andropos.rest.ApiClient;
import me.chonchol.andropos.rest.ApiService;
import me.chonchol.andropos.sharedpref.ClientSharedPreference;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SaleRecordActivity extends AppCompatActivity {

    //private ProgressDialog progressDialog;
    private RecyclerView saleRecordListView;
    private List<QuotationList> quotationLists = new ArrayList<>();
    private SaleRecordAdapter adapter;
    private ApiService apiService;
    private ViewDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_record);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        List<QuotationList> allSaleRecords = getAllSaleRecords();

        initializeView(allSaleRecords);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }


    private void initializeView(List<QuotationList> quotationLists){
        //progressDialog = new ProgressDialog(SaleRecordActivity.this);
//        progressDialog.setMessage("Please wait...");
//        progressDialog.show();
        saleRecordListView = findViewById(R.id.saleRecordListView);
        dialog = new ViewDialog(SaleRecordActivity.this);
        dialog.show();


        adapter = new SaleRecordAdapter(getApplicationContext(), quotationLists);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        saleRecordListView.setLayoutManager(layoutManager);
        saleRecordListView.setItemAnimator(new DefaultItemAnimator());
        saleRecordListView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        saleRecordListView.setAdapter(adapter);
    }


    public List<QuotationList> getAllSaleRecords(){
        apiService = ApiClient.getClient(ClientSharedPreference.getClientUrl(getApplicationContext())).create(ApiService.class);
        apiService.getQuotationList().enqueue(new Callback<List<QuotationList>>() {
            @Override
            public void onResponse(Call<List<QuotationList>> call, Response<List<QuotationList>> response) {
                response.body();
                if (response.isSuccessful()){
                    for (QuotationList quotationList : response.body()) {
                        quotationLists.add(quotationList);
                        adapter.notifyDataSetChanged();
                    }
                    dialog.hide();
                }

            }

            @Override
            public void onFailure(Call<List<QuotationList>> call, Throwable t) {
                dialog.hide();
            }
        });
        return quotationLists;
    }
}
