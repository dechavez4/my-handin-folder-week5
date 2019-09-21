package facades;

import entity.Semester;
import entity.Student;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class StudentsFacade {

    private static StudentsFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private StudentsFacade() {
    }

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static StudentsFacade getStudentFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new StudentsFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    //TODO Remove/Change this before use
    public long getStudentCount() {
        EntityManager em = emf.createEntityManager();
        try {
            long StudentCount = (long) em.createQuery("SELECT COUNT(r) FROM Student r").getSingleResult();
            return StudentCount;
        } finally {
            em.close();
        }

    }

    public List<Student> getAllStudent() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Student> query
                    = em.createQuery("SELECT s from Student s", Student.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Student> getStudentByName(String name) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createNamedQuery("Student.findByFirstname").setParameter("firstname", name).getResultList();
        } finally {
            em.close();
        }
    }

    public Student insertStudent(String firstname, String lastname) {
        EntityManager em = emf.createEntityManager();
        Student s = new Student(firstname, lastname);
        try {
            em.getTransaction().begin();
            em.persist(s);
            em.getTransaction().commit();
            return s;
        } finally {
            em.close();
        }
    }

    public Student assignSemester(long stuId, long semId) {
        EntityManager em = emf.createEntityManager();
        try {
            Student stu = em.find(Student.class, stuId);
            Semester sem = em.find(Semester.class, semId);
            stu.setSemester(sem);
            em.getTransaction().begin();
            em.merge(stu);
            em.getTransaction().commit();
            return stu;
        } finally {
            em.close();
        }
    }

    public long getTotalAmount() {
        EntityManager em = emf.createEntityManager();
        try {
            return (long) em.createQuery("SELECT COUNT(s) FROM Student s").getSingleResult();

        } finally {
            em.close();
        }
    }

    public List<Student> getLastNameByAnd(String name) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createNamedQuery("Student.findByLastname").setParameter("lastname", name).getResultList();
        } finally {
            em.close();
        }
    }

    public long getNumSemStudent(String sem) {
        EntityManager em = emf.createEntityManager();
        try {
            Semester m = (Semester) em.createNamedQuery("Semester.findByName").setParameter("name", sem).getSingleResult();
            return(long) em.createQuery("SELECT COUNT(s) FROM Student s Where s.semester = :semester").setParameter("semester", m).getSingleResult();
        }finally{
            em.close();
        }
    }

}
