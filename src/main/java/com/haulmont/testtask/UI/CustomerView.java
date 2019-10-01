package com.haulmont.testtask.UI;

import com.haulmont.testtask.UI.utils.TableView;
import com.haulmont.testtask.dataAccess.CustomerRepo;
import com.haulmont.testtask.dataAccess.DBContext;
import com.haulmont.testtask.model.Customer;

/**
 *
 * @author korolevia
 */
public class CustomerView  extends TableView {

    /**
     * TableView for Customers
     */
    public CustomerView() {
        super(new CustomerRepo(DBContext.getDataSource()), Customer.class);
    }
}
