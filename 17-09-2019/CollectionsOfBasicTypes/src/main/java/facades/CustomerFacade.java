/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entities.Customer;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

/**
 *
 * @author Renz
 */
public class CustomerFacade {
    private static CustomerFacade instance;
    private static EntityManagerFactory emf;
    
    private CustomerFacade(){
        
    }
    
    public Customer getCustomerById(Long id){
        EntityManager em = emf.createEntityManager();
        try{
            Customer c = em.find(Customer.class, id);
            return c;
        }finally{
            em.close();
        }
    }
    
    public List<Customer> getAllCustomer(){
        EntityManager em = emf.createEntityManager();
        try{
            TypedQuery<Customer> query = 
                    em.createQuery("SELECT customer FROM Customer customer", Customer.class);
            return query.getResultList();
        }finally{
            em.close();
        }
    }
    
    public Customer addCustomer(Customer cust){
        EntityManager em = emf.createEntityManager();
        try{
            em.getTransaction().begin();
            em.persist(cust);
            em.getTransaction().commit();
        }finally{
            em.close();
        }
        return cust;
    }
    
    public Customer deleteCustomer(Long id){
        EntityManager em = emf.createEntityManager();
        Customer c = getCustomerById(id);
        try{
            
        em.getTransaction().begin();
        em.remove(c);
        em.getTransaction().commit();
    }finally{
            em.close();
        }
        return c;
    }
    
    public Customer editCustomer(Customer cust){
        EntityManager em = emf.createEntityManager();
        try{
            em.getTransaction().begin();
            em.merge(cust);
            em.getTransaction().commit();
        }finally{
            em.close();
        }
        return cust;
    }
    
    
}
