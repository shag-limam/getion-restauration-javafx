package com.gd.controller;




import java.awt.Image;
import java.sql.Date;


import com.gd.db.UMSDBException;

import com.gd.model.Produit;
import com.gd.model.Chef;
import com.gd.model.Developpeur;
import com.gd.run.GDApplication;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class ChefUIController {
	
	@FXML
	private TableView<Produit> ProduitTable;
	@FXML
	private TableColumn<Produit, Image> ImageColumn;
	@FXML
	private TableColumn<Produit, String> EtatColumn;
	@FXML
	private TableColumn<Produit, String> DescriptionColumn;
	@FXML
	private TableColumn<Produit, Date> DateColumn;
	@FXML
	private TableColumn<Produit, Long> AssigneColumn;
	@FXML
	private TableColumn<Produit, String> mise_ajourColumn;
	@FXML
	private TableColumn<Produit, String> AppColumn;
	@FXML
	private TableColumn<Produit, Integer> IdColumn;
	@FXML
	private TableColumn<Produit, Integer> quantiteColumn;
	@FXML
	private TableColumn<Produit, Double> PrixColumn;
	
	@FXML
	private TableColumn<Produit, String>EtatProduiColumn;
	
	@FXML
	private MenuButton MenuButtonField;
	
	@FXML
	private TextField rechercherField;
	
	@FXML
	private Button btncommande;
	
	@FXML
	private Button btnproduit;
	
	
	private Chef user;
	@SuppressWarnings("unused")
	private Stage dialogStage;
	
	
	
	
	
	public ChefUIController() {
	
	}
	
	@SuppressWarnings("static-access")
	@FXML
	private void initialize() {
		// Initialise la table des Produit
		
		//DateColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<Date>(cellData.getValue().getDate()));
		quantiteColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getQuantite()).asObject());
		DescriptionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));
		mise_ajourColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOpendate()));
		EtatProduiColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEtat()));
		AppColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIntitule()));
        PrixColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrix()).asObject());
       // ImageColumn.setCellValueFactory(cellData -> cellData.getValue().imageProperty());

		ProduitTable.setItems(GDApplication.getInstance().getDataSource().getproduits());

		addChangeListener();

		rechercher();

	}
	

	
	/**
	* Surveille les changements sur la table et affiche les informations dans le formulaire
	*/
	
	private void addChangeListener() {

	//	ProduitTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> displayNoteMsDetails(newValue));
		}
	
	public Chef getUser() {
		return user;
	}

	public void setUser(Chef user) {
		this.user = user;
		MenuButtonField.setText( user.getLogin());
	}
	
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	
	@SuppressWarnings("static-access")
	@FXML
	private void handleNouveauIncident() {
		Produit produit = new Produit();
		produit.setChef(this.getUser());

		boolean validerClicked = GDApplication.getInstance().showIncidentEditUI(produit);
		if (validerClicked) {
			Long chef =user.getId();
			Long developpeur = (long) 0;			
			try {
				GDApplication.getInstance().getDataSource().Addprodui(produit);
				GDApplication.getInstance().getUtilitaire().displayNotification("Produit créé avec succès !");
				int id = idIncident();
			} catch (UMSDBException e) {
				// TODO Auto-generated catch block
				GDApplication.getInstance().getUtilitaire().displayErrorMessage("Error : " + e.getMessage());
			}
		}
	}
	
	@SuppressWarnings("static-access")
	@FXML
	private void handleModifierIncident() {

		Produit selectedProduit = ProduitTable.getSelectionModel().getSelectedItem();
		int selectedIndex = ProduitTable.getSelectionModel().getSelectedIndex();

		if (selectedProduit != null) {
			boolean validerClicked = GDApplication.getInstance().showIncidentEditUI(selectedProduit);
			if (validerClicked) {

				
				try {
					GDApplication.getInstance().getDataSource().Updateproduit(selectedProduit, selectedIndex);
					GDApplication.getInstance().getUtilitaire().displayNotification("Produit update avec succès !");
					
				} catch (UMSDBException e) {
					// TODO Auto-generated catch block
					GDApplication.getInstance().getUtilitaire().displayErrorMessage("Error : " + e.getMessage());
				}

				ProduitTable.refresh();

			}
		} else {
			// Nothing selected.
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Aucune sélection");
			alert.setHeaderText("Aucun Ticket n'a été sélectionné !");
			alert.setContentText("Veuillez choisir un Ticket svp !.");
			alert.showAndWait();
		}
	}
	
	@FXML
	private void handleSupprimerProduit() {
	    int selectedIndex = ProduitTable.getSelectionModel().getSelectedIndex();

	    if (selectedIndex >= 0) {
	        javafx.scene.control.Alert confirmationAlert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.CONFIRMATION);
	        confirmationAlert.setTitle("Confirmation");
	        confirmationAlert.setHeaderText("Confirmation de suppression");
	        confirmationAlert.setContentText("Êtes-vous sûr de vouloir supprimer ce produit ?");

	        java.util.Optional<javafx.scene.control.ButtonType> result = confirmationAlert.showAndWait();

	        if (result.isPresent() && result.get() == javafx.scene.control.ButtonType.OK) {
	            try {
	                GDApplication.getInstance().getDataSource().DeleteProduit(selectedIndex);
	            } catch (UMSDBException e) {
	                e.printStackTrace();
	            }
	        }
	    } else {
	        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.WARNING);
	        alert.setTitle("Avertissement");
	        alert.setHeaderText("Alerte !");
	        alert.setContentText("Veuillez sélectionner un produit svp !");
	        alert.showAndWait();
	    }
	}


	
	private void rechercher() {

		FilteredList<Produit> filteredData = new FilteredList<>(
				GDApplication.getInstance().getDataSource().getproduits(), b -> true);

		rechercherField.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(incident -> {

				if (newValue == null || newValue.isEmpty()) {
					return true;
				}

				String lowerCaseFilter = newValue.toLowerCase();

				if (incident.getIntitule().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				}
				if (Long.valueOf(user.getId()).toString().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				}
				return false;

			});
		});

		SortedList<Produit> sortedData = new SortedList<>(filteredData);

		sortedData.comparatorProperty().bind(ProduitTable.comparatorProperty());

		ProduitTable.setItems(sortedData);

	}
	
	
	public int idIncident() {
		 ObservableList<Produit> produits = FXCollections.observableArrayList();
		 Produit produit = null;
			produits= GDApplication.getInstance().getDataSource().getproduits();
			produit = produits.get(produits.size()-1);
		int id_produit = produit.getId();	
		return id_produit;
	}
	
	
	
	@FXML
    private void openCommandeUI(ActionEvent event) {
		Developpeur repp = new Developpeur();
		GDApplication.getInstance().initDevelopeurLayout(repp);
      Stage currentStage = (Stage) btnproduit.getScene().getWindow();
      currentStage.close();
	}
	
    @FXML
    private void openProduitUI(ActionEvent event) {
    	Chef repp = new Chef();
		GDApplication.getInstance().initRapporteurLayout1(repp);
      Stage currentStage = (Stage) btnproduit.getScene().getWindow();
      currentStage.close();
    }
	
	@FXML
	private void ChangePassword() {

		GDApplication.getInstance().initChangePassword();

	}
	

	
	

}
