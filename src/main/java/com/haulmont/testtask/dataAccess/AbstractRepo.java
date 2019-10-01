package com.haulmont.testtask.dataAccess;

import com.haulmont.testtask.annotations.IsModel;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

/**
 *
 * @author korolevia
 * @param <TModel>
 */
public abstract class AbstractRepo<TModel extends AbstractModel> {
    private static final String _WHERE_ID_ = " where ID = ?";

    private final SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd hh:mm:ss");

    /**
     *
     */
    protected final BasicDataSource datasource; 

    /**
     *
     */
    protected final Class<TModel> clazz;

    /**
     *
     */
    protected final String tableName;

    /**
     *
     * @param datasource
     * @param clazz
     */
    public AbstractRepo(BasicDataSource datasource, Class<TModel> clazz) {
        this.datasource = datasource;
        this.clazz = clazz;
        this.tableName = gatherTableName();
    }

    private String gatherTableName() {
        IsModel isModel = clazz.getAnnotation(IsModel.class);
        if (isModel == null) {
            return "DUAL";
        }
        return isModel.TableName();
    }

    private Map<String, Object> gatherItemValues(TModel item) throws IllegalArgumentException {
        com.fasterxml.jackson.databind.ObjectMapper m = new com.fasterxml.jackson.databind.ObjectMapper();
        m.setDateFormat(formatter);
        Map<String, Object> props = m.convertValue(item, Map.class);
        props.remove("createdDate");
        props.remove(AbstractModel.PK_ID);
        setupPropsBeforePersit(props);
        return props;
    }    
    
    /**
     *
     * @param props
     */
    protected void setupPropsBeforePersit(Map<String, Object> props) {

    }
    
    private Map<String, Object> gatherItemValuesWithId(TModel item) throws IllegalArgumentException {
        Map<String, Object> props = gatherItemValues(item);
        props.put(AbstractModel.PK_ID, item.getId());
        return props;
    }
    
    private boolean doInsert(TModel item) {
        QueryRunner run = new QueryRunner(datasource);
        int result;
        try {
            Map<String, Object> kv = gatherItemValues(item);
            result = run.execute(makeInsertQuery(kv.keySet()), kv.values().toArray());
        } catch (IllegalArgumentException | SQLException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
            result = -1;
        }
        return result > -1;
    }
    
    private boolean doUpdate(TModel item) {
        QueryRunner run = new QueryRunner(datasource);
        int result;
        try {
            Map<String, Object> kv = gatherItemValuesWithId(item);
            result = run.execute(makeUpdateQuery(kv.keySet()), kv.values().toArray());
        } catch (IllegalArgumentException | SQLException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
            result = -1;
        }
        return result > -1;
    }

    private String makeInsertQuery(Collection<String> fields) {
        StringBuilder sb = new StringBuilder();
        StringBuilder qb = new StringBuilder();

        sb.append("INSERT INTO ");
        sb.append(this.tableName);
        sb.append(" ( ");
        fields.forEach(new Consumer<String>() {
            String delim = "";
            @Override
            public void accept(String f) {
                sb.append(delim).append(" ").append(f);
                qb.append(delim).append(" ?");
                delim = ",";
            }
        });
        sb.append(" ) VALUES ( ").append(qb.toString()).append(" ) ");
        return sb.toString();
    }

    private String makeSelectQuery(String filter) {
        StringBuilder sb = new StringBuilder();
        
        sb.append("SELECT * FROM ").append(this.tableName).append(filter);
        
        return sb.toString();
    }
    
    private String makeSelectOneQuery() {
        return makeSelectQuery(_WHERE_ID_);
    }

    private String makeUpdateQuery(Collection<String> fields) {
        StringBuilder sb = new StringBuilder();

        sb.append("UPDATE ");
        sb.append(this.tableName);
        sb.append(" SET ");
        fields.forEach(new Consumer<String>() {
            String delim = "";
            @Override
            public void accept(String f) {
                if (!AbstractModel.PK_ID.equalsIgnoreCase(f)) {
                    sb.append(delim).append(" ").append(f).append(" = ?"); 
                    delim = ",";
                }
            }
        });
        sb.append(_WHERE_ID_);
        
        return sb.toString();
    }

    private String makeDeleteQuery() {
        StringBuilder sb = new StringBuilder();
        
        sb.append("DELETE FROM ").append(this.tableName).append(_WHERE_ID_);
        
        return sb.toString();
    }
    
    /**
     *
     * @param Id
     * @return
     */
    public TModel findById(Long Id) {
        TModel result = null;
        QueryRunner run = new QueryRunner(datasource);

        ResultSetHandler<TModel> h = new BeanHandler<>(clazz);

        try {
            result = run.query(makeSelectOneQuery(), h, new Object[]{Id});
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    /**
     *
     * @param fieldName
     * @param value
     * @return
     */
    protected List<TModel> findByField(String fieldName, Object value) {
        List<TModel> result = new ArrayList<>();
        QueryRunner run = new QueryRunner(datasource);

        ResultSetHandler<List<TModel>> h = new BeanListHandler<>(clazz);

        try {
            result = run.query(makeSelectQuery(" WHERE " + fieldName + " = ?"), h, new Object[]{value});
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    /**
     *
     * @return
     */
    public List<TModel> findAll() {
        List<TModel> result = new ArrayList<>();
        QueryRunner run = new QueryRunner(datasource);

        ResultSetHandler<List<TModel>> h = new BeanListHandler<>(clazz);

        try {
            result = run.query(makeSelectQuery(""), h);
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    /**
     *
     * @param nameFilter
     * @return
     */
    public List<TModel> findByName(String nameFilter) {
        throw new UnsupportedOperationException("To be done later");
    }
    
    /**
     *
     * @param item
     */
    public void persist(TModel item) {
        if (item.getId() == null) {
            doInsert(item);
        } else {
            doUpdate(item);
        }
    }
    
    /**
     *
     * @param item
     * @return
     */
    public boolean delete(TModel item) {
        QueryRunner run = new QueryRunner(datasource);
        int result;
        try {
            result = run.execute(makeDeleteQuery(), new Object[]{item.getId()});
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
            result = -1;
        }
        return result > -1;
    }
}
