/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tester;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import facades.StudentsFacade;
import javax.persistence.EntityManagerFactory;
import utils.EMF_Creator;

/**
 *
 * @author Renz
 */
public class StudentTester {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(
            "pu",
            "jdbc:mysql://localhost:3307/ORM_with_jpa",
            "dev",
            "ax2",
            EMF_Creator.Strategy.CREATE);
    private static final StudentsFacade FACADE = StudentsFacade.getStudentFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static void main(String[] args) {
        //bruger dette som en test for at tjekke om jeg ren faktisk laver mine metoder korrekt
        
        
        System.out.println("show firstname");
        System.out.println(FACADE.getStudentByName("Anders"));
        System.out.println("------------------------------------------");
        System.out.println("find all students");
        System.out.println(FACADE.getAllStudent());
        System.out.println("----------------------------------------");
        System.out.println("assign semester");
        System.out.println(FACADE.assignSemester(7, 1));
        System.out.println("-----------------------------------------");
        System.out.println("show total amount of students");
        System.out.println(FACADE.getTotalAmount());
        System.out.println("------------------------------------------");
        System.out.println("Show student by last name");
        System.out.println(FACADE.getLastNameByAnd("And"));
        System.out.println("------------------------------------------");
        System.out.println(FACADE.getNumSemStudent("CLcos-v14e"));
    }
}
