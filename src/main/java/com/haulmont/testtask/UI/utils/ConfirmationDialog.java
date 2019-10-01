package com.haulmont.testtask.UI.utils;

import com.vaadin.ui.Component;
import com.vaadin.ui.Label;

/**
 * Modal dialog to user action confirmation
 * @author korolevia
 */
public class ConfirmationDialog extends ModalForm {

    /**
     *
     * @param ActionText
     * @param action
     */
    public ConfirmationDialog(String ActionText, IUICommand action) {
        super("Confirm action"); 
        this.setHeight("200px");
        this.setWidth("400px");
        Label label = new Label(ActionText);
        label.setSizeUndefined();
        this.setModalContent(label);
        this.action = action;
    }
}