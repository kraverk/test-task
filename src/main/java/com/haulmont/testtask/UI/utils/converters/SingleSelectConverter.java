package com.haulmont.testtask.UI.utils.converters;

import com.haulmont.testtask.dataAccess.AbstractModel;
import com.vaadin.data.Container;
import java.util.Locale;

import com.vaadin.data.util.converter.Converter;
import com.vaadin.ui.AbstractSelect;
import java.util.Collection;
import java.util.Objects;

/**
 *
 * @author korolevia
 * @param <T>
 * @param <TPK>
 */
public class SingleSelectConverter<T extends AbstractModel, TPK extends Number> implements Converter<Object, TPK> {

    private final AbstractSelect select;
    private final Class<TPK> clazz_to;

    /**
     *
     * @param select
     * @param clazz_to
     */
    public SingleSelectConverter(AbstractSelect select, Class<TPK> clazz_to) {
        this.select = select;
        this.clazz_to = clazz_to;
    }

    private Container getContainer() {
        return select.getContainerDataSource();
    }

    public Class<TPK> getModelType() {
        return clazz_to;
    }

    public Class<Object> getPresentationType() {
        return Object.class;
    }

    @Override
    public Object convertToPresentation(TPK value, Class<? extends Object> targetType, Locale locale) throws ConversionException {
        if (value != null) {
             Collection<T> items = (Collection<T>) (getContainer().getItemIds());
             for (Object item : items) {
                 if ((value instanceof Long) && (Objects.equals((Long)value, ((T)item).getId()))) return (T) item;
                 if ((value instanceof Integer) && (Objects.equals(Long.valueOf((Integer)value), ((T)item).getId()))) return (T) item;
             }
            return null;
        }
        return null; 
    }

    @Override
    public TPK convertToModel(Object value, Class<? extends TPK> targetType, Locale locale) throws ConversionException {
        if (value != null) {
            if (targetType == Integer.class) {return (TPK)(Integer)((T)value).getId().intValue();}
            if (targetType == Long.class) {return (TPK)((T)value).getId();}
        }
        return null;
    }
}