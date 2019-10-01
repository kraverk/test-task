package com.haulmont.testtask.UI.utils.converters;

import com.haulmont.testtask.dataAccess.PriorityRepo;
import com.haulmont.testtask.model.Prescription;
import com.haulmont.testtask.model.Priority;
import com.vaadin.data.util.converter.Converter;
import java.util.Locale;

/**
 *
 * @author korolevia
 */
public class StringToPriorityConverter implements Converter<String, Integer> {
    
    final PriorityRepo repo =  new PriorityRepo();
    
    @Override
    public Integer convertToModel(String value, Class<? extends Integer> targetType, Locale locale) throws Converter.ConversionException {
        if (value == null) return null;
        
        Priority val = repo.findByName(value).stream().findAny().get();
        return val.getIntId();
    }

    @Override
    public String convertToPresentation(Integer value, Class<? extends String> targetType, Locale locale) throws Converter.ConversionException {
        return repo.findById(Long.valueOf(value)).getName();
    }

    @Override
    public Class<Integer> getModelType() {
        return Integer.class;
    }

    @Override
    public Class<String> getPresentationType() {
        return String.class;
    } 
}
