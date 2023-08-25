package com.gd.controller;

import com.gd.model.Produit;
import com.gd.service.RevenueTrackingService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.time.LocalDate;

public class ProductRevenueController {

	@FXML
    private Label dailyTotalLabel;

    private Produit selectedProduct;
    private RevenueTrackingService revenueTrackingService;

    public void init(Produit product, RevenueTrackingService service) {
        selectedProduct = product;
        revenueTrackingService = service;
        
        // Set calculated revenue data directly to the Label
        double dailyRevenue = revenueTrackingService.calculateDailyRevenueForProduct(selectedProduct, LocalDate.now());
        dailyTotalLabel.setText(String.valueOf(dailyRevenue));
    }

    // Add more methods and logic as needed
}
