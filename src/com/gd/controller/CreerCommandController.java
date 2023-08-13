package com.gd.controller;

import com.gd.model.Commande;
import com.gd.model.Developpeur;
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

import java.time.LocalDate;
import java.util.List;

public class CreerCommandController {

    private Commande commande;

    private Stage dialogStage;
    private boolean validerClicked;

    @FXML
    private ComboBox<String> EtatComboBox;
    @FXML
    private ComboBox<String> IntituleComboBox;

    @FXML
    private TextField ClientField;

    @FXML
    private TextArea DescField;

    @FXML
    private DatePicker Date;

    @FXML
    private TextField QuantiteField;

    @FXML
    private TextField MontantTotalField;
    
    
    @FXML
    private void initialize() {
        EtatComboBox.getItems().clear();
        EtatComboBox.getItems().addAll("Payée", "Non payée");

//        // Populate the ComboBox with the intitule values of products
//        List<Produit> produits = GDApplication.getInstance().getDataSource// Retrieve the list of produits from your data source
//        for (Produit produit : produits) {
//        	IntituleComboBox.getItems().add(produit.getIntitule());
        }
    


    public void setCommande(Commande commande) {
        this.commande = commande;
        // Initialize UI elements with existing Commande data
        // For example:
        EtatComboBox.setValue(commande.isPayee() ? "Payée" : "Non payée");
        ClientField.setText(commande.getDeveloppeur().getNom()); // Assuming Developpeur has a 'nom' property
        //DescField.setText(commande.getProduit().getDescription());
        DescField.setText(commande.getProduit().getIntitule()); // Use getIntitule() instead of getDescription()
        Date.setValue(commande.getDateCommande());
        QuantiteField.setText(String.valueOf(commande.getQuantite()));
        MontantTotalField.setText(String.valueOf(commande.getMontantTotal()));
    }
    



    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    // Called when the user clicks Valider.
    @FXML
    private void handleValider() {
        if (isInputValid()) {
            // Update the Commande object with user input
            commande.setPayee(EtatComboBox.getValue().equals("Payée"));
            // Set other properties similarly

            validerClicked = true;
            dialogStage.close();
        }
    }

    // Validates the user input in the text fields.
    private boolean isInputValid() {
        String errorMessage = "";
        if (ClientField.getText() == null || ClientField.getText().length() == 0) {
            errorMessage += "Le Nom Client n'est pas renseigné !\n";
        }

        // Add validation for other fields...

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
