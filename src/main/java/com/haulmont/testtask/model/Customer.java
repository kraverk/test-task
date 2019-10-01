package com.haulmont.testtask.model;

import com.haulmont.testtask.annotations.IsModel;

/**
 *
 * @author korolevia
 */
@IsModel(TableName="CUSTOMER")
public class Customer extends Human {

    private String phone;

    /**
     * Customer Bean
     */
    public Customer() {
        super();
        this.phone = "";
    }

    /**
     *
     * @return
     */
    public String getPhone() {
        return phone;
    }

    /**
     *
     * @param phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return String.format("Customer[id=%d, firstName='%s', lastName='%s', middleName='%s', phone='%s']", getId(),
                        getFirstName(), getLastName(), getMiddleName(), phone);
    }
}
