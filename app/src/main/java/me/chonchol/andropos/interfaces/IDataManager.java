package me.chonchol.andropos.interfaces;

import java.util.List;

import me.chonchol.andropos.model.Customer;
import me.chonchol.andropos.model.Product;

public interface IDataManager {

    void customerData(Customer customer);

    Customer getCustomerData();

    void cartProducts(List<Product> productList);

    List<Product> getCartProductList();
}
