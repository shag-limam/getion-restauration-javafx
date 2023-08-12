package com.gd.controller;

import java.time.LocalDate;

import com.gd.model.Commande;
import com.gd.model.Produit;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class CreerCommandController {


	private Commande commande;

    private Stage dialogStage;
    private boolean validerClicked;

    @FXML
    private ComboBox<Produit> produitComboBox;
    
    @FXML
    private TextField quantiteField;
    
    @FXML
    private DatePicker dateCommandePicker;

    @FXML
    private void initialize() {
        // Remplir la combobox avec les produits disponibles
        produitComboBox.getItems().addAll(/* Liste des produits disponibles depuis votre source de donn�es */);
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
        // Initialiser les champs avec les valeurs de la commande existante
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @FXML
    private void handleValider() {
        if (isInputValid()) {
            // R�cup�rer les valeurs des champs et les affecter � la nouvelle commande
            Produit selectedProduit = produitComboBox.getSelectionModel().getSelectedItem();
            // Convertir la quantit� en int
            int quantite = Integer.parseInt(quantiteField.getText());
            LocalDate dateCommande = dateCommandePicker.getValue();
            
            // Cr�er et configurer la nouvelle commande
            Commande nouvelleCommande = new Commande();
            nouvelleCommande.setProduit(selectedProduit);
            nouvelleCommande.setQuantite(quantite);
            nouvelleCommande.setDateCommande(dateCommande);
            
            // Enregistrer la nouvelle commande dans la base de donn�es ou dans votre source de donn�es
            
            validerClicked = true;
            dialogStage.close();
        }
    }

    private boolean isInputValid() {
        String errorMessage = "";

        // V�rifier que le produit est s�lectionn�
        if (produitComboBox.getSelectionModel().isEmpty()) {
            errorMessage += "Veuillez s�lectionner un produit !\n";
        }
        
        // V�rifier que la quantit� est un entier positif
        try {
            int quantite = Integer.parseInt(quantiteField.getText());
            if (quantite <= 0) {
                errorMessage += "La quantit� doit �tre un entier positif !\n";
            }
        } catch (NumberFormatException e) {
            errorMessage += "La quantit� doit �tre un nombre entier !\n";
        }
        
        // V�rifier que la date de commande est s�lectionn�e
        if (dateCommandePicker.getValue() == null) {
            errorMessage += "Veuillez s�lectionner une date de commande !\n";
        }

        if (errorMessage.isEmpty()) {
            return true; // Aucune erreur de validation
        } else {
            // Afficher une bo�te de dialogue d'erreur avec les messages d'erreur
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Champs invalides");
            alert.setHeaderText("Veuillez corriger les champs invalides");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return false;
        }
    }


    @FXML
    private void handleAnnuler() {
        validerClicked = false;
        dialogStage.close();
    }

    public boolean isValiderClicked() {
        return validerClicked;
    }

}
