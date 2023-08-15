package com.gd.controller;

import java.time.LocalDate;
import java.util.List;

import com.gd.db.UMSDBException;
import com.gd.model.Commande;
import com.gd.model.Produit;
import com.gd.run.GDApplication;

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
	private ComboBox<String> NiveauComboBox;
	@FXML
	private ComboBox<String> produitCommandeComboBox;


	@FXML
	private TextField QuantiteField;

	@FXML
	private TextField MontantTotalField;
		
	@FXML
	private DatePicker Date;

	@FXML
	private void initialize() throws UMSDBException {
	    NiveauComboBox.getItems().clear();
	    NiveauComboBox.getItems().addAll("Payée","Non payée");
	    
	    // Charger les noms des produits disponibles dans le ComboBox
	    List<String> nomsProduits = GDApplication.getInstance().getDataSource().getProduitNames();
	    produitCommandeComboBox.getItems().addAll(nomsProduits);
	}


	public void setCommande(Commande commande) {
	    this.commande = commande;
	    MontantTotalField.setText(String.valueOf(commande.getMontantTotal()));
	    QuantiteField.setText(String.valueOf(commande.getQuantite()));
	    Date.getEditor().setText(commande.getDateCommande());
	}

//	public void setCommande(Commande commande) {
//		this.commande = commande;
//		//AppField.setText(commande.getIntitule());
//		MontantTotalField.setText(String.valueOf(commande.getMontantTotal())); // Convert float to String
//		//QuantiteField.setText(String.valueOf(commande.getQuantite()));
//		QuantiteField.setText(Integer.toString(commande.getQuantite()));
//		Date.getEditor().setText(commande.getDateCommande());
//	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	// Called when the user clicks Valider.
	@FXML
	private void handleValider() throws UMSDBException{
		if (isInputValid()) {
			//commande.setIntitule(AppField.getText());
			// Convert the String value to float before setting the prix
	        float prixValue = Float.parseFloat(MontantTotalField.getText());
	        int quantiteValue = Integer.parseInt(QuantiteField.getText());
	        commande.setMontantTotal(prixValue);
	        commande.setQuantite(quantiteValue);
	        commande.setPayee(NiveauComboBox.getSelectionModel().getSelectedItem());
	        //commande.setDateCommande(Date.getEditor().getText().toString());
	        commande.setDateCommande(Date.getEditor().getText().toString());
	        
	        String nomProduit = produitCommandeComboBox.getSelectionModel().getSelectedItem();
	        Produit produit = GDApplication.getInstance().getDataSource().getProduitByName(nomProduit);
	        commande.setProduit(produit);

			validerClicked = true;
			dialogStage.close();
		}
	}

	// Validates the user input in the text fields.
	private boolean isInputValid() {
		String errorMessage = "";
//		if (AppField.getText() == null || AppField.getText().length() == 0) {
//			errorMessage += "Le Nom Application n'est pas renseigné !\n";
//		}

		// Compléter les contrôles des autres champs ...

//		if (DesField.getText() == null || DesField.getText().length() == 0) {
//			errorMessage += "Le Description n'est pas renseigné !\n";
//		}

		if (errorMessage.length() == 0)
			return true;
		else { // Show the error message.
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(dialogStage);
			alert.setTitle("Champs non renseignés et/ou invalides !");
			alert.setHeaderText("Veuillez remplir tous les champs svp !");
			alert.setContentText(errorMessage);
			alert.showAndWait();
			return false;
		}
	}

	// Called when the user clicks Annuler.
	@FXML
	private void handleAnnuler() {
		validerClicked = false;
		dialogStage.close();
	}

	public boolean isValiderClicked() {
		return validerClicked;
	}

}
