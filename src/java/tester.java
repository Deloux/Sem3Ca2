
import entity.Company;
import entity.Hobby;
import entity.Person;
import entity.Phone;
import facade.Facade;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Alex
 */
public class tester {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Sem3Ca2PU");
        EntityManager em = emf.createEntityManager();

        Facade f = new Facade();

        Person p = new Person("Per", "Thorsen", "per@mail.dk");
        Phone ph = new Phone(12345678, "blabla");
        Hobby h = new Hobby("Football", "blabla");
        List<Hobby> hobbies = new ArrayList();
        hobbies.add(h);
        f.createPerson(p, ph, hobbies);

//        Address a = new Address("Enghavevej 1", "bla?");
//        f.createAddress(a, 2650);
        Phone ph2 = new Phone(11223344, "Silvans Fon");
        Company c = new Company("Silvan", "BlablaSilvanBla", 12345, 6666, 200, "silvan@silvanMail.dk");
        f.createCompany(c, ph2);

        
        p = f.getPerson(1);
        
//        f.deletePerson(p);
//        f.deleteCompany(c);
        
        System.out.println("name :" + p.getFirstName() + " lastname: " + p.getLastName() + " email: " + p.getEmail());
    }

}
