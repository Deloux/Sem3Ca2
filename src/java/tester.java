
import Controller.AddressJpaController;
import Controller.CityInfoJpaController;
import Controller.CompanyJpaController;
import Controller.HobbyJpaController;
import Controller.PersonJpaController;
import Controller.PhoneJpaController;
import entity.Address;
import entity.Company;
import entity.Hobby;
import entity.Person;
import entity.Phone;
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

        AddressJpaController ajc = new AddressJpaController(emf);
        CityInfoJpaController cijc = new CityInfoJpaController(emf);
        CompanyJpaController cjc = new CompanyJpaController(emf);
        HobbyJpaController hjc = new HobbyJpaController(emf);
        PersonJpaController pjc = new PersonJpaController(emf);
        PhoneJpaController phjc = new PhoneJpaController(emf);

        Person p = new Person("Per", "Thorsen", "per@mail.dk");
        Phone ph = new Phone(12345678, "blabla");
        Hobby h = new Hobby("Football", "blabla");
        List<Hobby> hobbies = new ArrayList();
        Address a = new Address("Enghavevej 1", "bla?");
        Address a2 = new Address("Silvanvej 4", "Silvan Silvan");
        
        pjc.create(p);
        phjc.createAndAssingToPerson(ph, p);
        ajc.create(a, 2650);
        hjc.create(h);
        pjc.assignPersonToHobby(p, h);
        ajc.assignPersonToAddress(a, p);
        ajc.create(a2, 2650);
        

        
//        f.createAddress(a, 2650);
        Phone ph2 = new Phone(11223344, "Silvans Fon");
        Company c = new Company("Silvan", "BlablaSilvanBla", 12345, 6666, 200, "silvan@silvanMail.dk");
         cjc.create(c);
        phjc.createAndAssingToCompany(ph2, c);
        
        ajc.assignCompanyToAddress(a2, c);
        
        
        
//        f.deletePerson(p);
//        f.deleteCompany(c);
//        String res = "";
//        for (int i = 0; i < pjc.getPersonInfo(ph).size(); i++) {
//            res += pjc.getPersonInfo(ph).get(i);
//        }
//        String json = new Gson().toJson(pjc.getPersonInfo(ph));
//        System.out.println(pjc.getPersonInfo(ph).get(0));
        System.out.println(cjc.getCompanyInfo(11223344));
//        System.out.println("name :" + p.getFirstName() + " lastname: " + p.getLastName() + " email: " + p.getEmail());
    }

}
