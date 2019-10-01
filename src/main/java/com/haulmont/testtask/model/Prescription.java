package com.haulmont.testtask.model;

import com.haulmont.testtask.annotations.IsModel;
import com.haulmont.testtask.dataAccess.AbstractModel;
import java.sql.Date;

/**
 *
 * @author korolevia
 */
@IsModel(TableName="tPrescription")
public class Prescription extends AbstractModel{
    
    private Doctor doctor;

    private Customer customer;

    /**
     *
     * @return
     */
    public Doctor getDoctor() {
        return doctor;
    }

    /**
     *
     * @param doctor
     */
    public void setDoctor(Doctor doctor) {
        
        if (this.doctor != doctor) {
            setDoctorId((doctor == null)? null : doctor.getId());
            this.doctor = doctor;
        };
    }

    /**
     *
     * @return
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     *
     * @param customer
     */
    public void setCustomer(Customer customer) {
        if (this.customer != customer) {
            setCustomerId((customer == null)? null : customer.getId());
            this.customer = customer;
        };
    }
    
    private Long doctorId;

    private Long customerId;

    private String description;

    private Date createdDate;

    private Date validityDate;

    private int priority;

    /**
     * Prescription Bean
     */
    public Prescription() {
        this.doctorId = null;
        this.customerId = null;
        this.description = "";
        this.validityDate = null;
        this.priority = 0;
    }

    /**
     *
     * @return
     */
    public Long getDoctorId() {
        return doctorId;
    }

    /**
     *
     * @param doctorId
     */
    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    /**
     *
     * @return
     */
    public Long getCustomerId() {
        return customerId;
    }

    /**
     *
     * @param customerId
     */
    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    /**
     *
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return
     */
    public Date getValidityDate() {
        return validityDate;
    }

    /**
     *
     * @param validityDate
     */
    public void setValidityDate(Date validityDate) {
        this.validityDate = validityDate;
    }

    /**
     *
     * @return
     */
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     *
     * @param createdDate
     */
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    /**
     *
     * @return
     */
    public int getPriority() {
        return priority;
    }

    /**
     *
     * @param priority
     */
    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return String.format("Prescription[id=%d, customerId='%s', doctorId='%s', description='%s', createdDate='%s', validityDate='%s']", getId(),
                        customerId, doctorId, description, createdDate, validityDate);
    }
}
