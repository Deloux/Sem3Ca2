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

    public List getAllZipCodes() {
        Query q = em.createQuery("SELECT z.zipCode FROM CityInfo z");
        List zipCodes = q.getResultList();
        return zipCodes;
    }

    public List<Company> getCompHigherThanxxEmp(int empAmount) {
        Query q = em.createQuery("SELECT c FROM Company c WHERE c.numEmployees > :empAmount").setParameter(":empAmount", empAmount);
        List<Company> companies = q.getResultList();
        return companies;
    }

    public void deletePerson(Person p) {
        em.getTransaction().begin();
        em.remove(p);
        em.getTransaction().commit();
    }

    public void deleteCompany(Company c) {
        em.getTransaction().begin();
        em.remove(c);
        em.getTransaction();
    }

    public void assignInfoEntityToAddress(int id, Address a) {
        Company c = em.getReference(Company.class, id);
        a.addInfoEntity(c);
        em.getTransaction().begin();
        em.merge(a);
        em.getTransaction().commit();
    }

    public void createAddress(Address a, int zipCode) {
        CityInfo ci = em.getReference(CityInfo.class, zipCode);
        em.getTransaction().begin();
        em.persist(a);
        em.getTransaction().commit();
        ci.addAdress(a);
        em.getTransaction().begin();
        em.merge(ci);
        em.getTransaction().commit();
    }

    public void createCompany(Company c, Phone ph) {
        c.addPhone(ph);
        em.getTransaction().begin();
        em.persist(c);
        em.getTransaction().commit();

    }

    public void createPerson(Person p, Phone ph, List<Hobby> hobbies) {
        for (int i = 0; i < hobbies.size(); i++) {
            p.addHobby(hobbies.get(i));
        }
        p.addPhone(ph);
        em.getTransaction().begin();
        em.persist(p);
        em.getTransaction().commit();

    }

    public Person getPerson(int id) {
        Query q = em.createQuery("SELECT p FROM Person p WHERE p.id = " + id);
        List<Person> persons = q.getResultList();

        return persons.get(0);
    }

    }

