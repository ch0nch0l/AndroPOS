package me.chonchol.andropos.layout;

import android.content.Intent;
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import me.chonchol.andropos.R;
import me.chonchol.andropos.adapter.CategoryListAdapter;
import me.chonchol.andropos.enums.RequestCode;
import me.chonchol.andropos.helper.RecyclerItemTouchHelper;
import me.chonchol.andropos.interfaces.OnRecyclerViewItemClickListener;
import me.chonchol.andropos.model.Category;
import me.chonchol.andropos.rest.ApiClient;
import me.chonchol.andropos.rest.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryListActivity extends AppCompatActivity implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {

    private RecyclerView recyclerView;
    private List<Category> categories;
    private CategoryListAdapter adapter;
    private CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddCategoryActivity.class);
                startActivityForResult(intent, RequestCode.ADDCATEGORY.getValue());

            }
        });

        initializeView();
    }


    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof CategoryListAdapter.CatItemViewHolder) {
            // get the removed item name to display it in snack bar
            String name = categories.get(viewHolder.getAdapterPosition()).getCatName();

            // backup of removed item for undo purpose
            final Category deletedItem = categories.get(viewHolder.getAdapterPosition());
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

    public void initializeView(){
        recyclerView = findViewById(R.id.categoryListView);
        coordinatorLayout = findViewById(R.id.coordinatorLayout);
        categories = new ArrayList<>();

        adapter = new CategoryListAdapter(this, categories, new OnRecyclerViewItemClickListener() {
            @Override
            public void onObjectItemClick(Object object) {
                Category category = (Category) object;
                Intent intent = new Intent(getApplicationContext(), CategoryActivity.class);
                intent.putExtra("CAT_NAME", category.getCatName());
                intent.putExtra("CAT_ID", category.getCatId());
                startActivity(intent);
            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);

        ItemTouchHelper.SimpleCallback simpleCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclerView);
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<List<Category>> getAllCategories = apiService.getAllCategories();
        getAllCategories.enqueue(new Callback<List<Category>>() {

            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {

                for (Category category : response.body()) {
                    categories.add(category);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show();
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RequestCode.ADDCATEGORY.getValue()){
            if (resultCode == RESULT_OK)
                adapter.notifyDataSetChanged();
        }
    }
}
