package com.haulmont.testtask.dataAccess;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.commons.dbcp2.BasicDataSource;

/**
 *
 * @author korolevia
 */
public class DBContext {
    private static BasicDataSource dataSource;
 
    /**
     *
     * @return
     */
    public static BasicDataSource getDataSource() {
        if (dataSource == null)
        {
            BasicDataSource ds = new BasicDataSource();
            
            Properties prop = getProps();
            
            ds.setUrl(prop.getProperty("db.url")); 
            ds.setDriverClassName(prop.getProperty("db.driver"));
            ds.setUsername(prop.getProperty("db.user"));
            ds.setPassword(prop.getProperty("db.password"));
 
            ds.setMinIdle(5);
            ds.setMaxIdle(10);
            ds.setMaxOpenPreparedStatements(100);
 
            dataSource = ds;
        }
        return dataSource;
    }   
    
    static Properties getProps() {
            
        Properties prop = new Properties();
        prop.setProperty("db.driver", "org.hsqldb.jdbcDriver");
        prop.setProperty("db.user", "SA");
        prop.setProperty("db.password", "");
        
        try (InputStream input = DBContext.class.getClassLoader().getResourceAsStream("db.properties")) {

            prop.load(input);

            System.out.println(prop.getProperty("db.url"));
            System.out.println(prop.getProperty("db.driver"));
            System.out.println(prop.getProperty("db.user"));
            System.out.println(prop.getProperty("db.password"));

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return prop;
    }
}
