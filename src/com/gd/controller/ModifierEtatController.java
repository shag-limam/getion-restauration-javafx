package com.gd.controller;


import java.util.Date;

//import com.gd.model.Etat;
import com.gd.model.Commande;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;

import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class ModifierEtatController {
	
	


	private Commande commande;
	//private Date dateupdate;

	private Stage dialogStage;
	private boolean validerClicked;
	@FXML
	private ComboBox<String> EtatComboBox;
	
	@FXML
	private ComboBox<String> NiveauComboBox;

	
	

	@FXML
	private void initialize() {
		NiveauComboBox.getItems().clear();
		NiveauComboBox.getItems().addAll("Non payée",  "Payée");
	}

	public void setcommande(Commande commande) {
		this.commande = commande;
//		AppField.setText(commande.getNomClient());
//		DesField.setText(commande.get);
		

	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	// Called when the user clicks Valider.
		@SuppressWarnings("deprecation")
		@FXML
		private void handleValider() {
			if (isInputValid()) {
				
//				this.dateupdate = new Date();
				commande.setPayee(NiveauComboBox.getSelectionModel().getSelectedItem());
//				incident.setOpendate("ll y a "+" " +dateupdate.getDay()+" "+ " jours");
			

				validerClicked = true;
				dialogStage.close();
			}
		}

		// Validates the user input in the text fields.
		private boolean isInputValid() {
			String errorMessage = "";
			

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