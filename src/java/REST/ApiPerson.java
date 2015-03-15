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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;

/**
 * REST Web Service
 *
 * @author mads
 */
@Path("person")
public class ApiPerson {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of Person
     */
    public ApiPerson() {
    }

    /**
     * Retrieves representation of an instance of REST.Person
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public String getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }
    
    @GET
    @Path("phone/{phone}")
    @Produces("application/json")
    public String find(@PathParam("phone") Integer phone) {
       List result = new PersonJpaController().getPersonInfo(new Phone(phone, ""));
       return new Gson().toJson(result );
    }

    /**
     * PUT method for updating or creating an instance of Person
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Path("{first_name}/{last_name}/{email}")
    @Consumes("application/json")
    public void putJson(@PathParam("first_name") String first_name, @PathParam("last_name") String last_name, @PathParam("email") String email) {
        Person person = new Person(first_name, last_name, email);
        new PersonJpaController().create(person);
    }
}
