package com.gd.controller;

import java.awt.Image;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

import com.gd.db.UMSDBException;
import com.gd.model.Chef;
import com.gd.model.Commande;
import com.gd.model.Developpeur;
import com.gd.model.Produit;
import com.gd.run.GDApplication;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class DeveloppeurUIController {
	@FXML
	private TableView<Commande> commandeTable;
	@FXML
	private TableColumn<Commande, Integer> idCommandeColumn;
	@FXML
	private TableColumn<Commande, String> produitCommandeColumn;
	@FXML
	private TableColumn<Commande, Integer> quantiteColumn;
	@FXML
	private TableColumn<Commande, String> dateCommandeColumn;
	@FXML
	private TableColumn<Commande, Float> montantTotalColumn;
	@FXML
	private TableColumn<Commande, String> etatPaiementColumn;
	@FXML
	private TableColumn<Commande, String>ProduitColumn;

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
	private TableColumn<Produit, Double> PrixColumn;
	
	@FXML
	private TableColumn<Produit, String>EtatProduiColumn;
	
	

	@FXML
	private MenuButton MenuButtonField;
	
	@FXML
	private Button btnproduit;

	@FXML
	private TextField rechercherField;


	private Developpeur user;
	@SuppressWarnings("unused")
	private Stage dialogStage;
	
	//private ObservableList<Produit> incidentsDev = FXCollections.observableArrayList();


	public Developpeur getUser() {
		return user;
	}

	public void setUser(Developpeur user) {
		this.user = user;
		MenuButtonField.setText(user.getLogin());
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	
	
	@FXML
	private void initialize(){
//		// Initialise la table des commandes
        idCommandeColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<Integer>(cellData.getValue().getId()));
        //produitCommandeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProduit().getIntitule()));
        //quantiteColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getQuantite()));
        quantiteColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getQuantite()).asObject());
        dateCommandeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDateCommande().toString()));
        etatPaiementColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().isPayee() ));
        montantTotalColumn.setCellValueFactory(cellData -> new SimpleFloatProperty(cellData.getValue().getMontantTotal()).asObject());
        //ProduitColumn.setCellValueFactory(cellData -> new SimpleFloatProperty(cellData.getValue().setProduitc()));
        ProduitColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProduitc()));
        
        commandeTable.setItems(GDApplication.getInstance().getDataSource().getCommandes());
		
		addChangeListener();

		rechercher();

	}
	
//	@FXML
//	private void initialize() {
//	    // Initialise la table des commandes
//	    idCommandeColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
//	    produitCommandeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProduit().getIntitule()));
//	    quantiteColumn.setCellValueFactory(new PropertyValueFactory<>("quantite"));
//	    dateCommandeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDateCommande().toString()));
//	    montantTotalColumn.setCellValueFactory(new PropertyValueFactory<>("montantTotal"));
//	    etatPaiementColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().isPayee() ? "Payée" : "Non payée"));
//	    
//	    // Assurez-vous que la source de données renvoie une liste observée d'objets Commande
//	    ObservableList<Commande> commandes = GDApplication.getInstance().getDataSource().getCommandes();
//	    
//	    commandeTable.setItems(commandes);
//	}

	
	
	@SuppressWarnings("static-access")
	@FXML
	private void handleNouveaucommande() {
		Commande commande = new Commande();
		commande.setDeveloppeur(this.getUser());

		boolean validerClicked = GDApplication.getInstance().showCommandeEditUI(commande);
		if (validerClicked) {
			Long developpeur =user.getId();
			Long chef = (long) 0;			
			try {
				GDApplication.getInstance().getDataSource().AddCommande(commande);
				GDApplication.getInstance().getUtilitaire().displayNotification("Commande créé avec succès !");
				int id = idCommande();
			} catch (UMSDBException e) {
				// TODO Auto-generated catch block
				GDApplication.getInstance().getUtilitaire().displayErrorMessage("Error : " + e.getMessage());
			}
		}
	}
	
	

	
	/**
	* Surveille les changements sur la table et affiche les informations dans le formulaire
	*/
	
	private void addChangeListener() {
	//	IncidentTable.getSelectionModel().selectedItemProperty().addListener(

		      //  (observable, oldValue, newValue) -> displayNoteMsDetails(newValue));

		}
	





	private void rechercher() {

		FilteredList<Commande> filteredData = new FilteredList<>(
				GDApplication.getInstance().getDataSource().getCommandes(), b -> true);

		rechercherField.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(incident -> {

				if (newValue == null || newValue.isEmpty()) {
					return true;
				}

				String lowerCaseFilter = newValue.toLowerCase();

				if (incident.getProduit().getIntitule().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				}
				if (Long.valueOf(user.getId()).toString().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				}
				return false;

			});
		});

		SortedList<Commande> sortedData = new SortedList<>(filteredData);

		sortedData.comparatorProperty().bind(commandeTable.comparatorProperty());
		commandeTable.setItems(sortedData);

	}
	
	@FXML
	private void ChangePassword() {

		GDApplication.getInstance().initChangePassword();

	}


	
    @FXML
    private void openDeveloppeur2UI(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../ui/DeveloppeurProduitUI.fxml"));
            BorderPane page = (BorderPane) loader.load();
            // Créer une nouvelle scène et afficher la nouvelle interface utilisateur
            Scene scene = new Scene(page);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();

            // Vous pouvez également fermer la scène actuelle si nécessaire
            Stage currentStage = (Stage) btnproduit.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public int idCommande() {
		 ObservableList<Commande> commandes = FXCollections.observableArrayList();
		 Commande commande = null;
		 commandes= GDApplication.getInstance().getDataSource().getCommandes();
		 commande = commandes.get(commandes.size()-1);
		int id_commande = commande.getId();	
		return id_commande;
	}
	

}
