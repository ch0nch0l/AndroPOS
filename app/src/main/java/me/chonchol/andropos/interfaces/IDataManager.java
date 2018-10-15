package me.chonchol.andropos.interfaces;

import me.chonchol.andropos.model.Customer;

public interface IDataManager {

    void customerData(Customer customer);

    Customer getCustomerData();
}
