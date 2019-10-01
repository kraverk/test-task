package com.haulmont.testtask.UI.utils;

import com.haulmont.testtask.dataAccess.AbstractModel;
import com.haulmont.testtask.dataAccess.AbstractRepo;
import com.haulmont.testtask.dataAccess.DBContext;
import com.haulmont.testtask.dataAccess.DoctorRepo;
import com.haulmont.testtask.model.Doctor;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author korolevia
 * @param <T>
 */
public class TableView <T extends AbstractModel> extends VerticalLayout implements View {

    /**
     *
     * @return
     */
    public Grid getTable() {
        return table;
    }

    private enum ButtonType {
        NEW ("New"),
        EDIT("Edit"),
        DELETE("Delete");
        
        private String title;

        private ButtonType(String title) {
            this.title = title;
        }
    }
    
    private class ButtonClickListener implements Button.ClickListener{
        private final ButtonType buttonType;
        private final Grid table;
        private final Class<?> clazz;
        private ModalForm modalForm;

        public ButtonClickListener(ButtonType buttonType, Grid table, Class<?> clazz) {
            this.buttonType = buttonType;
            this.table = table;
            this.clazz = clazz;
        }
        
        @Override
        public void buttonClick(Button.ClickEvent event) {
            T item = null;
            if (this.buttonType == ButtonType.NEW) {
                try {
                    item = (T) clazz.newInstance();
                } catch (InstantiationException | IllegalAccessException ex) {
                    Logger.getLogger(TableView.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                item = (T) this.table.getSelectedRow();
            }
            
            if (item == null) return;
            
            if (buttonType == ButtonType.DELETE) {
                modalForm = new ConfirmationDialog("Are you sure to delete??", new deleteCommand(item));
            } else {    
                BeanModalForm editorForm = getEditorForm();
                if (editorForm == null) { 
                    editorForm = new BeanModalForm(clazz);
                }
                editorForm.setAction(new persistCommand(item));
                editorForm.setItem(item); 
                
                modalForm = editorForm;
            }
            UI.getCurrent().addWindow(modalForm);
        }
    }
    
    private class persistCommand implements IUICommand {
        private final T item;

        public persistCommand(T item) {
            this.item = item;
        }
        
        @Override
        public void perform() {
            repo.persist(item);
            containerRefresh();
        }
    }

    private class deleteCommand implements IUICommand {
        private final T item;

        public deleteCommand(T item) {
            this.item = item;
        }
        
        @Override
        public void perform() {
            if (repo.delete(item))  {
                container.removeItem(item);
            };
        }
    }
    
    private final AbstractRepo repo;
    private final BeanItemContainer<T> container; 
    private final Grid table = new Grid("List");
    private final Class<?> clazz;
    
    /**
     *
     * @param repo
     * @param clazz
     */
    public TableView( AbstractRepo repo, Class<?> clazz) {
        super();
        this.repo = repo;
        this.clazz = clazz;
        this.container = new BeanItemContainer<T>((Class<? super T>) this.clazz);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        containerRefresh();

        if (this.getComponentCount() == 0) {
            reconstructView();
        } 
                
        setSizeFull();
        setMargin(true);
    }
    
    private void containerRefresh() {
        container.removeAllItems();
        container.addAll(repo.findAll());
    }
    
    /**
     *
     * @return
     */
    protected BeanModalForm getEditorForm() {
        return null;
    }
    
    /**
     * Called once when no components exists on this
     */
    protected void reconstructView() {
        drawGrid();
        drawButtons();
    }
    
    /**
     * Adds grid
     */
    protected void drawGrid() {
        table.setContainerDataSource(container);
        table.setEditorEnabled(false);
        
        table.getColumn("id").setHidden(true);
        
        table.setSizeFull();
        
        addComponent(table);
        setExpandRatio(table, 1.0F);
    }
    
    /**
     * Adds bottom panel with buttons
     */
    protected void drawButtons() {
        HorizontalLayout h = new HorizontalLayout();
        
        for (ButtonType value : ButtonType.values()) {
            Button btn = new Button(value.title);
            btn.addClickListener(new ButtonClickListener(value, table, clazz));
            h.addComponent(btn);
        }
        
        addComponent(h);
    }
}
