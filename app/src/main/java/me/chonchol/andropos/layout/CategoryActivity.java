package me.chonchol.andropos.layout;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import me.chonchol.andropos.R;
import me.chonchol.andropos.adapter.DynamicListAdapter;
import me.chonchol.andropos.helper.RecyclerItemTouchHelper;
import me.chonchol.andropos.interfaces.OnRecyclerViewItemClickListener;
import me.chonchol.andropos.model.Subcategory;
import me.chonchol.andropos.rest.ApiClient;
import me.chonchol.andropos.rest.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryActivity extends AppCompatActivity implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener{

    private TextView txtCatName;
    private RecyclerView subcatRecyclerView;
    private List<Subcategory> subcategories;
    private List<Object> objects;
    private DynamicListAdapter adapter;
    private CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initializeView();

        Toast.makeText(getApplicationContext(), (getIntent().getExtras().getString("CAT_ID")), Toast.LENGTH_LONG).show();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }


    private void initializeView(){
        txtCatName = findViewById(R.id.txtCatName);
        subcatRecyclerView = findViewById(R.id.subcategoryListView);
        coordinatorLayout = findViewById(R.id.coordinatorLayout);

        txtCatName.setText(getIntent().getExtras().getString("CAT_NAME"));

        objects = new ArrayList<>();
        adapter = new DynamicListAdapter(getApplicationContext(), objects, new OnRecyclerViewItemClickListener() {
            @Override
            public void onObjectItemClick(Object object) {
                Toast.makeText(getApplicationContext(), "Its Working", Toast.LENGTH_LONG).show();
            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        subcatRecyclerView.setLayoutManager(layoutManager);
        subcatRecyclerView.setItemAnimator(new DefaultItemAnimator());
        subcatRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        subcatRecyclerView.setAdapter(adapter);

        ItemTouchHelper.SimpleCallback simpleCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(subcatRecyclerView);
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<List<Subcategory>> getSubcategoryListByCatId = apiService.getSubcategoryListByCatId(getIntent().getExtras().getInt("CAT_ID"));

        getSubcategoryListByCatId.enqueue(new Callback<List<Subcategory>>() {
            @Override
            public void onResponse(Call<List<Subcategory>> call, Response<List<Subcategory>> response) {

                for (Subcategory subcategory : response.body()) {
                    objects.add(subcategory);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Subcategory>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof DynamicListAdapter.ObjectItemViewHolder) {
            // get the removed item name to display it in snack bar
            for(Object object: objects){
                subcategories.add((Subcategory) object);
            }
            String name = subcategories.get(viewHolder.getAdapterPosition()).getSubcatName();

            // backup of removed item for undo purpose
            final Object deletedItem = objects.get(viewHolder.getAdapterPosition());
            final int deletedIndex = viewHolder.getAdapterPosition();

            // remove the item from recycler view
            adapter.removeItem(viewHolder.getAdapterPosition());

            // showing snack bar with Undo option
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, name + " removed from cart!", Snackbar.LENGTH_LONG);
            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // undo is selected, restore the deleted item
                    adapter.restoreItem(deletedItem, deletedIndex);
                }
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();
        }
    }
}
