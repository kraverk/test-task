package com.haulmont.testtask.UI;

import com.haulmont.testtask.UI.utils.TableView;
import com.haulmont.testtask.dataAccess.DBContext;
import com.haulmont.testtask.dataAccess.DoctorRepo;
import com.haulmont.testtask.dataAccess.PrescriptionRepo;
import com.haulmont.testtask.model.Doctor;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SingleSelectionModel;
import com.vaadin.ui.Notification;

/**
 *
 * @author korolevia
 */
public class DoctorView extends TableView {

    final PrescriptionRepo prescriptRepo;

    /**
     * TableView fo Doctors
     */
    public DoctorView() {
        super(new DoctorRepo(DBContext.getDataSource()), Doctor.class);
        prescriptRepo = new PrescriptionRepo(DBContext.getDataSource());
    }

    /**
     * Attach filter fields while drawGrid
     */
    @Override
    protected void drawGrid() {
        super.drawGrid(); 
        Grid grid = getTable();
        grid.addSelectionListener(selectionEvent -> { // Java 8
            // Get selection from the selection model
            Object selected = ((SingleSelectionModel)
                grid.getSelectionModel()).getSelectedRow();

            if (selected != null)
                Notification.show(
                    String.format("Selected doctor has %s receipts",
                    getReceiptCount((long)grid.getContainerDataSource().getItem(selected)
                        .getItemProperty("id").getValue())));
        });        
    }
    
    private int getReceiptCount(Long doctorId) {
        return prescriptRepo.findByDoctorId(doctorId).size();
    }
    
}
