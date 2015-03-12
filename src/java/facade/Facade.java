/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.Address;
import entity.CityInfo;
import entity.Company;
import entity.Hobby;
import entity.Person;
import entity.Phone;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.eclipse.persistence.sessions.Project;

/**
 *
 * @author Alex
 */
public class Facade {

    EntityManager em;

    public Facade() {
        em = Persistence.createEntityManagerFactory("Sem3Ca2PU").createEntityManager();
    }

    

    
    

    

    

   
    

   

    }

