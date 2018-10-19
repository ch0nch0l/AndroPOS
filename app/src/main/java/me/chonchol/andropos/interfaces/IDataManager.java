package me.chonchol.andropos.interfaces;

import java.util.List;

import me.chonchol.andropos.model.CartProduct;
import me.chonchol.andropos.model.Customer;
import me.chonchol.andropos.model.Product;

public interface IDataManager {

    void setCustomerData(Customer customer);

    Customer getCustomerData();

    void setCartProductList(List<CartProduct> productList);

    List<CartProduct> getCartProductList();
}
