package com.haulmont.testtask;

import com.haulmont.testtask.UI.*;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 * @author korolevia
 */
@Theme(ValoTheme.THEME_NAME)
public class MainUI extends UI {
    
    private class EmptyView extends VerticalLayout implements View {

        @Override
        public void enter(ViewChangeListener.ViewChangeEvent event) {
            //throw new UnsupportedOperationException("Not supported yet."); 
        }
    }
    
    private Navigator navigator;
    
    ClickListener navListener = new ClickListener() {
        @Override
        public void buttonClick(Button.ClickEvent event) {
            navigator.navigateTo(event.getButton().getCaption());
        }
    };
    
    @Override
    protected void init(VaadinRequest request) {
        
        VerticalLayout leftMenu = new VerticalLayout();
        leftMenu.setMargin(true);

        leftMenu.addComponent(new Label("Main UI"));
        
        // Customers link
        Button customersButton = new Button("Customers");
        customersButton.addClickListener(navListener);
        leftMenu.addComponent(customersButton);

        // Doctors link
        Button doctorsButton = new Button("Doctors");
        doctorsButton.addClickListener(navListener);
        leftMenu.addComponent(doctorsButton);

        // Prescription link
        Button PrescrButton = new Button("Prescriptions");
        PrescrButton.addClickListener(navListener);
        leftMenu.addComponent(PrescrButton);

        // Container
        VerticalLayout contentContainer = new VerticalLayout();
        contentContainer.setSizeFull();
  
        navigator = new Navigator(this, contentContainer);
        
        navigator.addView("", new EmptyView());
        navigator.addView("Customers", new CustomerView());
        navigator.addView("Doctors", new DoctorView());
        navigator.addView("Prescriptions", new PrescriptionView());
        
        for (Component component : leftMenu) {
            component.setWidth("100%");
        }        
        
        HorizontalSplitPanel horizont = new HorizontalSplitPanel(leftMenu, contentContainer);
        horizont.setSizeFull();
        horizont.setSplitPosition(15F);
        
        setContent(horizont);
    }
    
}