/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.exceptions.NonexistentEntityException;
import entity.Address;
import entity.Hobby;
import entity.Person;
import entity.Phone;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import static oracle.security.o3logon.b.a;

/**
 *
 * @author mads
 */
public class PersonJpaController implements Serializable {

    public PersonJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public PersonJpaController() {
        
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Person person) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(person);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Person person) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            person = em.merge(person);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = person.getId();
                if (findPerson(id) == null) {
                    throw new NonexistentEntityException("The person with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Person person;
            try {
                person = em.getReference(Person.class, id);
                person.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The person with id " + id + " no longer exists.", enfe);
            }
            em.remove(person);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Person> findPersonEntities() {
        return findPersonEntities(true, -1, -1);
    }

    public List<Person> findPersonEntities(int maxResults, int firstResult) {
        return findPersonEntities(false, maxResults, firstResult);
    }

    private List<Person> findPersonEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Person.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Person findPerson(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Person.class, id);
        } finally {
            em.close();
        }
    }

    public int getPersonCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Person> rt = cq.from(Person.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

//    public void assignPersonToAddress(int id, Address a) {
//        EntityManager em = getEntityManager();
//        try {
//            Person p = em.getReference(Person.class, id);
//            a.addInfoEntity(p);
//            em.getTransaction().begin();
//            em.merge(a);
//            em.getTransaction().commit();
//        } finally {
//            em.close();
//        }
//    }
    public void assignPersonToHobby(Person p, Hobby h) {
        EntityManager em = getEntityManager();
        try {
            p.addHobby(h);
            em.getTransaction().begin();
            em.merge(h);
            em.merge(p);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
    public List<Object> getAllPersonInfo() {
        String result = "";
        EntityManager em = getEntityManager();

        Query q = em.createQuery("select IE.EMAIL, P.FIRSTNAME, P.LASTNAME, A.STREET, A.CITYINFO_ZIPCODE, A.ADDITIONALINFO, H.\"NAME\", H.DESCRIPTION, CI.CITY"
        +"from CPHMH35.INFOENTITY IE "
        +"join CPHMH35.PERSON P on IE.ID = P.ID "
        +"where IE.DTYPE = 'Person' ");
        List<Object> queryResult = q.getResultList(); 
        return queryResult;
    }
    
    public List<DataObject> getPersonInfoByPhone(int phoneNum) {
        String result = "";
        EntityManager em = getEntityManager();
//        try {
//        
//    } catch (Exception e) {
//    }
        Query q = em.createQuery("SELECT p.infoEntity.id FROM Phone p where p.phoneNumber =" + phoneNum);
        List<Integer> infoId = q.getResultList();
        Query q2 = em.createQuery("SELECT p.email FROM InfoEntity p where p.id = " + infoId.get(0));
        List<String> email = q2.getResultList();
//        Query q3 = em.createQuery("SELECT p.address FROM InfoEntity p where p.id = " + infoId.get(0));
//        List<String> address = q3.getResultList();
        Query q4 = em.createQuery("SELECT p.address.id FROM InfoEntity p where p.id = " + infoId.get(0));
        List<Integer> addId = q4.getResultList();
        Query q8 = em.createQuery("SELECT p.firstName, p.lastName FROM Person p where p.id = " + infoId.get(0));
        List<Object[]> name = q8.getResultList();
        Query q5 = em.createQuery("SELECT p.street FROM Address p where p.id =" + addId.get(0));
        List<String> street = q5.getResultList();
        Query q6 = em.createQuery("SELECT p.cityInfo.zipCode FROM Address p where p.id =" + addId.get(0));
        List<Integer> zip = q6.getResultList();
        Query q7 = em.createQuery("SELECT p.city FROM CityInfo p where p.zipCode =" + zip.get(0));
        List<String> city = q7.getResultList();
        
        Person per = findPerson(addId.get(0));
//        per.getHobbies().get(0);
//        Query q3 = em.createQuery("SELECT p. FROM Person p" + zip.get(0));
//        List<String> hobby = q3.getResultList();
        

        List<DataObject> objList = new ArrayList<>();
        DataObject dataO = new DataObject(name.get(0)[0].toString(), name.get(0)[1].toString(), phoneNum, email.get(0), city.get(0), street.get(0), zip.get(0));
//        result = "" + name.get(0)[0] + " " + name.get(0)[1] + " " + email.get(0) + " " + street.get(0) + " " + zip.get(0).toString() + " " + city.get(0) + " " + per.getHobbies().get(0).getName();
        objList.add(dataO);
//        objList.add(new DataObject(0, name.get(0)[0].toString()));
//        objList.add(new DataObject(1, name.get(0)[1].toString()));
//        objList.add(new DataObject(2, ph.getNumber()));
//        objList.add(new DataObject(3, email.get(0)));
//        objList.add(new DataObject(4, city.get(0)));
//        objList.add(new DataObject(5, street.get(0)));
//        objList.add(new DataObject(6, zip.get(0)));
//        
        return objList;
    }
    public List<Object> getPersonInfoByName(String name) {
        String result = "";
        EntityManager em = getEntityManager();
//        try {
//        
//    } catch (Exception e) {
//    }
        String[] names = name.split(" ");
        Query q1 = em.createQuery("SELECT IE.EMAIL, P.FIRSTNAME, P.LASTNAME FROM Person p JOIN INFOENTITY ON IE.ID = P.ID WHERE p.FIRSTNAME LIKE %"+names[0]+"%");
        List<Object> queryResult1 = q1.getResultList();
        
        Query q2 = em.createQuery("SELECT IE.EMAIL, P.FIRSTNAME, P.LASTNAME FROM Person p JOIN INFOENTITY ON IE.ID = P.ID WHERE p.LASTNAME LIKE %"+names[1]+"%");
        List<Object> queryResult2 = q2.getResultList();
        
        List<Object> allResults = new ArrayList<Object>(queryResult1);
        allResults.addAll(queryResult2);
        
        return allResults;
    }
}
