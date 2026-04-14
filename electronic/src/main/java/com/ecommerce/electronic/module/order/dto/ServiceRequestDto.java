package com.ecommerce.electronic.module.order.dto;

public class ServiceRequestDto {

    private Long userId;
    private Long warrantyId;
    private String issueDescription;

    public ServiceRequestDto() {
    }

    public Long getUserId() {
        return userId;
    }

    public Long getWarrantyId() {
        return warrantyId;
    }

    public String getIssueDescription() {
        return issueDescription;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setWarrantyId(Long warrantyId) {
        this.warrantyId = warrantyId;
    }

    public void setIssueDescription(String issueDescription) {
        this.issueDescription = issueDescription;
    }
}