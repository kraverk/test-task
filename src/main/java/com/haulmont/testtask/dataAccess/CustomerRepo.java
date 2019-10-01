package com.haulmont.testtask.dataAccess;

import com.haulmont.testtask.model.Customer;
import org.apache.commons.dbcp2.BasicDataSource;

/**
 *
 * @author korolevia
 */
public class CustomerRepo extends AbstractRepo<Customer> {

    /**
     *
     * @param datasource
     */
    public CustomerRepo(BasicDataSource datasource) {
        super(datasource, Customer.class);
    }
}
