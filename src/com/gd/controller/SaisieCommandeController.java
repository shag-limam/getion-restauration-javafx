package com.gd.controller;
import org.hibernate.query.Query;

import com.gd.db.UMSDBException;
import com.gd.db.dao.*;
import com.gd.model.Commande;
import com.gd.model.Produit;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;


import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class SaisieCommandeController implements Initializable {

    @FXML
    private TextField dateField;
    
    private Stage dialogStage;
    
	private boolean validerClicked;

	private Produit produit;
	
    @FXML
    private TextField clientField;
    @FXML
    private TextField intituleField;

    @FXML
    private TableView<Produit> productTable;

    @FXML
    private TableColumn<Produit, String> intituleColumn;

    @FXML
    private TableColumn<Produit,Float> prixColumn;

    @FXML
    private TableColumn<Produit, Integer> quantiteColumn;

    @FXML
    private TableColumn<Produit, String> statutColumn;

    @FXML
    private ListView<String> produitsListView;

    @FXML
    private TextField quantiteField;
    @FXML
    private TextField totalField;

    private ObservableList<Produit> produitsList;
    private ObservableList<String> produitsSelectionnesList;
    private Commande commande;
    private double montantTotal = 0.0;
   private IDaoCommandImpl commandeDAO;
    private IDaoImpl<Produit> produitDAO;
    public SaisieCommandeController() {
    	produitDAO=new ProduitDaoImpl();
    	commandeDAO=new CommandeDaoImpl();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialiser les colonnes du TableView
        intituleColumn.setCellValueFactory(cellData -> cellData.getValue().intituleProperty());
        prixColumn.setCellValueFactory(cellData -> cellData.getValue().prixProperty().asObject());
        quantiteColumn.setCellValueFactory(cellData -> cellData.getValue().quantiteProperty().asObject());
        //statutColumn.setCellValueFactory(cellData -> cellData.getValue().etatProperty());

        // Charger les produits en stock dans le TableView
        try {
            loadProduitsEnStock();
        } catch (UMSDBException e) {
            e.printStackTrace();
        }

        // Initialiser la liste des produits sélectionnés
        produitsSelectionnesList = FXCollections.observableArrayList();
        produitsListView.setItems(produitsSelectionnesList);
    }

    private void loadProduitsEnStock() throws UMSDBException {
        List<Produit> produits = produitDAO.list();
        ObservableList<Produit> produitsEnStock = FXCollections.observableArrayList();

        for (Produit produit : produits) {
            if (produit.getQuantite() > 0) { // Vérifier si la quantité est supérieure à 0 (en stock)
                produitsEnStock.add(produit);
            }
        }

        productTable.setItems(produitsEnStock);
    }
    
    public void setProduit(Produit produit) {
		this.produit = produit;
		
		intituleColumn.setText(produit.getIntitule());
		prixColumn.setText(String.valueOf(produit.getPrix())); // Convert float to String
		quantiteColumn.setText(String.valueOf(produit.getQuantite()));
		//statutColumn.setText(produit.getEtat());
		
	}

    @FXML
    private void handleSelectionner() {
        Produit produitSelectionne = productTable.getSelectionModel().getSelectedItem();
        if (produitSelectionne != null) {
        	intituleField.setText(produitSelectionne.getIntitule());
            quantiteField.setText("");
            //produitsListView.getItems().clear();
            //produitsListView.getItems().add(produitSelectionne.getIntitule());
        } else {
            showAlert("Aucun produit sélectionné", "Veuillez sélectionner un produit avant de continuer.");
        }
    }
  
    @FXML
    private void handleAjouterProduit() {
        Produit produitSelectionne = productTable.getSelectionModel().getSelectedItem();
        if (produitSelectionne != null) {
            try {
                int quantiteChoisie = Integer.parseInt(quantiteField.getText());
                if (quantiteChoisie >= 1 && quantiteChoisie <= produitSelectionne.getQuantite()) {
                    double prixProduit = produitSelectionne.getPrix();
                    double montant = prixProduit * quantiteChoisie;
                    String produitInfo = produitSelectionne.getIntitule() + ", Quantité: " + quantiteChoisie + ", Montant: " + montant;

                    // Mettre à jour le montant total
                    montantTotal += montant;

                    // Ajouter le produitInfo à la liste des produits sélectionnés
                    produitsSelectionnesList.add(produitInfo);

                    // Actualiser le montant total affiché
                    totalField.setText(String.valueOf(montantTotal));

                  
                    // Effacer les champs d'intitulé et de quantité
                    intituleField.setText("");
                    quantiteField.setText("");
                } else {
                    showAlert("Quantité invalide", "La quantité saisie n'est pas valide!");
                }
            } catch (NumberFormatException e) {
                showAlert("Erreur de saisie", "Veuillez saisir un nombre entier pour la quantité!");
            }
        } else {
            showAlert("Aucun produit sélectionné", "Veuillez sélectionner un produit avant de l'ajouter.");
        }
    }
    
 
   
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void closeWindow() {
        Stage stage = (Stage) productTable.getScene().getWindow();
        stage.close();
    }
    
   

   /* 
    @FXML
    private void handleEnregistrerCommande() throws UMSDBException {
        // Récupérer les détails de la commande (nom client, date, produits sélectionnés, etc.)
        String nomClient = clientField.getText();
        LocalDate dateCommande = dateField.getValue();
        double montantTotal = this.montantTotal;
        List<Produit> produitsChoisis = new ArrayList<>();

        // Convertir la liste des noms de produits sélectionnés en liste d'objets Produit
        for (String produitInfo : produitsSelectionnesList) {
            String[] infoArray = produitInfo.split(", ");
            String intitule = infoArray[0];
            int quantiteChoisie = Integer.parseInt(infoArray[1].replace("Quantité: ", ""));
            double montant = Double.parseDouble(infoArray[2].replace("Montant: ", ""));
            
            // Récupérer le Produit depuis la base de données en utilisant l'intitulé
            Produit produit = produitDAO.getProduitByIntitule(intitule); // À adapter selon votre DAO

            // Assurez-vous que le produit a été récupéré avec succès
            if (produit != null) {
            	
            	if (quantiteChoisie <= produit.getQuantite()) {
                    // Mettre à jour les détails du produit (réduire la quantité en stock)
                    produit.setQuantite(produit.getQuantite() - quantiteChoisie);}
                // Mettre à jour les détails du produit si nécessaire
                produit.setQuantite(quantiteChoisie);
                // Autres mises à jour possibles...
                
                // Ajouter le produit à la liste des produits choisis
                produitsChoisis.add(produit);
            }
        }

        // Créer la commande et enregistrer dans la base de données
        Commande commande = new Commande(nomClient, dateCommande, produitsChoisis, montantTotal);
        commandeDAO.create(commande);
        for (Produit produit : produitsChoisis) {
            produitDAO.update(produit);
        }
        // Afficher un message de succès
        showAlert("Commande enregistrée", "La commande a été enregistrée avec succès!");

        // Fermer la fenêtre de saisie de commande
        closeWindow();
    }
*/
    
    @FXML
    private void handleEnregistrerCommande() throws UMSDBException {
        String nomClient = clientField.getText();
        String dateCommande = dateField.getText();
        double montantTotal = this.montantTotal;
        List<Produit> produitsChoisis = new ArrayList<>();
        List<Integer> quantitesChoisies = new ArrayList<>();

        for (String produitInfo : produitsSelectionnesList) {
            String[] infoArray = produitInfo.split(", ");
            String intitule = infoArray[0];
            int quantiteChoisie = Integer.parseInt(infoArray[1].replace("Quantité: ", ""));
            double montant = Double.parseDouble(infoArray[2].replace("Montant: ", ""));

            Produit produit = produitDAO.getProduitByIntitule(intitule);
            if (produit != null) {
                produitsChoisis.add(produit);
                quantitesChoisies.add(quantiteChoisie);
            }
        }

       // int quantiteChoisieTotale = quantitesChoisies.stream().mapToInt(Integer::intValue).sum();
       // Commande commande = new Commande(nomClient, dateCommande, quantiteChoisieTotale, produitsChoisis, montantTotal);
       
        Commande commande = new Commande(nomClient, dateCommande, produitsChoisis, quantitesChoisies, montantTotal);
        commandeDAO.create(commande);
        

        for (int i = 0; i < produitsChoisis.size(); i++) {
            Produit produit = produitsChoisis.get(i);
            int quantiteChoisie = quantitesChoisies.get(i);
            produit.setQuantite(produit.getQuantite() - quantiteChoisie);
            produitDAO.update1(produit);
        }

        showAlert("Commande enregistrée", "La commande a été enregistrée avec succès!");

        closeWindow();
    }

    
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	
	public boolean isValiderClicked() {
		return validerClicked;
	}
    


}