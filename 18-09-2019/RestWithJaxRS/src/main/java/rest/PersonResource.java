/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.PersonDTO;
import entities.Person;
import exceptions.PersonNotFoundException;
import facades.PersonFacade;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import utils.EMF_Creator;

/**
 *
 * @author Renz
 */
@Path("person")
public class PersonResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(
            "pu",
            "jdbc:mysql://127.0.01:3307/jpa_rest",
            "dev",
            "ax2",
            EMF_Creator.Strategy.CREATE);
    private static final PersonFacade FACADE = PersonFacade.getPersonFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static Person p1, p2, p3;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"succes\"}";
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/create")
    public Response create() {
        EntityManager em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();
            p1 = new Person("Renz", "Oliver", "12345678");
            p1.setDate(new Date());
            em.persist(p1);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return Response.ok().entity(GSON.toJson("Setup Complete")).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addPerson(String personJson) {
        Person p = GSON.fromJson(personJson, Person.class);
        Person person = FACADE.addPerson(p.getFirstName(), p.getLastName(), p.getPhone());
        return Response.ok().entity(GSON.toJson(new PersonDTO(person))).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/all")
    public Response getAllPerson() {
        List<Person> p = FACADE.getAllPerson();
        return Response.ok().entity(GSON.toJson(p)).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("id/{id}")
    public Response getPersonById(@PathParam("id") int id) {
        Person p = FACADE.getPersonById(id);
        return Response.ok().entity(GSON.toJson(p)).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}/{firstName}/{lastName}/{phone}")
    public Response editPerson(@PathParam("id") int id, @PathParam("firstName") String firstName, @PathParam("lastName") String lastName, @PathParam("phone") String phone) {
        Person p = new Person();
        p.setFirstName(firstName);
        p.setLastName(lastName);
        p.setPhone(phone);
        return Response.ok().entity(GSON.toJson(p)).build();
    }

    @DELETE
    @Path("DeletePersonById/{id}")
    public String deletePerson(@PathParam("id") int id) {
        FACADE.deletePerson(id);
        return "{\"status\": \"removed\"}";
    }
}
