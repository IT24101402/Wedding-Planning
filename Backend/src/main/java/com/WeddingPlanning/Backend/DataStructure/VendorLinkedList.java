package com.WeddingPlanning.Backend.DataStructure;

import com.WeddingPlanning.Backend.Model.Vendor;
import java.util.ArrayList;
import java.util.List;

public class VendorLinkedList {
    private VendorNode head;

    public void add(Vendor vendor) {
        VendorNode newNode = new VendorNode(vendor);
        if (head == null) {
            head = newNode;
        } else {
            VendorNode current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
    }

    public void removeById(Long id) {
        if (head == null) return;

        if (head.data.getId().equals(id)) {
            head = head.next;
            return;
        }

        VendorNode current = head;
        while (current.next != null) {
            if (current.next.data.getId().equals(id)) {
                current.next = current.next.next;
                return;
            }
            current = current.next;
        }
    }

    public void update(Long id, Vendor updatedVendor) {
        VendorNode current = head;
        while (current != null) {
            if (current.data.getId().equals(id)) {
                current.data = updatedVendor;
                return;
            }
            current = current.next;
        }
    }

    public Vendor findById(Long id) {
        VendorNode current = head;
        while (current != null) {
            if (current.data.getId().equals(id)) {
                return current.data;
            }
            current = current.next;
        }
        return null;
    }

    public Vendor findByEmail(String email) {
        VendorNode current = head;
        while (current != null) {
            if (current.data.getEmail().equals(email)) {
                return current.data;
            }
            current = current.next;
        }
        return null;
    }

    public List<Vendor> toList() {
        List<Vendor> list = new ArrayList<>();
        VendorNode current = head;
        while (current != null) {
            list.add(current.data);
            current = current.next;
        }
        return list;
    }

    public int size() {
        int count = 0;
        VendorNode current = head;
        while (current != null) {
            count++;
            current = current.next;
        }
        return count;
    }

    public void sortByPrice(boolean ascending) {
        if (head == null) return;

        for (VendorNode i = head; i != null; i = i.next) {
            for (VendorNode j = i.next; j != null; j = j.next) {
                boolean condition = ascending ? i.data.getPrice() > j.data.getPrice() :
                        i.data.getPrice() < j.data.getPrice();
                if (condition) {
                    Vendor temp = i.data;
                    i.data = j.data;
                    j.data = temp;
                }
            }
        }
    }
}
