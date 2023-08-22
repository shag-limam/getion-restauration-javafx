package com.gd.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
//
//@Entity
//@Table(name = "Produit")
//public class Produit  implements Serializable {
//	/**
//	 * 
//	 */
//	private static final long serialVersionUID = 1L;
//	@Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int id;
//	
//	@Lob
//    @Column(name = "image", columnDefinition = "BLOB")
//    private byte[] image;
//
//    
//	@Column(name = "description")
//	private String description;
//	
//	@Column(name = "opendate")
//	private String opendate;
//	
//	
////	@ManyToOne 
////	private Commande commande = null;
//	
//	@ManyToMany(mappedBy = "produits", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
//    private List<Commande> commandes = new ArrayList<>();
//	
//	@Column(name = "quantite")
//	private int quantite;
//	
//	@Column(name = "intitule")
//	private String intitule;
//	
//	@Column(name = "prix")
//	private float prix;
//	
//	@Column(name = "etatproduit")
//	private String etatproduit;
//	
//
//	
//	@ManyToOne 
//    private Chef chef;
//	
//	@ManyToOne 
//	private Developpeur developpeur;
//	@OneToMany(
//	        cascade = CascadeType.ALL,
//	        orphanRemoval = true
//	    )
//	@JoinColumn(name = "produit_id")
//	
//	public int getId() {
//		return id;
//	}
//	public void setId(int id) {
//		this.id = id;
//	}
//	public float getPrix() {
//		return prix;
//	}
//	public void setPrix(float prix) {
//		this.prix = prix;
//	}
//	public String getDescription() {
//		return description;
//	}
//	public void setDescription(String description) {
//		this.description = description;
//	}
//	public String getOpendate() {
//		return opendate;
//	}
//	public void setOpendate(String opendate) {
//		this.opendate = opendate;
//	}
//
//
//	public int getQuantite() {
//		return quantite;
//	}
//
//	public void setQuantite(int quantite) {
//		this.quantite = quantite;
//	}
//
//	
//
//	public Chef getRapporteur() {
//		return chef;
//	}
//	public void setRapporteur(Chef chef) {
//		this.chef = chef;
//	}
//	public Developpeur getDeveloppeur() {
//		return developpeur;
//	}
//	public void setDeveloppeur(Developpeur developpeur) {
//		this.developpeur = developpeur;
//	}
//	public String getIntitule() {
//		return intitule;
//	}
//	public void setIntitule(String intitule) {
//		this.intitule = intitule;
//	}
//	public String getEtat() {
//		return etatproduit;
//	}
//	public void setEtat(String etat) {
//		this.etatproduit = etat;
//	}
//
//
//}
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.*;

@Entity
@Table(name = "Produit")
public class Produit implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Lob
    @Column(name = "image", columnDefinition = "BLOB")
    private byte[] image;

    @Column(name = "description")
    private String description;

    @Column(name = "opendate")
    private String opendate;

    @ManyToMany(mappedBy = "produits", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    private List<Commande> commandes = new ArrayList<>();


    @Column(name = "quantite")
    private int quantite;

    @Column(name = "intitule")
    private String intitule;

    @Column(name = "prix")
    private float prix;
    
    @Column(name = "etatproduit")
	private String etatproduit;

    public String getEtatproduit() {
		return etatproduit;
	}

	public void setEtatproduit(String etatproduit) {
		this.etatproduit = etatproduit;
	}

	@ManyToOne
    private Chef chef;

    @ManyToOne
    private Developpeur developpeur;

//    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
//    @JoinColumn(name = "produit_id")
                          


 
    
    public StringProperty intituleProperty() {
        return new SimpleStringProperty(intitule);
    }
    
    public StringProperty getatProperty() {
        return new SimpleStringProperty(etatproduit);
    }

    public IntegerProperty quantiteProperty() {
        return new SimpleIntegerProperty(quantite);
    }

    
    public FloatProperty prixProperty() {
        return new SimpleFloatProperty(prix);
    }

    
 

    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOpendate() {
		return opendate;
	}

	public void setOpendate(String opendate) {
		this.opendate = opendate;
	}

	public List<Commande> getCommandes() {
		return commandes;
	}

	public void setCommandes(List<Commande> commandes) {
		this.commandes = commandes;
	}

	public int getQuantite() {
		return quantite;
	}

	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}

	public String getIntitule() {
		return intitule;
	}

	public void setIntitule(String intitule) {
		this.intitule = intitule;
	}

	public float getPrix() {
		return prix;
	}

	public void setPrix(float prix) {
		this.prix = prix;
	}

	

	public Chef getChef() {
		return chef;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public Developpeur getDeveloppeur() {
		return developpeur;
	}

	public void setDeveloppeur(Developpeur developpeur) {
		this.developpeur = developpeur;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	

}
