package com.WeddingPlanning.Backend.DataStructure;

import com.WeddingPlanning.Backend.Model.Vendor;

public class VendorNode {
    public Vendor data;
    public VendorNode next;

    public VendorNode(Vendor data) {
        this.data = data;
        this.next = null;
    }
}
