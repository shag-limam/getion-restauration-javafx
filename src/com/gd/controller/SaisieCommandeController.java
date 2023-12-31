package com.gd.controller;
import org.hibernate.query.Query;

import com.gd.db.UMSDBException;
import com.gd.db.dao.*;
import com.gd.model.Commande;
import com.gd.model.Produit;
import com.gd.run.GDApplication;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
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

//    @FXML
//    private TextField dateField;
    @FXML
    private DatePicker datePField;
    
    private Stage dialogStage;
    
	private boolean validerClicked;

	private Produit produit;
	
	@FXML
	private TableView<Commande> commandeTable;

	
    @FXML
    private TextField clientField;
    @FXML
	private ComboBox<String> NiveauComboBox;
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

    private RestaurateurUIController restaurateurUIController;

	
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
        statutColumn.setCellValueFactory(cellData -> cellData.getValue().getatProperty());

        // Charger les produits en stock dans le TableView
        try {
            loadProduitsEnStock();
        } catch (UMSDBException e) {
            e.printStackTrace();
        }

        // Initialiser la liste des produits s�lectionn�s
        produitsSelectionnesList = FXCollections.observableArrayList();
        produitsListView.setItems(produitsSelectionnesList);
        NiveauComboBox.getItems().clear();
	    NiveauComboBox.getItems().addAll("Pay�e", "Non pay�e");
    }

    private void loadProduitsEnStock() throws UMSDBException {
        List<Produit> produits = produitDAO.list();
        ObservableList<Produit> produitsEnStock = FXCollections.observableArrayList();

        for (Produit produit : produits) {
            if (produit.getQuantite() > 0) { // V�rifier si la quantit� est sup�rieure � 0 (en stock)
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
		statutColumn.setText(produit.getEtatproduit());
		
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
            showAlert("Aucun produit s�lectionn�", "Veuillez s�lectionner un produit avant de continuer.");
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
                    String produitInfo = produitSelectionne.getIntitule() + ", Quantit�: " + quantiteChoisie + ", Montant: " + montant;

                    // Mettre � jour le montant total
                    montantTotal += montant;

                    // Ajouter le produitInfo � la liste des produits s�lectionn�s
                    produitsSelectionnesList.add(produitInfo);

                    // Actualiser le montant total affich�
                    totalField.setText(String.valueOf(montantTotal));

                  
                    // Effacer les champs d'intitul� et de quantit�
                    intituleField.setText("");
                    quantiteField.setText("");
                } else {
                    showAlert("Quantit� invalide", "La quantit� saisie n'est pas valide!");
                }
            } catch (NumberFormatException e) {
                showAlert("Erreur de saisie", "Veuillez saisir un nombre entier pour la quantit�!");
            }
        } else {
            showAlert("Aucun produit s�lectionn�", "Veuillez s�lectionner un produit avant de l'ajouter.");
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
        // R�cup�rer les d�tails de la commande (nom client, date, produits s�lectionn�s, etc.)
        String nomClient = clientField.getText();
        LocalDate dateCommande = dateField.getValue();
        double montantTotal = this.montantTotal;
        List<Produit> produitsChoisis = new ArrayList<>();

        // Convertir la liste des noms de produits s�lectionn�s en liste d'objets Produit
        for (String produitInfo : produitsSelectionnesList) {
            String[] infoArray = produitInfo.split(", ");
            String intitule = infoArray[0];
            int quantiteChoisie = Integer.parseInt(infoArray[1].replace("Quantit�: ", ""));
            double montant = Double.parseDouble(infoArray[2].replace("Montant: ", ""));
            
            // R�cup�rer le Produit depuis la base de donn�es en utilisant l'intitul�
            Produit produit = produitDAO.getProduitByIntitule(intitule); // � adapter selon votre DAO

            // Assurez-vous que le produit a �t� r�cup�r� avec succ�s
            if (produit != null) {
            	
            	if (quantiteChoisie <= produit.getQuantite()) {
                    // Mettre � jour les d�tails du produit (r�duire la quantit� en stock)
                    produit.setQuantite(produit.getQuantite() - quantiteChoisie);}
                // Mettre � jour les d�tails du produit si n�cessaire
                produit.setQuantite(quantiteChoisie);
                // Autres mises � jour possibles...
                
                // Ajouter le produit � la liste des produits choisis
                produitsChoisis.add(produit);
            }
        }

        // Cr�er la commande et enregistrer dans la base de donn�es
        Commande commande = new Commande(nomClient, dateCommande, produitsChoisis, montantTotal);
        commandeDAO.create(commande);
        for (Produit produit : produitsChoisis) {
            produitDAO.update(produit);
        }
        // Afficher un message de succ�s
        showAlert("Commande enregistr�e", "La commande a �t� enregistr�e avec succ�s!");

        // Fermer la fen�tre de saisie de commande
        closeWindow();
    }
