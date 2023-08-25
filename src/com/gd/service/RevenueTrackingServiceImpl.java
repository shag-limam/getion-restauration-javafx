package com.gd.service;

import com.gd.model.Commande;
import com.gd.model.Produit;

import java.time.LocalDate;

public class RevenueTrackingServiceImpl implements RevenueTrackingService {

	   //methode de filltrage
    public double calculateDailyRevenueForProduct(Produit product, LocalDate date) {
        double totalRevenue = 0.0;
        
        for (Commande commande : product.getCommandes()) {
            LocalDate commandeDate = LocalDate.parse(commande.getDateCommande());
            
            if (commandeDate.equals(date) && "Payée".equalsIgnoreCase(commande.isPayee())) {
                totalRevenue += commande.getMontantTotal();
            }
        }
        
        return totalRevenue;
    }

    public double calculateWeeklyRevenueForProduct(Produit product, LocalDate startDate, LocalDate endDate) {
        double totalRevenue = 0.0;
        
        for (Commande commande : product.getCommandes()) {
            LocalDate commandeDate = LocalDate.parse(commande.getDateCommande());
            
            if (commandeDate.isAfter(startDate.minusDays(1)) && commandeDate.isBefore(endDate.plusDays(1)) &&
                "Payée".equalsIgnoreCase(commande.isPayee())) {
                totalRevenue += commande.getMontantTotal();
            }
        }
        
        return totalRevenue;
    }


    public double calculateMonthlyRevenueForProduct(Produit product, int year, int month) {
        double totalRevenue = 0.0;
        
        for (Commande commande : product.getCommandes()) {
            LocalDate commandeDate = LocalDate.parse(commande.getDateCommande());
            
            if (commandeDate.getYear() == year && commandeDate.getMonthValue() == month &&
                "Payée".equalsIgnoreCase(commande.isPayee())) {
                totalRevenue += commande.getMontantTotal();
            }
        }
        
        return totalRevenue;
    }
}
