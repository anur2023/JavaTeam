package com.doctor.appoint.modules.admin.dto;

public class RevenueReportDTO {

    private Double totalRevenue;
    private Double onlineRevenue;
    private Double offlineRevenue;

    public RevenueReportDTO() {
    }

    public RevenueReportDTO(Double totalRevenue, Double onlineRevenue, Double offlineRevenue) {
        this.totalRevenue = totalRevenue;
        this.onlineRevenue = onlineRevenue;
        this.offlineRevenue = offlineRevenue;
    }

    public Double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(Double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public Double getOnlineRevenue() {
        return onlineRevenue;
    }

    public void setOnlineRevenue(Double onlineRevenue) {
        this.onlineRevenue = onlineRevenue;
    }

    public Double getOfflineRevenue() {
        return offlineRevenue;
    }

    public void setOfflineRevenue(Double offlineRevenue) {
        this.offlineRevenue = offlineRevenue;
    }
}