/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entities.Person;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

/**
 *
 * @author Renz
 */
public class PersonFacade implements IPersonFacade {

    private static PersonFacade instance;
    private static EntityManagerFactory emf;

    public PersonFacade() {

    }

    public static PersonFacade getPersonFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new PersonFacade();
        }
        return instance;
    }

    @Override
    public Person addPerson(String fName, String lName, String phone) {
        EntityManager em = emf.createEntityManager();
        Person p = new Person(fName, lName, phone);
        p.setDate(new Date());
        try {
            em.getTransaction().begin();
            em.persist(p);
            em.getTransaction().commit();

        } finally {
            em.close();
        }
        return p;
    }

    @Override
    public Person deletePerson(int id) {
        EntityManager em = emf.createEntityManager();
        Person p = em.find(Person.class, id);
        try {
            em.getTransaction().begin();
            em.remove(p);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return null;
    }

    @Override
    public Person getPersonById(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            Person p = em.find(Person.class, id);
            return p;
        } finally {
            em.close();
        }
    }

    @Override
    public List<Person> getAllPerson() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Person> query = em.createQuery("SELECT p from Person p", Person.class);
            return query.getResultList();
        } finally {
            em.close();
        }

    }

    @Override
    public Person editPerson(Person p) {
        EntityManager em = emf.createEntityManager();
        try {
            p = getPersonById(p.getId());
            em.getTransaction().begin();
            em.refresh(p);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return p;
    }

}
