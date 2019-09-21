/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Renz
 */
public class CustomerTester {
        public static void main(String[] args){
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
            EntityManager em = emf.createEntityManager();
            Customer em1 = new Customer("Emil", "Thomsen");
            //sem1.addHobby("fodbold");
            Customer em2 = new Customer("Oliver","Molsing");
            Address a1 = new Address("amagerbro", "kbhS");
            //em2.addHobby("basketbold");
            //em1.addAddress(a1);
            try{
               em.getTransaction().begin();
               em.persist(em1);
               em.persist(a1);
               em.persist(em2);
               em.getTransaction().commit();
                System.out.println(em1);
                System.out.println(a1);
                System.out.println(em2);
            }finally{
                em.close();
            }
        }
}
