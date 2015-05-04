package ua.epam.rd.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.epam.rd.domain.Group;
import ua.epam.rd.domain.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Mykhaylo Gnylorybov on 23.04.2015.
 * email: mail@marduk.ru
 * skype: marduk.ru
 * <p>
 * UserRepository implementation
 * no data verification on repository level!
 */
@Repository
public class UserRepositoryJPA implements UserRepository {

    @PersistenceContext(name = "myPersistentUnit")
    EntityManager em;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Long add(User user) {
        em.persist(user);
        return user.getId();
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public User getById(Long id) {
        return em.find(User.class, id);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public User getByMail(String email) {
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.email = :mail", User.class);
        query.setParameter("mail", email);
        try {
            return query.getSingleResult();
        } catch (javax.persistence.NoResultException e) {
            return null;
        }
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<User> getAll() {
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u", User.class);
        return query.getResultList();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void merge(User user) {
        em.merge(user);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(Long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long getTotalEntry() {
        Query queryTotal = em.createQuery
                ("SELECT COUNT (u) from User u");
        long countResult = (long) queryTotal.getSingleResult();
        return countResult;
    }

    @Override
    public List<User> getEntryInRange(int first, int size) {
        Query query = em.createQuery("FROM User");
        query.setFirstResult(first);
        query.setMaxResults(size);
        return query.getResultList();
    }

    @Override
    //2DO: Unit test
    public User findUserByToken(String token) {
        TypedQuery<User> query = em.createQuery
                ("SELECT u from User u WHERE u.token.token = :token", User.class);
        query.setParameter("token", token);
        try {
            return query.getSingleResult();
        } catch (javax.persistence.NoResultException e) {
            return null;
        }
    }

    @Override
    public long getTotalEntryWithFilter(Boolean blocked, Boolean roleTeacher, String regexp) {
        String queryBaseCount = "SELECT count(u) from User u ";
        String queryWhere = "WHERE ";
        String queryAnd = "AND ";
        String queryFilterBlocked = "u.blocked = :blocked ";
        String queryFilterRoleUser = "u.membership.size = 0 ";
        String queryFilterRoleTeacher = "u.membership.size > 0 ";
        String queryFilterRegexp = "u.email LIKE :exp ";
        String querySortIncrease = "ORDER BY u.email ASC ";
        String querySortDecrease = "ORDER BY u.email DESC ";

        String queryString = queryBaseCount;

        boolean needAnd = false;
        boolean first = true;
        //creating query string
        if (blocked != null) {
            if (first == true) {
                first = false;
                queryString += queryWhere;
            }
            queryString += queryFilterBlocked;
            needAnd = true;
        }
        if (roleTeacher != null) {
            if (needAnd == true) queryString += queryAnd;
            if (roleTeacher.equals(Boolean.FALSE)) {
                if (first == true) {
                    first = false;
                    queryString += queryWhere;
                }
                queryString += queryFilterRoleUser;
            } else {
                if (first == true) {
                    first = false;
                    queryString += queryWhere;
                }
                queryString += queryFilterRoleTeacher;
            }
            needAnd = true;
        }
        if ((regexp != null) && (regexp.length() > 0)) {
            if (first == true) {
                first = false;
                queryString += queryWhere;
            }
            if (needAnd == true) queryString += queryAnd;
            queryString += queryFilterRegexp;
            needAnd = true;
        }

        System.err.println(queryString);
        //create query
        Query queryTotal = em.createQuery(queryString);

        //parameters
        if (blocked != null) {
            queryTotal.setParameter("blocked", blocked);
        }
        ;
        if ((regexp != null) && (regexp.length() > 0)) {
            queryTotal.setParameter("exp", regexp);
        }
        long countResult = (long) queryTotal.getSingleResult();
        return countResult;
    }

    @Override
    //Нифига не красиво
    public List<User> getEntryInRangeWithFilter(int first, int size, Boolean blocked, Boolean roleTeacher, Boolean sort, String regexp) {
        String queryBase = "SELECT u from User u ";
        String queryWhere = "WHERE ";
        String queryAnd = "AND ";
        String queryFilterBlocked = "u.blocked = :blocked ";
        String queryFilterRoleUser = "u.membership.size = 0 ";
        String queryFilterRoleTeacher = "u.membership.size > 0 ";
        String queryFilterRegexp = "u.email LIKE :exp ";
        String querySortIncrease = "ORDER BY u.email ASC ";
        String querySortDecrease = "ORDER BY u.email DESC ";

        String queryString = queryBase;

        boolean needAnd = false;
        boolean firstW = true;

        //creating query string
        if (blocked != null) {
            if (firstW == true) {
                firstW = false;
                queryString += queryWhere;
            }
            queryString += queryFilterBlocked;
            needAnd = true;
        }
        if (roleTeacher != null) {
            if (needAnd == true) queryString += queryAnd;
            if (roleTeacher.equals(Boolean.FALSE)) {
                if (firstW == true) {
                    firstW = false;
                    queryString += queryWhere;
                }
                queryString += queryFilterRoleUser;
            } else {
                if (firstW == true) {
                    firstW = false;
                    queryString += queryWhere;
                }
                queryString += queryFilterRoleTeacher;
            }
            needAnd = true;
        }
        if ((regexp != null) && (regexp.length() > 0)) {
            if (firstW == true) {
                firstW = false;
                queryString += queryWhere;
            }
            if (needAnd == true) queryString += queryAnd;
            queryString += queryFilterRegexp;
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

        //parameters
        if (blocked != null) {
            query.setParameter("blocked", blocked);
        }
        ;
        if ((regexp != null) && (regexp.length() > 0)) {
            query.setParameter("exp", regexp);
        }

        query.setFirstResult(first);
        query.setMaxResults(size);

        return query.getResultList();
    }

    @Override
    //2DO: JPQL
    @Transactional (propagation = Propagation.REQUIRED, readOnly = true)
    public long getActiveGroupMembershipCount(Long idUser) {
//        System.out.println("-----------------------------------------------------");
//        Query queryTotal = em.createQuery
//                ("SELECT COUNT (g) from Group g WHERE g MEMBER OF User u.membership AND u.id = :idUser AND g.blocked = :blockedParam");
//        queryTotal.setParameter("blockedParam", Boolean.FALSE);
//        queryTotal.setParameter("idUser", idUser);
//
//        long countResult = (long) queryTotal.getSingleResult();
//        System.out.println("getActiveGroupMembershipCount FOR ID=" + idUser + " : " + countResult);
        long countResult = 0;
        List <Group> membership =  getById(idUser).getMembership();
        Iterator <Group> it = membership.iterator();
        while (it.hasNext()) {
            Group g = it.next();
            if (g.getBlocked().equals(Boolean.FALSE)) {
                countResult++;
            }
        }
        return countResult;
    }

    @Override
    //2DO: JPQL
    @Transactional (propagation = Propagation.REQUIRED, readOnly = true)
    public List<Group> getActiveGroupMembership(Long idUser) {
//        Query query = em.createQuery
//                ("SELECT u.membership from User u where u.membership.blocked = :blockedParam");
//        query.setParameter("blockedParam", Boolean.FALSE);
//        return query.getResultList();
        List <Group> membership =  getById(idUser).getMembership();
        List <Group> activeMembership = new ArrayList<Group>(membership.size());
        Iterator <Group> it = membership.iterator();
        while (it.hasNext()) {
            Group g = it.next();
            if (g.getBlocked().equals(Boolean.FALSE)) {
                activeMembership.add(g);
            }
        }
        return activeMembership;
    }
}
