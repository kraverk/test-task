package com.haulmont.testtask.dataAccess;

/**
 *
 * @author korolevia
 */
public abstract class AbstractModel {

    /**
     *
     */
    public static final String PK_ID = "id";

    /**
     *
     */
    public static final String CREATED_DATE = "createdDate";
    private Long id;

    /**
     *
     * @return
     */
    public Long getId() {
            return id;
    }

    /**
     *
     * @param id
     */
    public void setId(Long id) {
            this.id = id;
    }
    
    /**
     *
     * @return
     */
    public String toPresentationName() {
        return this.toString();
    }
}
