package com.gd.service;

import com.gd.model.Produit;

import java.time.LocalDate;

public interface RevenueTrackingService {
    double calculateDailyRevenueForProduct(Produit product, LocalDate date);
    double calculateWeeklyRevenueForProduct(Produit product, LocalDate startDate, LocalDate endDate);
    double calculateMonthlyRevenueForProduct(Produit product, int year, int month);
}
