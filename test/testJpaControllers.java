/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import Controller.AddressJpaController;
import Controller.CityInfoJpaController;
import Controller.CompanyJpaController;

import Controller.HobbyJpaController;
import Controller.InfoEntityJpaController;
import Controller.PersonJpaController;
import Controller.PhoneJpaController;
import Controller.exceptions.NonexistentEntityException;
import entity.Address;
import entity.Company;
import entity.Hobby;
import entity.InfoEntity;
import entity.Person;
import entity.Phone;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Alex
 */
public class testJpaControllers {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("Sem3Ca2PU");
    AddressJpaController ajc = new AddressJpaController(emf);
    CityInfoJpaController cijc = new CityInfoJpaController(emf);
    CompanyJpaController cjc = new CompanyJpaController(emf);
    HobbyJpaController hjc = new HobbyJpaController(emf);
    PersonJpaController pjc = new PersonJpaController(emf);
    PhoneJpaController phjc = new PhoneJpaController(emf);
    InfoEntityJpaController iejc = new InfoEntityJpaController(emf);

    public testJpaControllers() {
    }

    @BeforeClass
    public static void setUpClass() {

    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testCreateRemovePerson() throws NonexistentEntityException {
        Person p = new Person("Per", "Thorsen", "per@mail.dk");

        pjc.create(p);

        List<Person> persons = pjc.findPersonEntities();
        assertTrue(persons.size() == 1);
        Person p2 = pjc.findPerson(persons.get(0).getId());
        assertEquals(p.getFirstName(), p2.getFirstName());
        assertEquals(p.getLastName(), p2.getLastName());
        assertEquals(p.getEmail(), p2.getEmail());
        pjc.destroy(persons.get(0).getId());
        assertTrue(pjc.findPersonEntities().isEmpty());
    }

    @Test
    public void testCreateRemoveCompany() throws NonexistentEntityException {
        Company c = new Company("Silvan", "BlablaSilvanBla", 12345, 6666, 200, "silvan@silvanMail.dk");

        cjc.create(c);
        List<Company> companies = cjc.findCompanyEntities();

        assertTrue(companies.size() == 1);
        Company c2 = cjc.findCompany(companies.get(0).getId());
        assertEquals(c.getCvr(), c2.getCvr());
        assertEquals(c.getDescription(), c2.getDescription());
        assertEquals(c.getEmail(), c2.getEmail());
        assertEquals(c.getMarketValue(), c2.getMarketValue());
        assertEquals(c.getName(), c2.getName());
        assertEquals(c.getNumEmployees(), c2.getNumEmployees());
        cjc.destroy(companies.get(0).getId());
        assertTrue(pjc.findPersonEntities().isEmpty());
    }

    @Test
    public void testCreateRemoveHobby() throws NonexistentEntityException {
        Hobby h = new Hobby("Football", "Bla");

        hjc.create(h);
        List<Hobby> hobbies = hjc.findHobbyEntities();

        assertTrue(hobbies.size() == 1);
        Hobby h2 = hjc.findHobby(hobbies.get(0).getId());
        assertEquals(h.getName(), h2.getName());
        assertEquals(h.getDescription(), h2.getDescription());
        hjc.destroy(hobbies.get(0).getId());
        assertTrue(hjc.findHobbyEntities().isEmpty());
    }

    @Test
    public void testCreateRemoveAddress() throws NonexistentEntityException {
        Address a = new Address("Arnold Nielsens blvd.", "145 st. tv.");

        ajc.create(a, 2650);
        List<Address> address = ajc.findAddressEntities();

        assertTrue(address.size() == 1);
        Address a2 = ajc.findAddress(address.get(0).getId());
        assertEquals(a.getStreet(), a2.getStreet());
        assertEquals(a.getAdditionalInfo(), a2.getAdditionalInfo());
        ajc.destroy(address.get(0).getId());
        assertTrue(ajc.findAddressEntities().isEmpty());
    }

    @Test
    public void testCreateRemoveAssignPhoneCascade() throws NonexistentEntityException {
        Phone ph = new Phone(12345678, "blabla");
        Phone ph2 = new Phone(87654321, "Silvans fon");
        Phone ph3 = new Phone(87654331, "Silvans fon2");
        Person p = new Person("Per", "Thorsen", "per@mail.dk");
        Company c = new Company("Silvan", "BlablaSilvanBla", 12345, 6666, 200, "silvan@silvanMail.dk");

        List<Company> companies = cjc.findCompanyEntities();
        List<Person> persons = pjc.findPersonEntities();
        List<InfoEntity> infoEntities = iejc.findInfoEntityEntities();
        List<Phone> phones = phjc.findPhoneEntities();

        assertTrue(companies.isEmpty());
        assertTrue(persons.isEmpty());
        assertTrue(infoEntities.isEmpty());
        assertTrue(phones.isEmpty());

        cjc.create(c);
        pjc.create(p);
        phjc.createAndAssingToPerson(ph, p);
        phjc.createAndAssingToCompany(ph2, c);
        phjc.createAndAssingToCompany(ph3, c);

        companies = cjc.findCompanyEntities();
        persons = pjc.findPersonEntities();
        infoEntities = iejc.findInfoEntityEntities();
        phones = phjc.findPhoneEntities();

        assertTrue(companies.size() == 1);
        assertTrue(persons.size() == 1);
        assertTrue(infoEntities.size() == 2);
        assertTrue(phones.size() == 3);

        pjc.destroy(persons.get(0).getId());

        companies = cjc.findCompanyEntities();
        persons = pjc.findPersonEntities();
        infoEntities = iejc.findInfoEntityEntities();
        phones = phjc.findPhoneEntities();

        assertTrue(companies.size() == 1);
        assertTrue(persons.isEmpty());
        assertTrue(infoEntities.size() == 1);
        assertTrue(phones.size() == 2);

        cjc.destroy(companies.get(0).getId());

        companies = cjc.findCompanyEntities();
        persons = pjc.findPersonEntities();
        infoEntities = iejc.findInfoEntityEntities();
        phones = phjc.findPhoneEntities();

        assertTrue(companies.isEmpty());
        assertTrue(persons.isEmpty());
        assertTrue(infoEntities.isEmpty());
        assertTrue(phones.isEmpty());

    }

    @Test
    public void testEditCompany() throws Exception {
        Company c = new Company("Silvan", "BlablaSilvanBla", 12345, 6666, 200, "silvan@silvanMail.dk");

        cjc.create(c);

        List<Company> companies = cjc.findCompanyEntities();
        assertTrue(companies.size() == 1);
        int id = companies.get(0).getId();
        Company c2 = cjc.findCompany(id);
        assertEquals(c.getCvr(), c2.getCvr());
        assertEquals(c.getDescription(), c2.getDescription());
        assertEquals(c.getEmail(), c2.getEmail());
        assertEquals(c.getMarketValue(), c2.getMarketValue());
        assertEquals(c.getName(), c2.getName());
        assertEquals(c.getNumEmployees(), c2.getNumEmployees());

        c2.setName("Netto");
        c2.setNumEmployees(1);
        cjc.edit(c2);
        Company c3 = cjc.findCompany(id);
        assertTrue("Netto".equals(c3.getName()));
        assertTrue(1 == c3.getNumEmployees());
        cjc.destroy(id);

    }

    @Test
    public void testEditPerson() throws Exception {
        Person p = new Person("Per", "Thorsen", "per@mail.dk");

        pjc.create(p);

        List<Person> persons = pjc.findPersonEntities();
        assertTrue(persons.size() == 1);
        int id = persons.get(0).getId();
        Person p2 = pjc.findPerson(id);
        assertEquals(p.getFirstName(), p2.getFirstName());
        assertEquals(p.getLastName(), p2.getLastName());
        assertEquals(p.getEmail(), p2.getEmail());

        p2.setFirstName("Hans");
        pjc.edit(p2);
        Person p3 = pjc.findPerson(id);
        assertTrue("Hans".equals(p3.getFirstName()));
        pjc.destroy(id);

    }

    @Test
    public void testEditAddress() throws Exception {
        Address a = new Address("Arnold Nielsens blvd.", "145 st. tv.");

        ajc.create(a, 2650);

        List<Address> address = ajc.findAddressEntities();
        assertTrue(address.size() == 1);
        int id = address.get(0).getId();
        Address a2 = ajc.findAddress(id);
        assertEquals(a.getStreet(), a2.getStreet());
        assertEquals(a.getAdditionalInfo(), a2.getAdditionalInfo());

        a2.setStreet("Enghavevej 1");
        ajc.edit(a2);
        Address a3 = ajc.findAddress(id);
        assertTrue("Enghavevej 1".equals(a3.getStreet()));
        ajc.destroy(id);
    }
    @Test
    public void testEditHobby() throws Exception{
        Hobby h = new Hobby("Football", "Bla");
    
        hjc.create(h);

        List<Hobby> hobbies = hjc.findHobbyEntities();
        assertTrue(hobbies.size() == 1);
        int id = hobbies.get(0).getId();
        Hobby h2 = hjc.findHobby(id);
       assertEquals(h.getName(), h2.getName()); 
       assertEquals(h.getDescription(), h2.getDescription());

        h2.setName("Footbawl");
        hjc.edit(h2);
        Hobby h3 = hjc.findHobby(id);
        assertTrue("Footbawl".equals(h3.getName()));
        hjc.destroy(id);
    }
    @Test
    public void testEditPhone() throws Exception{
        Phone ph = new Phone(12345678, "blabla");
        Person p = new Person("Per", "Thorsen", "per@mail.dk");
        
        pjc.create(p);
        phjc.createAndAssingToPerson(ph, p);
        
        List<Phone> phones = phjc.findPhoneEntities();
        assertTrue(phones.size() == 1);
        int id = phones.get(0).getId();
        Phone ph2 = phjc.findPhone(id);
       assertEquals(ph.getNumber(), ph2.getNumber()); 
       assertEquals(ph.getDescription(), ph2.getDescription());

        ph2.setNumber(78451265);
        phjc.edit(ph2);
        Phone ph3 = phjc.findPhone(id);
        assertTrue(78451265 == ph3.getNumber());
        pjc.destroy(pjc.findPersonEntities().get(0).getId());
        
    }

    @Test
    public void testAssingAdress() throws NonexistentEntityException {
        Person p = new Person("Alexander", "Lund", "per@mail.dk");
        Person p2 = new Person("Kristoffer", "Lund", "jaja@mail.dk");
        Address a = new Address("Arnold Nielsens blvd.", "145 st. tv.");

        ajc.create(a, 2650);
        pjc.create(p);

        List<Address> address = ajc.findAddressEntities();
        List<Person> persons = pjc.findPersonEntities();

        assertTrue(address.size() == 1);
        assertTrue(persons.size() == 1);

        assertNull(persons.get(0).getAddress());
        assertTrue(address.get(0).getInfoEntitys().isEmpty());

        ajc.assignPersonToAddress(a, p);

        address = ajc.findAddressEntities();
        persons = pjc.findPersonEntities();

        assertNotNull(persons.get(0).getAddress());
        assertTrue(address.get(0).getInfoEntitys().size() == 1);
        assertTrue(address.get(0).getInfoEntitys().get(0).getEmail().equals(p.getEmail()));

        ajc.assignPersonToAddress(a, p2);
        persons = pjc.findPersonEntities();

        address = ajc.findAddressEntities();

        assertTrue(address.get(0).getInfoEntitys().size() == 2);
        assertTrue(persons.size() == 2);

        ajc.destroy(address.get(0).getId());

        address = ajc.findAddressEntities();
        persons = pjc.findPersonEntities();

        assertTrue(address.isEmpty());
        assertTrue(persons.isEmpty());
    }

    @Test
    public void testGetPersonInfo() throws NonexistentEntityException {
        Person p = new Person("Per", "Thorsen", "per@mail.dk");
        Phone ph = new Phone(12345678, "blabla");
        Hobby h = new Hobby("Football", "blabla");
        List<Hobby> hobbies = new ArrayList();
        Address a = new Address("Enghavevej 1", "bla?");

        pjc.create(p);
        phjc.createAndAssingToPerson(ph, p);
        ajc.create(a, 2650);
        hjc.create(h);
        pjc.assignPersonToHobby(p, h);
        ajc.assignPersonToAddress(a, p);

        List objList = pjc.getPersonInfoByPhone(12345678);
        String DataO = objList.get(0).toString();
        String[] pData = DataO.split(",");

        assertTrue(pData[0].contains("fName=Per"));
        assertTrue(pData[1].contains("lName=Thorsen"));
        assertTrue(pData[2].contains("phone=12345678"));
        assertTrue(pData[3].contains("email=per@mail.dk"));
        assertTrue(pData[4].contains("city=Hvidovre"));
        assertTrue(pData[5].contains("street=Enghavevej 1"));
        assertTrue(pData[6].contains("zip=2650"));

        //pjc.destroy(pjc.findPersonEntities().get(0).getId());
        ajc.destroy(ajc.findAddressEntities().get(0).getId());

        assertTrue(cjc.findCompanyEntities().isEmpty());
        assertTrue(pjc.findPersonEntities().isEmpty());
        assertTrue(iejc.findInfoEntityEntities().isEmpty());
        assertTrue(phjc.findPhoneEntities().isEmpty());
        assertTrue(ajc.findAddressEntities().isEmpty());
        assertTrue(hjc.findHobbyEntities().isEmpty());

        System.out.println(pData[0]);
        System.out.println(objList.get(0).toString());

    }
}
