package rest.api.cardinity.taskmanager.repository;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import rest.api.cardinity.taskmanager.models.entity.base.BaseEntity;
import rest.api.cardinity.taskmanager.models.entity.base.BaseUpdatableEntity;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author dipanjal
 * @since 2/6/2021
 */
public class BaseRepository<T> {
    @Autowired
    private SessionFactory sessionFactory;
    private Class<T> entityClass;

    private final int BATCH_SIZE = 50;
    private final int OPERATION_CREATE = 1;
    private final int OPERATION_UPDATE= 2;

    protected Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    protected Criteria getCriteria() {
        return getSession().createCriteria(getModelClass())
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
    }

    private Class<T> getModelClass() {
        if (entityClass == null) {
            ParameterizedType thisType = (ParameterizedType) getClass().getGenericSuperclass();
            this.entityClass = (Class<T>) thisType.getActualTypeArguments()[0];
        }
        return this.entityClass;
    }

    private String getDomainClassName() {
        return getModelClass().getName();
    }


    public void create(T t) {
        getSession().save(t);
    }

    public void create(Collection<T> items) {
        this.doBatch(items, OPERATION_CREATE);
    }

    public T get(Serializable id) {
        return (T) getSession().get(getModelClass(), id);
    }

    public T load(Serializable id) {
        return getSession().load(getModelClass(), id);
    }

    public List<T> getAll() {
        return getSession()
                .createQuery("from " + getDomainClassName())
                .list();
    }

    public void update(T t) {
        if(t instanceof BaseUpdatableEntity){
            ((BaseUpdatableEntity) t).setUpdatedAt(new Date());
        }
        getSession().update(t);
    }

    public void update(List<T> items) {
        this.doBatch(items, OPERATION_UPDATE);
    }

    public void delete(T t) {
        getSession().delete(t);
    }

    public void deleteById(Serializable id) {
        delete(load(id));
    }

    public void deleteAll() {
        getSession()
                .createQuery("delete from " + getDomainClassName())
                .executeUpdate();
    }

    private void doBatch(Collection<T> items, int operationType){
        Session session = getSession();
        AtomicInteger count = new AtomicInteger(0);
        items.forEach( item -> {
            if (OPERATION_CREATE == operationType) {
                session.save(item);
            } else {
                session.update(item);
            }

            if (count.getAndIncrement() % BATCH_SIZE == 0) {
                flushSession();
                clearSession();
            }
        });
    }

    private void flushSession() {
        getSession().flush();
    }

    private void clearSession() {
        getSession().clear();
    }
}
