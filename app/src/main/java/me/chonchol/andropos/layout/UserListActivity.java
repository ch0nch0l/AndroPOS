package me.chonchol.andropos.layout;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import me.chonchol.andropos.R;
import me.chonchol.andropos.adapter.DynamicListAdapter;
import me.chonchol.andropos.helper.ViewDialog;
import me.chonchol.andropos.interfaces.OnRecyclerViewItemClickListener;
import me.chonchol.andropos.model.Category;
import me.chonchol.andropos.model.User;
import me.chonchol.andropos.rest.ApiClient;
import me.chonchol.andropos.rest.ApiService;
import me.chonchol.andropos.sharedpref.ClientSharedPreference;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserListActivity extends AppCompatActivity {

    private ViewDialog dialog;
    private RecyclerView userListView;
    private DynamicListAdapter adapter;

    private List<Object> objects;
    private List<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initializeView();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }


    private void initializeView() {
        dialog = new ViewDialog(UserListActivity.this);
        dialog.show();

        userListView = findViewById(R.id.userListView);
        objects = new ArrayList<>();
        adapter = new DynamicListAdapter(getApplicationContext(), objects, new OnRecyclerViewItemClickListener() {
            @Override
            public void onObjectItemClick(Object object) {

            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        userListView.setLayoutManager(layoutManager);
        userListView.setItemAnimator(new DefaultItemAnimator());
        userListView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        userListView.setAdapter(adapter);

        ApiService apiService = ApiClient.getClient(ClientSharedPreference.getClientUrl(getApplicationContext())).create(ApiService.class);
        Call<List<User>> getAllUsers = apiService.getAllUsers();
        getAllUsers.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()){
                    for (User user: response.body()) {
                        objects.add(user);
                    }
                    adapter.notifyDataSetChanged();
                    dialog.hide();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                dialog.hide();
                Toasty.error(getApplicationContext(), "Failed to fetch any user.!", Toast.LENGTH_SHORT, true).show();

            }
        });
    }

}
