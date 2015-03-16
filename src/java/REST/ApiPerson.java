/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package REST;


import Controller.PersonJpaController;
import com.google.gson.Gson;
import entity.Phone;
import entity.Person;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.POST;

/**
 * REST Web Service
 *
 * @author mads
 */
@Path("person")
public class ApiPerson {

    @Context
    private UriInfo context;
    
    EntityManagerFactory emf;
    /**
     * Creates a new instance of Person
     */
    public ApiPerson() {
        emf = Persistence.createEntityManagerFactory("Sem3Ca2PU");
    }
    
    @GET
    @Produces("application/json")
    public String findAllPersons() {
       List<Object> result = new PersonJpaController(emf).getAllPersonInfo();
       return new Gson().toJson(result);
    }
    
    @GET
    @Path("phone/{phone}")
    @Produces("application/json")
    public String findPerson(@PathParam("phone") Integer phone) {
       List result = new PersonJpaController(emf).getPersonInfoByPhone(phone);
       return new Gson().toJson(result );
    }
    
    @GET
    @Path("name/{name}")
    @Produces("application/json")
    public String findPerson(@PathParam("name") String name) {
       List result = new PersonJpaController(emf).getPersonInfoByName(name);
       return new Gson().toJson(result );
    }

    @POST
    @Consumes("application/json")
    public void createPerson(@FormParam("first_name") String first_name, @FormParam("last_name") String last_name, @FormParam("email") String email) {
        Person person = new Person(first_name, last_name, email);
        new PersonJpaController(emf).create(person);
    }
    
    @PUT
    @Consumes("application/json")
    public void editPerson(@FormParam("first_name") String first_name, @FormParam("last_name") String last_name, @FormParam("email") String email) {
        Person person = new Person(first_name, last_name, email);
        new PersonJpaController(emf).create(person);
    }
}
