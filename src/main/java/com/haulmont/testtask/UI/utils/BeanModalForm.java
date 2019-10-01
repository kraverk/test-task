package com.haulmont.testtask.UI.utils;

import com.haulmont.testtask.dataAccess.AbstractModel;
import com.haulmont.testtask.utils.ClassName;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.UI;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author korolevia
 * @param <TModel>
 */
public class BeanModalForm <TModel extends AbstractModel> extends ModalForm {
    
    TModel item;
    FieldGroup fieldGroup;
    Class<?> itemType;
    
    /**
     *
     * @return
     */
    public Layout getModalContent() {
        return (Layout)super.getModalContent();
    }
    
    /**
     *
     * @return
     */
    public FieldGroup getBinder() {
        return this.fieldGroup;
    }

    /**
     *
     * @param item
     */
    public void setItem(TModel item) {
        this.item = item;
        fieldGroup = new BeanFieldGroup<>(itemType);
        fieldGroup.setItemDataSource(new BeanItem<>(this.item));
        setupForm();
    }
    
    /**
     *
     * @param clazz
     */
    public BeanModalForm(Class<?> clazz) {
        super(String.format("New %s", ClassName.getShortName(clazz.toString())));
        this.itemType = clazz;
        if (this.item != null && this.item.getId() != null) {
            this.setCaption(String.format("Edit %s (ID = %s)", ClassName.getShortName(clazz.toString()), item.getId()));
        };

        this.setHeight("500px");
        this.setWidth("400px");
        
        this.setModalContent(new FormLayout());
    }

    /**
     *
     * @return
     */
    @Override
    protected boolean beforeOK() {
        ModalForm calee = this;
        
        UI.getCurrent().addWindow(new ConfirmationDialog("Are you sure?", new IUICommand() {
            @Override
            public void perform() {
                try {
                    fieldGroup.commit();
                    calee.doOK();
                } catch (FieldGroup.CommitException ex) {
                    Logger.getLogger(BeanModalForm.class.getName()).log(Level.SEVERE, null, ex);
                }            
            }
        }));

        return false; 
    }
    
    /**
     *
     * @param propertyId
     * @return
     */
    protected Component setupProperty(String propertyId) {
        Component cOut;
        cOut = fieldGroup.buildAndBind(propertyId);
        cOut.setWidth("100%");
        return cOut;
    }
    
    private void setupForm() {
        for (Object propertyId : fieldGroup.getUnboundPropertyIds()) {
            if (AbstractModel.PK_ID.equalsIgnoreCase((String) propertyId)) continue;
            
            Component c = setupProperty((String) propertyId);
            if (c != null) {
                this.getModalContent().addComponent(c);
            }
        }
    }
}
