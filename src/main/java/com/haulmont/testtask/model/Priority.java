package com.haulmont.testtask.model;

import com.haulmont.testtask.dataAccess.AbstractModel;

/**
 * To represent Enum in combobox
 * @author korolevia
 */
public class Priority extends AbstractModel {
    
    String name;

    /**
     *
     * @return
     */
    public int getIntId() {
        return super.getId().intValue();
    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @param id
     * @param name
     */
    public Priority(int id, String name) {
        this.name = name;
        this.setId(Long.valueOf(id));        
    }

    @Override
    public String toString() {
        return getName();
    }
}
