package com.haulmont.testtask.dataAccess;

import com.haulmont.testtask.model.Doctor;
import java.util.Map;
import org.apache.commons.dbcp2.BasicDataSource;

/**
 *
 * @author korolevia
 */
public class DoctorRepo extends AbstractRepo<Doctor> {

    /**
     *
     * @param datasource
     */
    public DoctorRepo(BasicDataSource datasource) {
        super(datasource, Doctor.class);
    }
}
