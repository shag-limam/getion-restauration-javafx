package com.gd.db.dao;

import java.util.List;

import com.gd.db.UMSDBException;
import com.gd.model.Commande;
import com.gd.model.Restaurateur;
import com.gd.model.Produit;



public interface IDaoImpl<T> {
	
	public void create(T obj) throws UMSDBException;
	
	public List<T> readBy(Long id) throws UMSDBException;
	
	public List<T> readByDev(int idRapporteur, String statu) throws UMSDBException;
	
	public void update(T obj) throws UMSDBException;
	
	//public void update(String obj) throws UMSDBException;
	
	public void delete(Integer id) throws UMSDBException;
	
	public List<T> list() throws UMSDBException;

	List<Produit> readByDev(Restaurateur dev) throws UMSDBException;

	List<String> listProductNames() throws UMSDBException;
	T getProduitByName(String nomProduit) throws UMSDBException;

	Produit getProduitByIntitule(String intitule) throws UMSDBException;
	//public List<String> listProductNames();

	void update1(Produit produit) throws UMSDBException;

	//public Produit getProduitByName(String nomProduit);

	//List<String> listProductNames() throws UMSDBException;

//	public List<String> listProductNames();
//
//	public Produit getProduitByName(String nomProduit);
	
	

}

















