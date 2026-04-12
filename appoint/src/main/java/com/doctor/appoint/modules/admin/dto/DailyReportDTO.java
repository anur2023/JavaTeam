package com.doctor.appoint.modules.admin.dto;

public class DailyReportDTO {

    private Long totalAppointments;
    private Long onlineAppointments;
    private Long offlineAppointments;

    public DailyReportDTO() {
    }

    public DailyReportDTO(Long totalAppointments, Long onlineAppointments, Long offlineAppointments) {
        this.totalAppointments = totalAppointments;
        this.onlineAppointments = onlineAppointments;
        this.offlineAppointments = offlineAppointments;
    }

    public Long getTotalAppointments() {
        return totalAppointments;
    }

    public void setTotalAppointments(Long totalAppointments) {
        this.totalAppointments = totalAppointments;
    }

    public Long getOnlineAppointments() {
        return onlineAppointments;
    }

    public void setOnlineAppointments(Long onlineAppointments) {
        this.onlineAppointments = onlineAppointments;
    }

    public Long getOfflineAppointments() {
        return offlineAppointments;
    }

    public void setOfflineAppointments(Long offlineAppointments) {
        this.offlineAppointments = offlineAppointments;
    }
}