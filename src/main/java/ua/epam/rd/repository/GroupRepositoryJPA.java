package ua.epam.rd.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.epam.rd.domain.Group;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by Mykhaylo Gnylorybov on 28.04.2015.
 * email: mail@marduk.ru
 * skype: marduk.ru
 */

@Repository
public class GroupRepositoryJPA implements GroupRepository {

    @PersistenceContext(name = "myPersistentUnit")
    EntityManager em;

    @Override
    @Transactional (propagation = Propagation.REQUIRED)
    public Long add(Group group) {
        em.persist(group);
        return group.getId();
    }

    @Override
    @Transactional (propagation = Propagation.SUPPORTS, readOnly = true)
    public Group getById(Long id) {
        return em.find(Group.class, id);
    }

    @Override
    @Transactional (propagation = Propagation.SUPPORTS, readOnly = true)
    public Group getByName(String name) {
        TypedQuery<Group> query = em.createQuery("SELECT g FROM Group g WHERE g.groupName = :name", Group.class);
        query.setParameter("name", name);
        try {
            return query.getSingleResult();
        } catch (javax.persistence.NoResultException e) {
            return null;
        }
    }

    @Override
    @Deprecated
    @Transactional (propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Group> getAll() {
        TypedQuery<Group> query = em.createQuery("SELECT g FROM Group g", Group.class);
        return query.getResultList();
    }

    @Override
    public long getTotalEntryWithFilter(Boolean blocked, String regexp) {
        String queryBaseCount = "SELECT count(g) from Group g ";
        String queryWhere = "WHERE ";
        String queryAnd = "AND ";
        String queryFilterBlocked = "g.blocked = :blocked ";
        String queryFilterRegexp = "g.groupName LIKE :exp ";

        String queryString = queryBaseCount;
        boolean needBlockedParam = false;
        boolean needRegexpParan = false;

        boolean needAnd = false;
        boolean needWhere = true;
        if (blocked!=null) {
            queryString+=queryWhere+queryFilterBlocked;
            needWhere = false;
            needAnd = true;
            needBlockedParam=true;
        }
        if ((regexp != null) && (regexp.length() > 0)) {
            if (needWhere) queryString+=queryWhere;
            if (needAnd) queryString+=queryAnd;
            queryString+=queryFilterRegexp;
            needRegexpParan=true;
        }

        System.err.println(queryString);
        //create query
        Query queryTotal = em.createQuery(queryString);

        if (needBlockedParam) queryTotal.setParameter("blocked",blocked);
        if (needRegexpParan) queryTotal.setParameter("exp",regexp);

        long countResult = (long) queryTotal.getSingleResult();
        return countResult;
    }

    @Override
    public List<Group> getEntryInRangeWithFilter(int first, int size, Boolean blocked, Boolean sort, String regexp) {
        String queryBaseCount = "SELECT g from Group g ";
        String queryWhere = "WHERE ";
        String queryAnd = "AND ";
        String queryFilterBlocked = "g.blocked = :blocked ";
        String queryFilterRegexp = "g.groupName LIKE :exp ";
        String querySortIncrease = "ORDER BY g.groupName ASC ";
        String querySortDecrease = "ORDER BY g.groupName DESC ";

        String queryString = queryBaseCount;
        boolean needBlockedParam = false;
        boolean needRegexpParan = false;

        boolean needAnd = false;
        boolean needWhere = true;
        if (blocked!=null) {
            queryString+=queryWhere+queryFilterBlocked;
            needWhere = false;
            needAnd = true;
            needBlockedParam=true;
        }
        if ((regexp != null) && (regexp.length() > 0)) {
            if (needWhere) queryString+=queryWhere;
            if (needAnd) queryString+=queryAnd;
            queryString+=queryFilterRegexp;
            needRegexpParan=true;
        }
        if ((sort != null)) {
            if (sort.equals(Boolean.TRUE)) {
                queryString += querySortIncrease;
            } else if (sort.equals(Boolean.FALSE)) {
                queryString += querySortDecrease;
            }
        }
        System.err.println(queryString);
        //create query
        Query query = em.createQuery(queryString);

        query.setFirstResult(first);
        query.setMaxResults(size);

        if (needBlockedParam) query.setParameter("blocked",blocked);
        if (needRegexpParan) query.setParameter("exp",regexp);

        return query.getResultList();
    }

    @Override
    @Transactional (propagation = Propagation.REQUIRED)
    public void merge(Group group) {
        em.merge(group);
    }

    @Override
    @Transactional (propagation = Propagation.REQUIRED)
    public void delete(Long id) {
        throw new UnsupportedOperationException();

    }

    @Override
    @Transactional (propagation = Propagation.SUPPORTS, readOnly = true)
    public long getTotalEntry() {
        Query queryTotal = em.createQuery
                ("SELECT COUNT (g) from Group g");
        long countResult = (long) queryTotal.getSingleResult();
        return countResult;
    }

    @Override
    @Transactional (propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Group> getEntryInRange(int first, int size) {
        Query query = em.createQuery("FROM Group");
        query.setFirstResult(first);
        query.setMaxResults(size);
        return query.getResultList();
    }
}
