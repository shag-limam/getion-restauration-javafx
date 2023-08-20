package com.gd.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Commande")
public class Commande {

    public List<Integer> getQuantitesProduits() {
		return quantitesProduits;
	}

	public void setQuantitesProduits(List<Integer> quantitesProduits) {
		this.quantitesProduits = quantitesProduits;
	}



	

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    
	
    @ManyToOne
    private Developpeur developpeur;

//    @OneToMany(mappedBy = "commande")
//    private List<Produit> produits = new ArrayList<>(); 
    
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Produit> produits = new ArrayList<>();
    
    

    public List<Produit> getProduits() {
        return produits;
    }

    public void setProduits(List<Produit> produits) {
        this.produits = produits;
    }
    
//    @ElementCollection
//    private List<Integer> quantitesProduits = new ArrayList<>();

    
    @ManyToOne
    private Produit produit;

    
    public Commande() {
        
    }
//    public Commande(String nomClient, String dateCommande,int quantite, List<Produit> produitsChoisis, Double montantTotal) {
//        this.nomClient = nomClient;
//        this.dateCommande = dateCommande;
//        this.produits = produitsChoisis;
//        this.montantTotal = montantTotal;
//        this.quantite = quantite;
//    }
    
    public Commande(String nomClient, String dateCommande, List<Produit> produitsChoisis, List<Integer> quantitesChoisies, Double montantTotal) {
        this.nomClient = nomClient;
        this.dateCommande = dateCommande;
        this.produits = produitsChoisis;
        this.montantTotal = montantTotal;
        this.quantitesProduits = quantitesChoisies;
    }


    
    
    @Column(name = "nomClient")
	private String nomClient;
    
    @Column(name = "dateCommande")
	private String dateCommande;
    
    @Column(name = "quantitesProduits")
    @ElementCollection
    private List<Integer> quantitesProduits = new ArrayList<>();
    
    @Column(name = "quantite")
	private int quantite;
    
    
    




	@Column(name = "produit")
	private String produitC;
    @Column(name = "montantTotal")
	private Double montantTotal;
    
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

	public Double getMontantTotal() {
		return montantTotal;
	}

	public void setMontantTotal(Double montantTotal) {
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
