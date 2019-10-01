package com.haulmont.testtask.model;

import com.haulmont.testtask.annotations.IsModel;

/**
 *
 * @author korolevia
 */
@IsModel(TableName="Doctor")
public class Doctor extends Human {

    private String specialization;

    /**
     * Doctor Bean
     */
    public Doctor() {
        super();
        this.specialization = "";
    }

    /**
     *
     * @return
     */
    public String getSpecialization() {
        return specialization;
    }

    /**
     *
     * @param specialization
     */
    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    @Override
    public String toString() {
        return String.format("Doctor[id=%d, firstName='%s', lastName='%s', middleName='%s', specialization='%s']", getId(),
                        getFirstName(), getLastName(), getMiddleName(), specialization);
    }
}
