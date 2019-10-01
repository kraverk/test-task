package com.haulmont.testtask.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author korolevia
 */
public class ResourceScanner {

    /**
     * Travers path and get files in resources
     * @param path
     * @return List of String
     */
    public List<String> getResourceFiles(String path) {
        List<String> filenames = new ArrayList<>();

        try (
            InputStream in = getResourceAsStream(path);
            BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
                String resource;

                while ((resource = br.readLine()) != null) {
                    filenames.add(resource);
                }
        } catch (IOException e) {
        }

        return filenames;
    }

    /**
     * Get resource as stream
     * @param resource
     * @return Stream
     */
    public InputStream getResourceAsStream(String resource) {
        final InputStream in
                = getContextClassLoader().getResourceAsStream(resource);

        return in == null ? getClass().getResourceAsStream(resource) : in;
    }

    private ClassLoader getContextClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }    
}
