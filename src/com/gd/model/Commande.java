package com.gd.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "Commande")
public class Commande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne 
	private Chef cef;
	@OneToMany(
	        cascade = CascadeType.ALL,
	        orphanRemoval = true
	    )
	@JoinColumn(name = "commande_id")
	
    @ManyToOne
    private Developpeur developpeur;

    @ManyToOne
    private Produit produit;
    
    @Column(name = "dateCommande")
	private String dateCommande;
    
    @Column(name = "quantite")
	private int quantite;
    @Column(name = "produit")
	private String produitC;
    @Column(name = "montantTotal")
	private Float montantTotal;
    
    @Column(name = "payee")
	private String payee;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Developpeur getDeveloppeur() {
		return developpeur;
	}

	public void setDeveloppeur(Developpeur developpeur) {
		this.developpeur = developpeur;
	}

	public Produit getProduit() {
		return produit;
	}

	public void setProduit(Produit produit) {
		this.produit = produit;
	}
	public String getProduitc() {
		return produitC;
	}

	public void setProduitc(String produitC) {
		this.produitC = produitC;
	}

	public String getDateCommande() {
		return dateCommande;
	}

	public void setDateCommande(String dateCommande) {
		this.dateCommande = dateCommande;
	}

	public int getQuantite() {
		return quantite;
	}

	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}

	public Float getMontantTotal() {
		return montantTotal;
	}

	public void setMontantTotal(float montantTotal) {
		this.montantTotal = montantTotal;
	}

	public String isPayee() {
		return payee;
	}

	public void setPayee(String payee) {
		this.payee = payee;
	}
    

    // Ajoutez les constructeurs, getters, setters et autres méthodes nécessaires

}
