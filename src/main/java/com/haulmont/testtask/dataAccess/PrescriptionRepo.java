package com.haulmont.testtask.dataAccess;

import com.haulmont.testtask.model.Doctor;
import com.haulmont.testtask.model.Prescription;
import java.util.List;
import java.util.Map;
import org.apache.commons.dbcp2.BasicDataSource;

/**
 *
 * @author korolevia
 */
public class PrescriptionRepo extends AbstractRepo<Prescription> {
    DoctorRepo doctorRepo;
    CustomerRepo customerRepo;
    
    /**
     *
     * @param datasource
     */
    public PrescriptionRepo(BasicDataSource datasource) {
        super(datasource, Prescription.class);
        doctorRepo = new DoctorRepo(datasource);
        customerRepo = new CustomerRepo(datasource);
    }
    
    private Prescription updateReferences(Prescription item) {
        item.setDoctor(doctorRepo.findById(item.getDoctorId())) ;
        item.setCustomer(customerRepo.findById(item.getCustomerId())) ;
        return item;
    }

    private List<Prescription> updateReferences(List<Prescription> items) {
        items.forEach( e-> updateReferences(e));
        return items;
    }

    /**
     *
     * @param nameFilter
     * @return
     */
    @Override
    public List<Prescription> findByName(String nameFilter) {
        return updateReferences(super.findByName(nameFilter)); 
    }

    /**
     *
     * @return
     */
    @Override
    public List<Prescription> findAll() {
        return updateReferences(super.findAll()); 
    }

    /**
     *
     * @param Id
     * @return
     */
    @Override
    public Prescription findById(Long Id) {
        return updateReferences(super.findById(Id)); 
    }
    
    @Override
    protected void setupPropsBeforePersit(Map<String, Object> props) {
        super.setupPropsBeforePersit(props); 
        props.remove("doctor");
        props.remove("customer");
    }
    
    /**
     *
     * @param doctorId
     * @return
     */
    public List<Prescription> findByDoctorId(Long doctorId){
        return updateReferences(super.findByField("doctorId", doctorId)); 
    }
}
