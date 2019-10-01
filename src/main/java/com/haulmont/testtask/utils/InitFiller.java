package com.haulmont.testtask.utils;

import java.io.InputStream;
import java.sql.SQLException;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

/**
 *
 * @author korolevia
 */
public class InitFiller {
    String SQL_DEPLOY_PATH = "/sql/deploy";
    private BasicDataSource datasource; 
    
    /**
     * Call init after create
     * @param datasource
     */
    public InitFiller(BasicDataSource datasource) {
        this.datasource = datasource;    
    }
    
    static String convertStreamToString(InputStream is) {
            java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
            return s.hasNext() ? s.next() : "";
    }     

    /**
     * Spill scripts to DB
     */
    public void Init() {
        try {
            QueryRunner run = new QueryRunner(datasource);
            ResultSetHandler<Integer> rsh = new ScalarHandler<>("FResult"); // by first column
            int schemaSet = run.query("select isnull(max(1), 0) as FResult from INFORMATION_SCHEMA.TABLES where lower(table_name) = 'tprescription'", rsh);
            
            if (schemaSet == 0) {
                System.out.println("Initializing DB !!!");
                
                ResourceScanner rs = new ResourceScanner();
                for (String file : rs.getResourceFiles(SQL_DEPLOY_PATH)) {
                    
                    System.out.println(String.format("Spilling %s", file) );
                    run.execute(convertStreamToString(rs.getResourceAsStream(SQL_DEPLOY_PATH + "/" + file)), new Object[0][]);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
