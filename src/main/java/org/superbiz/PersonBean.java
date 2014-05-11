package org.superbiz;

import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;

@Singleton
@Lock(LockType.READ)
public class PersonBean {
    @PersistenceContext
    private EntityManager em;

    public Collection<Person> people() {
        return em.createNamedQuery("Person.findAll", Person.class).getResultList();
    }

    public Person findById(final long id) {
        return em.find(Person.class, id);
    }

    public long newPerson(final long id, final String dummy) {
        final Person p = new Person();
        p.setName(dummy);
        p.setId(id);
        em.persist(p);
        em.flush();
        return p.getId();
    }
}
