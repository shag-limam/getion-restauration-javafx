package com.gd.db.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.gd.db.HibernateConnection;
import com.gd.db.UMSDBException;
import com.gd.model.Developpeur;
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
	public List<Produit> readByDev(Developpeur dev) throws UMSDBException {
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

}
