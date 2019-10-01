package com.haulmont.testtask.dataAccess;

import com.haulmont.testtask.model.Priority;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 *
 * @author korolevia
 */
public class PriorityRepo extends AbstractRepo<Priority>{   
    static List<Priority> tmp = new ArrayList<>();

    enum PriorityInner {
        Normal, Cito, Statim;
    }

    /**
     * Enum in repo (!sic)
     */
    public PriorityRepo() {
        super(null, Priority.class);
    }
    
    /**
     *
     * @return
     */
    public static List<Priority> getPriorities() {
        
        if (tmp.isEmpty()) {
            for (PriorityInner priority : EnumSet.allOf(PriorityInner.class)) {
                Priority e = new Priority(priority.ordinal(), priority.name());
                tmp.add(e);
            }
        }
        return tmp;
    }

    @Override
    public boolean delete(Priority item) {
        throw new UnsupportedOperationException("Impossible for this model");
    }

    @Override
    public void persist(Priority item) {
        throw new UnsupportedOperationException("Impossible for this model");
    }

    @Override
    public List<Priority> findByName(String nameFilter) {
        return getPriorities().stream().filter(e -> e.getName().equalsIgnoreCase(nameFilter)).collect(Collectors.toList());
    }

    @Override
    public List<Priority> findAll() {
        return getPriorities();
    }

    @Override
    public Priority findById(Long Id) {
        return getPriorities().stream().filter(e -> Objects.equals(e.getId(), Id)).findFirst().get();
    }
}
