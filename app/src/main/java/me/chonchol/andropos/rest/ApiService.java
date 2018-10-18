package me.chonchol.andropos.rest;

import java.util.List;

import me.chonchol.andropos.model.Category;
import me.chonchol.andropos.model.Customer;
import me.chonchol.andropos.model.Product;
import me.chonchol.andropos.model.Quotation;
import me.chonchol.andropos.model.QuotationList;
import me.chonchol.andropos.model.Sale;
import me.chonchol.andropos.model.Stock;
import me.chonchol.andropos.model.Subcategory;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {

    //CATEGORY
    @GET("/api/category")
    Call<List<Category>> getAllCategories();

    @GET("/api/category/{cat_id}")
    Call<Category> getCategoryById(@Path("cat_id") int catId);

    @POST("/api/category")
    Call<Category> saveCategory(@Body Category category);


    //SUBCATEGORY
    @GET("/api/subcategory")
    Call<List<Subcategory>> getAllSubcategories();

    @GET("/api/subcategory/cat/{cat_id}")
    Call<List<Subcategory>> getSubcategoryListByCatId(@Path("cat_id") Integer id);

    @POST("/api/subcategory")
    Call<Subcategory> saveSubcategory(@Body Subcategory subcategory);


    //PRODUCTS
    @GET("/api/product")
    Call<List<Product>> getAllProducts();

    @POST("/api/product")
    Call<Product> saveProduct(@Body Product product);

    //STOCK
    @GET("/api/stock")
    Call<List<Stock>> getAllStockList();

    @POST("/api/stock")
    Call<Stock> saveStock(@Body Stock stock);

    @GET("/api/stock/available/{quantity}")
    Call<List<Stock>> getAvailableStockList(@Path("quantity") Integer quantity);

    @POST("/api/stock/{product_id}")
    Call<Stock> updateStock(@Path("product_id") Integer id);

    //CUSTOMER
    @POST("/api/customer")
    Call<Customer> createCustomer(@Body Customer customer);

    //QUOTATION
    @POST("/api/quotation")
    Call<Quotation> createQuotation(@Body Quotation quotation);

    //QUOTATIONLIST
    @POST("/api/quotationlist")
    Call<QuotationList> createQuotationList(@Body QuotationList quotationlist);

    //SALE
    @POST("/api/sale")
    Call<Sale> createSale(@Body Sale sale);


}
