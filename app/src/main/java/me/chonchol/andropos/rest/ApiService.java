package me.chonchol.andropos.rest;

import java.util.List;

import me.chonchol.andropos.model.Category;
import me.chonchol.andropos.model.Product;
import me.chonchol.andropos.model.Stock;
import me.chonchol.andropos.model.Subcategory;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {


    //CATEGORY
    @GET("/category")
    Call<List<Category>> getAllCategories();

    @GET("/category/{cat_id}")
    Call<Category> getCategoryById(@Path("cat_id") int catId);

    @POST("/category")
    Call<Category> saveCategory(@Body Category category);


    //SUBCATEGORY
    @GET("/subcategory")
    Call<List<Subcategory>> getAllSubcategories();

    @GET("/subcategory/cat/{cat_id}")
    Call<List<Subcategory>> getSubcategoryListByCatId(@Path("cat_id") Integer id);

    @POST("/subcategory")
    Call<Subcategory> saveSubcategory(@Body Subcategory subcategory);


    //PRODUCTS
    @GET("/product")
    Call<List<Product>> getAllProducts();

    @POST("/product")
    Call<Product> saveProduct(@Body Product product);


    //STOCK
    @GET("/stock")
    Call<List<Stock>> getAllStockList();

    @POST("/stock")
    Call<Stock> saveStock(@Body Stock stock);
}
