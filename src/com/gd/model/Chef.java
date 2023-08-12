package com.gd.model;



import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Chef")
public class Chef extends Utilisateur {
	
	@OneToMany( targetEntity=Produit.class)
    private List<Produit> produits = new ArrayList<>();

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public Chef(Long id, String login, String password, String nom, String prenom, String email) {
		super(id, login, password, nom, prenom, email);
		// TODO Auto-generated constructor stub
	}


	public Chef() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	
		

}
