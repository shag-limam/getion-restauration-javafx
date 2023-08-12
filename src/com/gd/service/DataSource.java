package com.gd.service;

import java.util.List;



import com.gd.db.UMSDBException;
import com.gd.db.dao.IDao;
import com.gd.db.dao.IDaoCommandImpl;
import com.gd.db.dao.IDaoImpl;
import com.gd.db.dao.ProduitDaoImpl;
import com.gd.db.dao.UtilisateurDaoImpl;
import com.gd.model.Commande;
import com.gd.model.Developpeur;
import com.gd.model.Produit;
import com.gd.model.Utilisateur;
import com.gd.db.dao.CommandeDaoImpl; // Replace with the actual package name


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DataSource {

	private ObservableList<Utilisateur> users = FXCollections.observableArrayList();

	private ObservableList<Produit> produits = FXCollections.observableArrayList();
	private ObservableList<String> dev = FXCollections.observableArrayList();
	private ObservableList<Commande> commandes = FXCollections.observableArrayList();


	private IDao dao;
	private IDaoImpl<Produit> daoImpl;
	private IDaoCommandImpl<Commande> daoCommandImpl;

	public DataSource() throws UMSDBException {
		dao = new UtilisateurDaoImpl();
		daoImpl = new ProduitDaoImpl();
		daoCommandImpl = new CommandeDaoImpl();
		listUsers();
		listDev();
		
		listProduir();
		
	}
	///////////////////////////////////////////////// Date source user //////////////////////////////////////////////////
     
	public ObservableList<Utilisateur> getUsers() {
		return users;
	}
	
	public ObservableList<String> getDev() {
		return dev;
	}
	
	
	

	public void AddUser(Utilisateur user) throws UMSDBException {
		
		dao.create(user);
		this.users.add(user);
		
		
		
	}
	
	public void DeleteUser(int id) throws UMSDBException {
		Utilisateur user = this.users.get(id);
		this.users.remove(user);
		dao.delete(user.getId());
		
		
	}
	
	public void UpdateUser(Utilisateur user,int selecteUserIndex ) throws UMSDBException {
		users.remove(selecteUserIndex);
		users.add(selecteUserIndex, user);
		dao.update(user);
		
	}
	
	public Utilisateur ReadUser (long l) throws UMSDBException {
		
	    return dao.read(l);
		
	} 
	
	public void listUsers() throws UMSDBException {
		List<Utilisateur> users = dao.list();
		this.users.addAll(users);
	}
	
	public void listDev() throws UMSDBException {
		List<String> usersDev = dao.listDev();
		this.dev.addAll(usersDev);
		
		
	}
	
	public Utilisateur Login(String login) throws UMSDBException {
		Utilisateur user = dao.Login(login);
		return user;
	}
	
	public String type(long id) throws UMSDBException {
		String type = dao.type(id);
		return type;
	}
	
	
     ///////////////////////////////////////////////// Date source produit //////////////////////////////////////////////////
	
   public void Addprodui(Produit produit) throws UMSDBException {
		
	    daoImpl.create(produit);
		this.produits.add(produit);
		
	}
   
   public ObservableList<Produit> getproduits() {
		return produits;
	}
   
   public void listProduir() throws UMSDBException {
		List<Produit> produits = daoImpl.list();
		this.produits.addAll(produits);
	}
   
   public void Updateproduit(Produit produit,int selecteUserIndex ) throws UMSDBException {
	   produits.remove(selecteUserIndex);
		produits.add(selecteUserIndex, produit);
		daoImpl.update(produit);
		
	}
   
	public void DeleteProduit(int id) throws UMSDBException {
		Produit produit = this.produits.get(id);
		this.produits.remove(produit);
		daoImpl.delete(produit.getId());
	}
   
   
   public List<Produit> readBy(Long long1) throws UMSDBException{
	   
	   List<Produit> produits = daoImpl.readBy(long1);
	   return produits;
		
	   
   }




///////////////////////////////////////////////// Date source commande //////////////////////////////////////////////////


	public void AddCommande(Commande commande) throws UMSDBException {
		daoCommandImpl.create(commande);
	    this.commandes.add(commande);
	}
	
	public ObservableList<Commande> getCommandes() {
	    return commandes;
	}
	
	public void listCommandes() throws UMSDBException {
	    List<Commande> commandes = daoCommandImpl.list();
	    this.commandes.addAll(commandes);
	}
	
	public void UpdateCommande(Commande commande, int selectedIndex) throws UMSDBException {
		commandes.remove(selectedIndex);
		commandes.add(selectedIndex, commande);
	    daoCommandImpl.update(commande);
	}
	
	public void DeleteCommande(int id) throws UMSDBException {
	    Commande commande = this.commandes.get(id);
	    this.commandes.remove(commande);
	    daoCommandImpl.delete(commande.getId());
	}

	
	public List<Commande> readCommandesByDev(Developpeur dev) throws UMSDBException {
	    List<Commande> commandes = daoCommandImpl.readByDev(dev);
	    return commandes;
	}
	
	public List<Commande> readCommandesByUserId(long userId) throws UMSDBException {
	    List<Commande> commandes = daoCommandImpl.readBy(userId);
	    return commandes;
	}

}


  


