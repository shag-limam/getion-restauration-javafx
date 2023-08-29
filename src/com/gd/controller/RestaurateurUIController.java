package com.gd.controller;

import java.awt.Image;
import java.awt.Label;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import com.gd.db.UMSDBException;
import com.gd.db.dao.CommandeDaoImpl;
import com.gd.db.dao.IDaoCommandImpl;
import com.gd.db.dao.IDaoImpl;
import com.gd.db.dao.ProduitDaoImpl;
import com.gd.model.Chef;
import com.gd.model.Commande;
import com.gd.model.Restaurateur;
import com.gd.model.Produit;
import com.gd.run.GDApplication;
import com.gd.service.RevenueTrackingService;

import javafx.beans.property.SimpleDoubleProperty;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

import javafx.scene.control.MenuButton;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import javafx.stage.Stage;

//import org.springframework.beans.factory.annotation.Autowired;



public class RestaurateurUIController {

	
	//@Autowired
    private RevenueTrackingService revenueTrackingService;
	@FXML
	private Label dailyTotalLabel;

	private Produit selectedProduct;
	
	@FXML
	private TableView<Commande> commandeTable;
	@FXML
	private TableColumn<Commande, String> produitCommandeColumn;
	@FXML
	private TableColumn<Commande, String> nomClientColumn;
	@FXML
	private TableColumn<Commande, Integer> quantiteColumn;
	@FXML
	private TableColumn<Commande, String> dateCommandeColumn;
	@FXML
	private TableColumn<Commande, Double> montantTotalColumn;
	@FXML
	private TableColumn<Commande, String> etatPaiementColumn;
	@FXML
	private TableColumn<Commande, String>ProduitColu;
	@FXML
	private TableColumn<Commande, String> quantitesProduitsColumn;

	private Commande commande;
	private IDaoCommandImpl commandeDAO;
    private IDaoImpl<Produit> produitDAO;
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
	private Button btnpro;
	
	@FXML
	private Button btnproduit;
	
	@FXML
	private Button btncommande;
	@FXML
	private Button btnmodi;

	@FXML
	private TextField rechercherField;


	private Restaurateur user;
	@SuppressWarnings("unused")
	private Stage dialogStage;
	
	//private ObservableList<Produit> incidentsDev = FXCollections.observableArrayList();


	public Restaurateur getUser() {
		return user;
	}

