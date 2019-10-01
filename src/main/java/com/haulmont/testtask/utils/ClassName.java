package com.haulmont.testtask.utils;

/**
 *
 * @author korolevia
 */
public class ClassName {

    /**
     * Returns last element in dot-splitted string
     * @param longName
     * @return String
     */
    public static String getShortName(String longName) {
        Object[] arr = longName.split("\\.");
        return (String)arr[arr.length - 1];
    }
}
