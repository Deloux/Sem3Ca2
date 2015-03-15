/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.exceptions.NonexistentEntityException;
import entity.Address;
import entity.Company;
import entity.Person;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author mads
 */
public class CompanyJpaController implements Serializable {

    public CompanyJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Company company) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(company);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Company company) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            company = em.merge(company);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = company.getId();
                if (findCompany(id) == null) {
                    throw new NonexistentEntityException("The company with id " + id + " no longer exists.");
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
            Company company;
            try {
                company = em.getReference(Company.class, id);
                company.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The company with id " + id + " no longer exists.", enfe);
            }
            em.remove(company);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Company> findCompanyEntities() {
        return findCompanyEntities(true, -1, -1);
    }

    public List<Company> findCompanyEntities(int maxResults, int firstResult) {
        return findCompanyEntities(false, maxResults, firstResult);
    }

    private List<Company> findCompanyEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Company.class));
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

    public Company findCompany(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Company.class, id);
        } finally {
            em.close();
        }
    }

    public int getCompanyCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Company> rt = cq.from(Company.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

//    public void assignCompanyToAddress(int id, Address a) {
//        EntityManager em = getEntityManager();
//        try {
//            Company c = em.getReference(Company.class, id);
//            a.addInfoEntity(c);
//            em.getTransaction().begin();
//            em.merge(a);
//            em.getTransaction().commit();
//        } finally {
//            em.close();
//        }
//    }
    public List<Company> getCompHigherThanxxEmp(int empAmount) {
        EntityManager em = getEntityManager();
        Query q = em.createQuery("SELECT c FROM Company c WHERE c.numEmployees > :empAmount").setParameter(":empAmount", empAmount);
        List<Company> companies = q.getResultList();
        return companies;
    }

    public String getCompanyInfo(int phoneOrCvr) {
        EntityManager em = getEntityManager();
        String str = "";
        String str2 = "" + phoneOrCvr;
        if (str2.length() > 6) {
            Query q = em.createQuery("SELECT p.infoEntity.id FROM Phone p where p.phoneNumber =" + str2);
            List<Integer> infoId = q.getResultList();
            Query q2 = em.createQuery("SELECT p.email FROM InfoEntity p where p.id = " + infoId.get(0));
            List<String> email = q2.getResultList();
//        Query q3 = em.createQuery("SELECT p.address FROM InfoEntity p where p.id = " + infoId.get(0));
//        List<String> address = q3.getResultList();
            Query q4 = em.createQuery("SELECT p.address.id FROM InfoEntity p where p.id = " + infoId.get(0));
            List<Integer> addId = q4.getResultList();
            Query q8 = em.createQuery("SELECT p.name, p.description, p.numEmployees FROM Company p where p.id = " + infoId.get(0));
            List<Object[]> name = q8.getResultList();
            Query q5 = em.createQuery("SELECT p.street FROM Address p where p.id =" + addId.get(0));
            List<String> street = q5.getResultList();
            Query q6 = em.createQuery("SELECT p.cityInfo.zipCode FROM Address p where p.id =" + addId.get(0));
            List<Integer> zip = q6.getResultList();
            Query q7 = em.createQuery("SELECT p.city FROM CityInfo p where p.zipCode =" + zip.get(0));
            List<String> city = q7.getResultList();
            str = "" + name.get(0)[0] + " " + name.get(0)[1] + " " + name.get(0)[2] + " " + email.get(0) + " " + street.get(0) + " " + zip.get(0).toString() + " " + city.get(0);

        } else {
            Query q = em.createQuery("SELECT p.id FROM Company p where p.cvr =" + str2);
            List<Integer> infoId = q.getResultList();
            Query q2 = em.createQuery("SELECT p.email FROM InfoEntity p where p.id = " + infoId.get(0));
            List<String> email = q2.getResultList();
//        Query q3 = em.createQuery("SELECT p.address FROM InfoEntity p where p.id = " + infoId.get(0));
//        List<String> address = q3.getResultList();
            Query q4 = em.createQuery("SELECT p.address.id FROM InfoEntity p where p.id = " + infoId.get(0));
            List<Integer> addId = q4.getResultList();
            Query q8 = em.createQuery("SELECT p.name, p.description, p.numEmployees FROM Company p where p.id = " + infoId.get(0));
            List<Object[]> name = q8.getResultList();
            Query q5 = em.createQuery("SELECT p.street FROM Address p where p.id =" + addId.get(0));
            List<String> street = q5.getResultList();
            Query q6 = em.createQuery("SELECT p.cityInfo.zipCode FROM Address p where p.id =" + addId.get(0));
            List<Integer> zip = q6.getResultList();
            Query q7 = em.createQuery("SELECT p.city FROM CityInfo p where p.zipCode =" + zip.get(0));
            List<String> city = q7.getResultList();

            str = "" + name.get(0)[0] + " " + name.get(0)[1] + " " + name.get(0)[2] + " " + email.get(0) + " " + street.get(0) + " " + zip.get(0).toString() + " " + city.get(0);

        }
        return str;
    }
}
