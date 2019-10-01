package com.haulmont.testtask.UI;

import com.haulmont.testtask.UI.utils.BeanModalForm;
import com.haulmont.testtask.dataAccess.AbstractModel;
import com.haulmont.testtask.model.Prescription;
import com.haulmont.testtask.UI.utils.converters.SingleSelectConverter;
import com.haulmont.testtask.dataAccess.AbstractRepo;
import com.haulmont.testtask.dataAccess.CustomerRepo;
import com.haulmont.testtask.dataAccess.DBContext;
import com.haulmont.testtask.dataAccess.DoctorRepo;
import com.haulmont.testtask.dataAccess.PriorityRepo;
import com.haulmont.testtask.model.Customer;
import com.haulmont.testtask.model.Doctor;
import com.haulmont.testtask.model.Priority;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import java.util.List;

/**
 *
 * @author korolevia
 */
public class PrescriptionForm extends BeanModalForm<Prescription> {
    
    private class ContainerFiller<T extends AbstractModel> {
        
        private void fillContainer(AbstractRepo repo, BeanItemContainer container) {
            List<T> list = repo.findAll();
            list.forEach((T e) -> {
                BeanItem<T> b = container.addBean(e);
                b.addItemProperty("presentationName", new ObjectProperty(e.toPresentationName()));
                container.addItem(b);
            });
        }
    }
    
    final DoctorRepo doctorRepo = new DoctorRepo(DBContext.getDataSource());
    final BeanItemContainer<Doctor> doctorContainer = new BeanItemContainer<>(Doctor.class);

    final CustomerRepo customerRepo = new CustomerRepo(DBContext.getDataSource());
    final BeanItemContainer<Customer> customerContainer = new BeanItemContainer<>(Customer.class);

    final PriorityRepo priorityRepo = new PriorityRepo();
    final BeanItemContainer<Priority> priorityContainer = new BeanItemContainer<>(Priority.class);
    
    /**
     * Prescription edit form
     */
    public PrescriptionForm() {
        super(Prescription.class);
    }

    /**
     * Form fields setup
     * @param propertyId
     * @return
     */
    @Override
    protected Component setupProperty(String propertyId) {

        if (AbstractModel.CREATED_DATE.equalsIgnoreCase(propertyId)) return null;

        if ("customer".equalsIgnoreCase(propertyId)) return null;
        if ("doctor".equalsIgnoreCase(propertyId)) return null;

        if ("customerId".equalsIgnoreCase(propertyId)) {
            ComboBox select = new ComboBox("Customer");

            select.setWidth("100%");
            select.setNullSelectionAllowed(false);

            (new ContainerFiller<Doctor>()).fillContainer(customerRepo, customerContainer);

            select.setContainerDataSource(customerContainer);
            select.setItemCaptionPropertyId("presentationName");

            getBinder().bind(select, propertyId);
            select.setConverter(new SingleSelectConverter<Customer, Long>(select, Long.class));
            
            return select;
        }
        if ("doctorId".equalsIgnoreCase(propertyId)) {
            ComboBox select = new ComboBox("Doctor");

            select.setWidth("100%");
            select.setNullSelectionAllowed(false);

            (new ContainerFiller<Doctor>()).fillContainer(doctorRepo, doctorContainer);
            select.setContainerDataSource(doctorContainer);
            select.setItemCaptionPropertyId("presentationName");

            getBinder().bind(select, propertyId);
            select.setConverter(new SingleSelectConverter<Doctor, Long>(select, Long.class));
            
            return select;
        }        
 
        if ("priority".equalsIgnoreCase(propertyId)) {//return makePriorityCombo(propertyId);
            ComboBox select = new ComboBox("Priority");

            select.setWidth("100%");
            select.setNullSelectionAllowed(false);

            //priorityContainer.setBeanIdProperty("intId");
            priorityContainer.addAll(priorityRepo.findAll());
            select.setContainerDataSource(priorityContainer);

            getBinder().bind(select, propertyId);
            select.setConverter(new SingleSelectConverter<Priority, Integer>(select, Integer.class));
            
            return select; 
        }        
        return super.setupProperty(propertyId); 
    }

}
