package com.WeddingPlanning.Backend.Model;

public class Bill extends Quotation {
    private boolean isFinalized;
    private boolean isPaid;

    public void finalizeBill() {
        this.isFinalized = true;
    }

    public void payBill() {
        this.isPaid = true;
    }

    public boolean isFinalized() {
        return isFinalized;
    }

    public boolean isPaid() {
        return isPaid;
    }
}