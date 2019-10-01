package com.haulmont.testtask.model;

import com.haulmont.testtask.dataAccess.AbstractModel;

/**
 *
 * @author korolevia
 */
public abstract class Human extends AbstractModel{

    private String firstName;

    private String lastName;

    private String middleName;

    /**
     * Human Bean
     */
    public Human() {
        this.firstName = "";
        this.lastName = "";
        this.middleName = "";
    }

    /**
     *
     * @return
     */
    public String getFirstName() {
            return firstName;
    }

    /**
     *
     * @param firstName
     */
    public void setFirstName(String firstName) {
            this.firstName = firstName;
    }

    /**
     *
     * @return
     */
    public String getLastName() {
            return lastName;
    }

    /**
     *
     * @param lastName
     */
    public void setLastName(String lastName) {
            this.lastName = lastName;
    }

    /**
     *
     * @return
     */
    public String getMiddleName() {
            return middleName;
    }

    /**
     *
     * @param middleName
     */
    public void setMiddleName(String middleName) {
            this.middleName = middleName;
    }
    
    /**
     *
     * @return
     */
    @Override
    public String toPresentationName() {
        return String.format("%s %s %s", getFirstName(), getMiddleName(), getLastName());
    }    
}