*/
    private Commande selectedCommande;

    public void setSelectedCommande(Commande selectedCommande) {
        this.selectedCommande = selectedCommande;
        // Now you have access to the selected Commande in this controller
    }
//    @FXML
//    private void handleOpenModifierEtatUI() {
//        try {
//            Commande selectedCommande = commandeTable.getSelectionModel().getSelectedItem();
//            if (selectedCommande == null) {
//                showAlert("Aucune commande s�lectionn�e", "Veuillez s�lectionner une commande avant de continuer.");
//                return;
//            }
//            
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("../ui/ModifierEtatUI.fxml"));
//            Parent modifierEtatUI = loader.load();
//            
//            ModifierEtatController modifierEtatController = loader.getController();
//            modifierEtatController.setSelectedCommande(selectedCommande); // Set the selected Commande
//
//            Stage stage = new Stage();
//            stage.setTitle("Modifier l'�tat de paiement");
//            stage.setScene(new Scene(modifierEtatUI));
//            stage.show();
//        } catch (IOException e) {
//            e.printStackTrace();
//            // Handle the error
//        }
//    }




    public void setDeveloppeurUIController(RestaurateurUIController restaurateurUIController) {
        this.restaurateurUIController = restaurateurUIController;
    }

    
    @FXML
    private void handleEnregistrerCommande() throws UMSDBException {
        String nomClient = clientField.getText();
        
        String etat =NiveauComboBox.getSelectionModel().getSelectedItem();
        
        LocalDate selectedDate = datePField.getValue();
        String dateCommande = selectedDate.toString();
        
        double montantTotal = this.montantTotal;
        List<Produit> produitsChoisis = new ArrayList<>();
        List<Integer> quantitesChoisies = new ArrayList<>();

        for (String produitInfo : produitsSelectionnesList) {
            String[] infoArray = produitInfo.split(", ");
            String intitule = infoArray[0];
            int quantiteChoisie = Integer.parseInt(infoArray[1].replace("Quantit�: ", ""));
            double montant = Double.parseDouble(infoArray[2].replace("Montant: ", ""));

            Produit produit = produitDAO.getProduitByIntitule(intitule);
            if (produit != null) {
                produitsChoisis.add(produit);
                quantitesChoisies.add(quantiteChoisie);
            }
        }

       // int quantiteChoisieTotale = quantitesChoisies.stream().mapToInt(Integer::intValue).sum();
       // Commande commande = new Commande(nomClient, dateCommande, quantiteChoisieTotale, produitsChoisis, montantTotal);
       
       
        Commande commande = new Commande(nomClient, dateCommande, produitsChoisis, quantitesChoisies, montantTotal,etat);
        commandeDAO.create(commande);
        

        for (int i = 0; i < produitsChoisis.size(); i++) {
            Produit produit = produitsChoisis.get(i);
            int quantiteChoisie = quantitesChoisies.get(i);
            produit.setQuantite(produit.getQuantite() - quantiteChoisie);
            produitDAO.update1(produit);
        }

        showAlert("Commande enregistr�e", "La commande a �t� enregistr�e avec succ�s!");

        closeWindow();
    }

    @FXML
	private void handleAnnuler() {
		
		closeWindow();
	}
    
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	
	public boolean isValiderClicked() {
		return validerClicked;
	}
    


}