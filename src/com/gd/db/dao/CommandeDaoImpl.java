package com.gd.db.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.gd.db.HibernateConnection;
import com.gd.db.UMSDBException;
import com.gd.model.Commande;
import com.gd.model.Developpeur;

public class CommandeDaoImpl implements IDaoCommandImpl<Commande> {

    @Override
    public void create(Commande obj) throws UMSDBException {
        try {
            Session session = HibernateConnection.getInstance().getSession();
            Transaction transaction = session.beginTransaction();
            session.save(obj);
            transaction.commit();
        } catch (Exception e) {
            throw new UMSDBException("ERROR:" + e.getClass() + ":" + e.getMessage());
        }
    }
    

    @Override
    public List<Commande> readByDev(Developpeur dev) throws UMSDBException {
        List<Commande> commandes = new ArrayList<>();
        try {
            Session session = HibernateConnection.getInstance().getSession();
            Transaction transaction = session.beginTransaction();
            Query q = session.createQuery("FROM Commande WHERE developpeur = :dev");
            q.setParameter("dev", dev);
            commandes = q.getResultList();
            transaction.commit();
        } catch (Exception e) {
            throw new UMSDBException("ERROR:" + e.getClass() + ":" + e.getMessage());
        }
        return commandes;
    }

    @Override
    public void update(Commande obj) throws UMSDBException {
        try {
            Session session = HibernateConnection.getInstance().getSession();
            Transaction transaction = session.beginTransaction();
            session.update(obj);
            transaction.commit();
        } catch (Exception e) {
            throw new UMSDBException("ERROR:" + e.getClass() + ":" + e.getMessage());
        }
    }

    @Override
    public void delete(Integer id) throws UMSDBException {
        try {
            Session session = HibernateConnection.getInstance().getSession();
            Transaction transaction = session.beginTransaction();
            Commande commande = session.find(Commande.class, id);
            if (commande != null) {
                session.delete(commande);
            }
            transaction.commit();
        } catch (Exception e) {
            throw new UMSDBException("ERROR:" + e.getClass() + ":" + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Commande> list() throws UMSDBException {
        List<Commande> commandes = new ArrayList<>();
        try {
            Session session = HibernateConnection.getInstance().getSession();
            Query query = session.createQuery("FROM Commande");
            commandes = query.getResultList();
        } catch (Exception e) {
            throw new UMSDBException("ERROR:" + e.getClass() + ":" + e.getMessage());
        }
        return commandes;
    }
    
    
    @Override
    public List<Commande> readByDev(int idRapporteur, String statu) throws UMSDBException {
        List<Commande> commandes = new ArrayList<>();
        try {
            Session session = HibernateConnection.getInstance().getSession();
            Transaction transaction = session.beginTransaction();
            
            Query q = session.createQuery("FROM Commande WHERE developpeur_id = :idRapporteur AND statu = :statu");
            q.setParameter("idRapporteur", idRapporteur);
            q.setParameter("statu", statu);
            commandes = q.getResultList();
            
            transaction.commit();
        } catch (Exception e) {
            throw new UMSDBException("ERROR:" + e.getClass() + ":" + e.getMessage());
        }
        return commandes;
    }
    
    @Override
    public List<Commande> readBy(Long id) throws UMSDBException {
        List<Commande> commandes = new ArrayList<>();
        try {
            Session session = HibernateConnection.getInstance().getSession();
            Transaction transaction = session.beginTransaction();
            Query q = session.createQuery("FROM Commande WHERE id = :id");
            q.setParameter("id", id);
            commandes = q.getResultList();
            transaction.commit();
        } catch (Exception e) {
            throw new UMSDBException("ERROR:" + e.getClass() + ":" + e.getMessage());
        }
        return commandes;
    }
    
    // Ajoutez d'autres méthodes spécifiques aux commandes si nécessaires

}
