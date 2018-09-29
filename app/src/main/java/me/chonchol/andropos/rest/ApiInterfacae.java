package me.chonchol.andropos.rest;

import java.util.List;

import me.chonchol.andropos.model.Category;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiInterfacae {

    @GET("/category")
    Call<List<Category>> getAllCategories();

    @GET("/category/{cat_id}")
    Call<Category> getCategoryById(@Path("cat_id") int catId);
}
