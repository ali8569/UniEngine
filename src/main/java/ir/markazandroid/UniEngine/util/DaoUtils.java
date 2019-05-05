package ir.markazandroid.UniEngine.util;

import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;

import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ali on 30/01/2018.
 */
public class DaoUtils {

    public static ProjectionList getProjectionByFieldNames(String[] fields){
        ProjectionList columnProjectionsList = Projections.projectionList();
        for (String column : fields) {
            columnProjectionsList.add(Projections.property(column),column);
        }
        return columnProjectionsList;
    }

    public static List<Selection<?>> getSelectionsByFieldNames(Root<?> root,String... fields){
        List<Selection<?>> list = new ArrayList<>();
        for (String column : fields) {
            list.add(root.get(column));
        }
        return list;
    }

    public static <T> void setUpdateFields(T entity, CriteriaUpdate criteriaUpdate, String[] fields){
        for (String field : fields) {
            try {
                Field f = entity.getClass().getDeclaredField(field);
                f.setAccessible(true);
                criteriaUpdate.set(field, f.get(entity));
            } catch (IllegalAccessException | NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
    }
}
