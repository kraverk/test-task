package com.haulmont.testtask;

import com.haulmont.testtask.dataAccess.DBContext;
import com.haulmont.testtask.utils.InitFiller;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import org.apache.commons.dbutils.QueryRunner;

/**
 *
 * @author korolevia
 */
@WebListener
public class WebAppListener implements ServletContextListener {

    /**
     *
     * @param sce
     */
    @Override
    public void contextInitialized ( ServletContextEvent sce ) {
        System.out.println ( "My Vaadin web app is starting. " );
        
        InitFiller init = new InitFiller(DBContext.getDataSource());
        init.Init();
    }

    /**
     *
     * @param sce
     */
    @Override
    public void contextDestroyed ( ServletContextEvent sce ) {
        System.out.println ( "My Vaadin web app is shutting down." );
        QueryRunner run = new QueryRunner(DBContext.getDataSource());
        try {
            run.execute("SHUTDOWN");
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        
    }

}
