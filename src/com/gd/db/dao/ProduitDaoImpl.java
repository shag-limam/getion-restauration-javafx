package com.gd.db.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.gd.db.HibernateConnection;
import com.gd.db.UMSDBException;
import com.gd.model.Restaurateur;
import com.gd.model.Produit;


public class ProduitDaoImpl implements IDaoImpl<Produit> {
	
	
	


	@Override
	public void create(Produit obj) throws UMSDBException {
		// TODO Auto-generated method stub
		
		try {
			Session session = HibernateConnection.getInstance().getSession();
			// Creating Transaction Object
			Transaction transaction = session.beginTransaction();
			session.save(obj);
			// Transaction Is Committed To Database
			transaction.commit();
		} catch (Exception e) {
			throw new UMSDBException("ERROR:" + e.getClass() + ":" + e.getMessage());
		}

	}

	@SuppressWarnings({ "rawtypes", "deprecation", "unchecked" })
	@Override
	public List<Produit> readBy(Long id) throws UMSDBException {
		// TODO Auto-generated method stub
		List<Produit> produits = new ArrayList<>();

		try {
			   Session session = HibernateConnection.getInstance().getSession();
			   Transaction transaction = session.beginTransaction();
			
			   org.hibernate.query.Query q =  session.createQuery("From incident Where developpeur_id = id");
			   q.setLong("id", id);
			   produits = q.getResultList();
			   transaction.commit();
			
			
		
			
		} catch (Exception e) {
			throw new UMSDBException("ERROR:" + e.getClass() + ":" + e.getMessage());
		}

		return produits;
	}

	@Override
	public List<Produit> readByDev(int idRapporteur, String statu) throws UMSDBException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Produit obj) throws UMSDBException {
		// TODO Auto-generated method stub
		
		try {
			Session session = HibernateConnection.getInstance().getSession();
			// Creating Transaction Object
			Transaction transaction = session.beginTransaction();
			session.update(obj);
			// Transaction Is Committed To Database
			transaction.commit();
		} catch (Exception e) {
			throw new UMSDBException("ERROR:" + e.getClass() + ":" + e.getMessage());
		}

	}

	@Override
	public void delete(Integer id) throws UMSDBException {
		// TODO Auto-generated method stub

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Produit> list() throws UMSDBException {
		List<Produit> produits = new ArrayList<>();
		try {
			Session session = HibernateConnection.getInstance().getSession();
			Query query = session.createQuery("From Produit");
			produits = query.getResultList();
		} catch (Exception e) {
			throw new UMSDBException("ERROR:" + e.getClass() + ":" + e.getMessage());
		}
		return produits;
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<Produit> readByDev(Restaurateur dev) throws UMSDBException {
		// TODO Auto-generated method stub
		List<Produit> produits = new ArrayList<>();

		try {
			   Session session = HibernateConnection.getInstance().getSession();
			   Transaction transaction = session.beginTransaction();
			
			   org.hibernate.query.Query q =  session.createQuery("From Produit Where developpeur = "+dev+"");
			   
			   produits = q.getResultList();
			   transaction.commit();
			
			
		
			
		} catch (Exception e) {
			throw new UMSDBException("ERROR:" + e.getClass() + ":" + e.getMessage());
		}

		return produits;
	}
	
	
	@Override
    public List<String> listProductNames() throws UMSDBException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<String> criteriaQuery = builder.createQuery(String.class);
            Root<Produit> root = criteriaQuery.from(Produit.class);
            criteriaQuery.select(root.get("intitule"));
            return session.createQuery(criteriaQuery).list();
        } catch (Exception e) {
            throw new UMSDBException("Error while retrieving product names: " + e.getMessage(), e);
        }
    }

	@Override
	public Produit getProduitByIntitule(String intitule) throws UMSDBException {
	    try (Session session = HibernateConnection.getInstance().getSessionFactory().openSession()) {
	        return session.createQuery("from Produit where intitule = :intitule", Produit.class)
	                .setParameter("intitule", intitule)
	                .uniqueResult();
	    } catch (Exception e) {
	        throw new UMSDBException("ERROR:" + e.getClass() + ":" + e.getMessage());
	    }
	}
	
	@Override
    public void update1(Produit produit) throws UMSDBException {
        try (Session session = HibernateConnection.getInstance().getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(produit);
            transaction.commit();
        } catch (Exception e) {
            throw new UMSDBException("ERROR:" + e.getClass() + ":" + e.getMessage());
        }
    }



	//@Override
	public Produit getProduitByName(String nomProduit) throws UMSDBException {
	    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	        CriteriaBuilder builder = session.getCriteriaBuilder();
	        CriteriaQuery<Produit> criteriaQuery = builder.createQuery(Produit.class);
	        Root<Produit> root = criteriaQuery.from(Produit.class);
	        criteriaQuery.select(root).where(builder.equal(root.get("intitule"), nomProduit));
	        return session.createQuery(criteriaQuery).uniqueResult();
	    } catch (Exception e) {
	        throw new UMSDBException("Error while retrieving product by name: " + e.getMessage(), e);
	    }
	}


}
