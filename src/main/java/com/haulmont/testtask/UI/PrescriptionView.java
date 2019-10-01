package com.haulmont.testtask.UI;

import java.text.SimpleDateFormat;
import com.haulmont.testtask.UI.utils.BeanModalForm;
import com.haulmont.testtask.UI.utils.converters.ModelToStringConverter;
import com.haulmont.testtask.UI.utils.converters.StringToPriorityConverter;
import com.haulmont.testtask.UI.utils.TableView;
import com.haulmont.testtask.dataAccess.DBContext;
import com.haulmont.testtask.dataAccess.PrescriptionRepo;
import com.haulmont.testtask.model.Customer;
import com.haulmont.testtask.model.Doctor;
import com.haulmont.testtask.model.Prescription;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.HeaderCell;
import com.vaadin.ui.Grid.HeaderRow;
import com.vaadin.ui.TextField;
import com.vaadin.ui.renderers.DateRenderer;
import java.util.Iterator;

/**
 *
 * @author korolevia
 */
public class PrescriptionView extends TableView {

    /**
     * Prescription Table View
     */
    public PrescriptionView() {
        super(new PrescriptionRepo(DBContext.getDataSource()), Prescription.class);
    }

    /**
     *
     * @return
     */
    @Override
    protected BeanModalForm getEditorForm() {
        return new PrescriptionForm();
    }
    
    /**
     *
     */
    @Override
    protected void drawGrid() {
        super.drawGrid();
        final Grid grid = this.getTable();
        BeanItemContainer<Prescription> container = (BeanItemContainer<Prescription>) grid.getContainerDataSource();
        
        // Hide the ids columns
        grid.getColumn("customerId").setHidden(true);
        grid.getColumn("doctorId").setHidden(true);
        
        // Converters
        grid.getColumn("priority").setConverter(new StringToPriorityConverter());
        grid.getColumn("customer").setConverter(new ModelToStringConverter<>(Customer.class));
        grid.getColumn("doctor").setConverter(new ModelToStringConverter<>(Doctor.class));
        
        //Renderers
        grid.getColumn("validityDate").setRenderer(new DateRenderer(new SimpleDateFormat("yyyy.MM.dd")));
        grid.getColumn("createdDate").setRenderer(new DateRenderer(new SimpleDateFormat("yyyy.MM.dd HH:mm")));
        
        // Create a header row to hold column filters
        HeaderRow filterRow = grid.appendHeaderRow();

        // Set up a filter for all columns
        for (Iterator<?> it = grid.getContainerDataSource()
                .getContainerPropertyIds()
                .stream()
                .filter(p -> "customer".equalsIgnoreCase((String) p) 
                          || "description".equalsIgnoreCase((String) p) 
                          || "priority".equalsIgnoreCase((String) p) 
                       )
                .iterator(); it.hasNext();) {
            Object pid = it.next();
            HeaderCell cell = filterRow.getCell(pid);
            // Have an input field to use for filter
            TextField filterField = new TextField();

            filterField.setWidth("100%");
            // Update filter When the filter input is changed
            filterField.addTextChangeListener(change -> {
                // Can't modify filters so need to replace
                container.removeContainerFilters(pid);

                // (Re)create the filter if necessary
                if (! change.getText().isEmpty())
                    container.addContainerFilter(
                            new SimpleStringFilter(pid,
                                    change.getText(), true, false));
            });
            cell.setComponent(filterField);
        }        
            }
    
    
}
