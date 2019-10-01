package com.haulmont.testtask.UI.utils.converters;

import com.haulmont.testtask.dataAccess.AbstractModel;
import com.vaadin.data.util.converter.Converter;
import java.util.Locale;

/**
 *
 * @author korolevia
 * @param <T>
 */

public class ModelToStringConverter<T extends AbstractModel> implements Converter<String, T> {

    Class<T> clazz;

    /**
     *
     * @param clazz
     */
    public ModelToStringConverter(Class<T> clazz) {
        this.clazz = clazz;
    }
    
    
    @Override
    public T convertToModel(String value, Class<? extends T> targetType, Locale locale) throws Converter.ConversionException {
        return null;
    }

    @Override
    public String convertToPresentation(T value, Class<? extends String> targetType, Locale locale) throws Converter.ConversionException {
        if (value == null) return null;
        return value.toPresentationName();
    }

    @Override
    public Class<T> getModelType() {
        return clazz;
    }

    @Override
    public Class<String> getPresentationType() {
        return String.class;
    }
}