	public void setUser(Restaurateur user) {
		this.user = user;
		MenuButtonField.setText(user.getLogin());
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	
	
	@FXML
	private void initialize() {

		 
		ProduitColu.setCellValueFactory(cellData -> {
		        List<Produit> produits = cellData.getValue().getProduits(); // Utilisez la méthode getProduits() pour obtenir la liste de produits pour une commande
		        StringBuilder produitNames = new StringBuilder();

		        for (Produit produit : produits) {
		            produitNames.append(produit.getIntitule()).append(", ");
		        }

		        return new SimpleStringProperty(produitNames.toString());
		    });

		    // Le reste de votre initialisation...
		
		
		quantitesProduitsColumn.setCellValueFactory(cellData -> {
	        List<Integer> quantitesProduits = cellData.getValue().getQuantitesProduits();
	        StringBuilder quantites = new StringBuilder();

	        for (int quantite : quantitesProduits) {
	            quantites.append(quantite).append(", ");
	        }

	        return new SimpleStringProperty(quantites.toString());
	    });
	    // Initialise the Commande table
		//quantiteColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getQuantite()).asObject());
	    dateCommandeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDateCommande().toString()));
	    etatPaiementColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().isPayee()));
	    //nomClientColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNomClient()));
	    nomClientColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNomClient()));
	    montantTotalColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getMontantTotal()).asObject());

	    // Assuming commandeTable is properly associated with the corresponding TableView in FXML
	    commandeTable.setItems(GDApplication.getInstance().getDataSource().getCommandes());
	    
	   // commandeTable.getColumns().addAll(produitCommandeColumn, quantiteColumn, quantitesProduitsColumn, dateCommandeColumn, montantTotalColumn, etatPaiementColumn);


	    // Initialise the Produit table
	    
	    addChangeListener();

	    rechercher();
	}

	public RestaurateurUIController() {
    	produitDAO=new ProduitDaoImpl();
    	commandeDAO=new CommandeDaoImpl();
    }

	// Cette méthode est appelée lorsque vous sélectionnez un produit dans le TableView
    @FXML
    private void onProduitSelected() {
        selectedProduct = ProduitTable.getSelectionModel().getSelectedItem();
    }
	
	@FXML
	private void handleModifierEtat() {

		Commande selectedIncident = commandeTable.getSelectionModel().getSelectedItem();
		int selectedIndex = commandeTable.getSelectionModel().getSelectedIndex();

		if (selectedIncident != null) {
			boolean validerClicked = GDApplication.getInstance().showModifierEtatUI(selectedIncident);
			if (validerClicked) {

				try {
					GDApplication.getInstance().getDataSource().UpdateCommande(selectedIncident, selectedIndex);
					GDApplication.getInstance().getUtilitaire().displayNotification(" commande update avec succès !");
				} catch (UMSDBException e) {
					// TODO Auto-generated catch block
					GDApplication.getInstance().getUtilitaire().displayErrorMessage("Error : " + e.getMessage());
				}

				commandeTable.refresh();

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
	private void afficherCommandesPayees() {
	    ObservableList<Commande> payedCommandes = FXCollections.observableArrayList();

	    for (Commande commande : commandeTable.getItems()) {
	        if (commande.isPayee().equals("Payée")) {
	            payedCommandes.add(commande);
	        }
	    }

	    commandeTable.setItems(payedCommandes);
	}
	
	@FXML
    private void openProduitUI(ActionEvent event) {
		//Restaurateur restaurateur = new Restaurateur();
		Chef repp = new Chef();
		GDApplication.getInstance().initDevLayout(repp);
      Stage currentStage =(Stage) btnpro.getScene().getWindow();
      closeWindow();
      currentStage.close();
      
	}

	private void closeWindow() {
        Stage stage = (Stage) commandeTable.getScene().getWindow();
        stage.close();
    }

	@FXML
	private void imprimerCommandeSelectionnee() {
	    Commande selectedCommande = commandeTable.getSelectionModel().getSelectedItem();

	    if (selectedCommande != null) {
	        try {
	            PDDocument document = new PDDocument();
	            PDPage page = new PDPage();
	            document.addPage(page);
	            PDPageContentStream contentStream = new PDPageContentStream(document, page);

	            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
	            contentStream.beginText();
	            contentStream.newLineAtOffset(50, 700);
	            contentStream.showText("Informations de la commande sélectionnée:");
	            contentStream.newLine();
	            contentStream.showText("Date de commande: " + selectedCommande.getDateCommande());
	            contentStream.newLine();
	            contentStream.showText("Montant total: " + selectedCommande.getMontantTotal());
	            // Ajoutez d'autres informations de la commande ici...
	            contentStream.endText();

	            contentStream.close();

	            // Sauvegarde du document PDF
	            String fileName = "commande_" + selectedCommande.getId() + ".pdf";
	            document.save(fileName);
	            document.close();

	            System.out.println("PDF généré et enregistré sous : " + fileName);
	        } catch (IOException e) {
	            e.printStackTrace();
	            // Gérez les erreurs liées à la génération de PDF ici
	        }
	    } else {
	        // Aucune commande sélectionnée, affichez un message d'alerte
	        Alert alert = new Alert(Alert.AlertType.WARNING);
	        alert.setTitle("Aucune sélection");
	        alert.setHeaderText("Aucune commande n'a été sélectionnée !");
	        alert.setContentText("Veuillez choisir une commande à imprimer en PDF.");
	        alert.showAndWait();
	    }
	}


	


	
	
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
	


	  private void showAlert(String title, String message) {
	        Alert alert = new Alert(Alert.AlertType.INFORMATION);
	        alert.setTitle(title);
	        alert.setHeaderText(null);
	        alert.setContentText(message);
	        alert.showAndWait();
	    }

	@FXML
	private void handleNouvelleCommande() {
	    try {
	    	FXMLLoader loader = new FXMLLoader();
	    	loader.setLocation(GDApplication.class.getResource("../ui/SaisieCommandeUI.fxml"));
	        Parent commandeUI = loader.load();
	        
//	        FXMLLoader loader = new FXMLLoader();
//			loader.setLocation(GDApplication.class.getResource("../ui/CreerCommandeUI.fxml"));
//			AnchorPane page = (AnchorPane) loader.load();
	        
	        Stage stage = new Stage();
	        stage.setTitle("Ajouter Commande");
	        stage.setScene(new Scene(commandeUI));
	        stage.setOnHiding(event -> {
	            try {
	                // Rechargez les commandes depuis la base de données après la fermeture de la fenêtre de saisie
	                 loadCommandes();
	            } catch (Exception e) {
	                e.printStackTrace();
	                // Gérez l'erreur ici selon vos besoins
	            }
	        });

	        stage.show();
	    } catch (IOException e) {
	        e.printStackTrace(); // Ajoutez un point-virgule ici
	        // Gérez l'erreur ici selon vos besoins
	    }
	}


	public Commande getSelectedCommande() {
	    return commandeTable.getSelectionModel().getSelectedItem();
	}

	private void loadCommandes() throws UMSDBException {
        List<Commande> commandes = commandeDAO.list();
        ObservableList<Commande> commandeList = FXCollections.observableArrayList(commandes);
        commandeTable.setItems(commandeList);
	}
	
	private void rechercher() {

		FilteredList<Commande> filteredData = new FilteredList<>(
				GDApplication.getInstance().getDataSource().getCommandes(), b -> true);

		rechercherField.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(commande -> {

				if (newValue == null || newValue.isEmpty()) {
					return true;
				}

				String lowerCaseFilter = newValue.toLowerCase();

				if (commande.getNomClient().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				}if (commande.isPayee().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				}
				if (String.valueOf(commande.getQuantitesProduits()).toLowerCase().indexOf(lowerCaseFilter) != -1) {
				    return true;
				}
				if (String.valueOf(commande.getProduits()).toLowerCase().indexOf(lowerCaseFilter) != -1) {
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
    private void openCommandeUI(ActionEvent event) {
		Restaurateur repp = new Restaurateur();
		GDApplication.getInstance().initDevelopeurLayout(repp);
      Stage currentStage = (Stage) btnproduit.getScene().getWindow();
      currentStage.close();
      closeWindow();
	}
	
    @FXML
    private void openDeveloppeur2UI(ActionEvent event) {
    	Chef repp = new Chef();
		GDApplication.getInstance().initRapporteurLayout1(repp);
      Stage currentStage = (Stage) btnproduit.getScene().getWindow();
      currentStage.close();

    }
    
    public int idCommande() {
		 ObservableList<Commande> commandes = FXCollections.observableArrayList();
		 Commande commande = null;
		 commandes= GDApplication.getInstance().getDataSource().getCommandes();
		 commande = commandes.get(commandes.size()-1);
		int id_commande = commande.getId();	
		return id_commande;
	}
	
    
   
   
    
    // Cette méthode affiche la vue de suivi des recettes
    @FXML
    private void showProductRevenueView() {
        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("../ui/ProductRevenue.fxml"));
//            TabPane tabPane = loader.load();
            
            FXMLLoader loader = new FXMLLoader();
	    	//loader.setLocation(GDApplication.class.getResource("../ui/DeveloppeurUI.fxml"));
            loader.setLocation(getClass().getResource("/com/gd/ui/DeveloppeurUI.fxml"));

	        Parent commandeUI = loader.load();

            // Create an instance of the controller
            ProductRevenueController productRevenueController = loader.getController();

            // Pass the selectedProduct and revenueTrackingService to the init method
            productRevenueController.init(selectedProduct, revenueTrackingService);

            Scene scene = new Scene(commandeUI);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Suivi des recettes pour le produit");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }
    }


 
}
