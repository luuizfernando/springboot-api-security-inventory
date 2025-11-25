package project.inventory.domain.order.enums;

public enum OrderStatus {
 
    PENDING("Pending"),
    APPROVED("Approved"),
    CANCELED("Canceled");

    private String status;

    OrderStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }
    
}