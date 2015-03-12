/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.exceptions.NonexistentEntityException;
import Controller.exceptions.PreexistingEntityException;
import entity.CityInfo;
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
public class CityInfoJpaController implements Serializable {

    public CityInfoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CityInfo cityInfo) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(cityInfo);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCityInfo(cityInfo.getZipCode()) != null) {
                throw new PreexistingEntityException("CityInfo " + cityInfo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CityInfo cityInfo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            cityInfo = em.merge(cityInfo);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = cityInfo.getZipCode();
                if (findCityInfo(id) == null) {
                    throw new NonexistentEntityException("The cityInfo with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(int id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CityInfo cityInfo;
            try {
                cityInfo = em.getReference(CityInfo.class, id);
                cityInfo.getZipCode();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cityInfo with id " + id + " no longer exists.", enfe);
            }
            em.remove(cityInfo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CityInfo> findCityInfoEntities() {
        return findCityInfoEntities(true, -1, -1);
    }

    public List<CityInfo> findCityInfoEntities(int maxResults, int firstResult) {
        return findCityInfoEntities(false, maxResults, firstResult);
    }

    private List<CityInfo> findCityInfoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CityInfo.class));
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

    public CityInfo findCityInfo(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CityInfo.class, id);
        } finally {
            em.close();
        }
    }

    public int getCityInfoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CityInfo> rt = cq.from(CityInfo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    public List getAllZipCodes() {
       EntityManager em = getEntityManager();
        
        Query q = em.createQuery("SELECT z.zipCode FROM CityInfo z");
        List zipCodes = q.getResultList();
        return zipCodes;
    }
}
