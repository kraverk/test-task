package com.haulmont.testtask.UI.utils;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 *
 * @author korolevia
 */
public abstract class ModalForm extends Window{
    
    final VerticalLayout contentHolder;
    Component  modalContent; 
    IUICommand action;

    /**
     *
     * @return
     */
    public Component getModalContent() {
        return this.modalContent;
    }
    
    /**
     *
     * @param content
     */
    public void setModalContent(Component content) {
        if (this.modalContent == null) {
            this.modalContent = content;
            this.contentHolder.addComponent(this.modalContent);
            this.contentHolder.setComponentAlignment(this.modalContent,  Alignment.MIDDLE_CENTER);
            this.contentHolder.setMargin(true);
            this.contentHolder.setSizeFull();
            
            this.setClosable(false);
            this.setResizable(false);
            
            // Trivial logic for closing the sub-window
            Button ok = new Button("OK");
            ok.addClickListener(new Button.ClickListener() {
                public void buttonClick(Button.ClickEvent event) {
                    if (beforeOK()) {
                        doOK();
                    }
                }
            });
            Button cancel = new Button("Cancel");
            cancel.addClickListener(new Button.ClickListener() {
                public void buttonClick(Button.ClickEvent event) {
                    if (beforeCancel()) {
                        doCancel(); 
                    }
                }
            });
            HorizontalLayout h = new HorizontalLayout(ok, cancel);
            this.contentHolder.addComponent(h);
            this.contentHolder.setComponentAlignment(h, Alignment.BOTTOM_CENTER);
        }
    }
    
    /**
     *
     * @param action
     */
    public void setAction(IUICommand action) {
        this.action = action;
    }
    
    /**
     *
     * @return
     */
    protected boolean beforeOK() {
        return (action != null);
    } 

    /**
     *
     * @return
     */
    protected boolean beforeCancel() {
        return true;
    } 
    
    /**
     *
     * @param caption
     */
    public ModalForm(String caption) {
        super(caption);

        this.contentHolder = new VerticalLayout();
        this.setContent(contentHolder);
        
        this.setModal(true);
    }

    /**
     * Do OK button click
     */
    protected void doOK() {
        action.perform();
        close(); 
    }
    
    /**
     * Closes form
     */
    protected void doCancel() {
        close(); 
    }
    
}
