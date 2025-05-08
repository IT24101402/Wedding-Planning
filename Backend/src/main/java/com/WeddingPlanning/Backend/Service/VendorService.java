package com.WeddingPlanning.Backend.Service;

import com.WeddingPlanning.Backend.Model.Vendor;
import com.WeddingPlanning.Backend.Repository.VendorRepository;
import org.springframework.stereotype.Service;
import java.util.LinkedList;
import java.util.List;

@Service
public class VendorService {
    private LinkedList<Vendor> vendors = new LinkedList<>();
    private final VendorRepository repository;

    public VendorService(VendorRepository repository) {
        this.repository = repository;
        this.vendors = repository.loadVendors();
    }

    public void addVendor(Vendor vendor) {
        vendors.add(vendor);
        repository.saveVendors(vendors);
    }

    public List<Vendor> getAllVendors() {
        return vendors;
    }

    public Vendor getVendorById(Long id) {
        for (Vendor v : vendors) {
            if (v.getId().equals(id)) return v;
        }
        return null;
    }

    public void updateVendor(Long id, Vendor updated) {
        for (int i = 0; i < vendors.size(); i++) {
            if (vendors.get(i).getId().equals(id)) {
                vendors.set(i, updated);
                break;
            }
        }
        repository.saveVendors(vendors);
    }

    public void deleteVendor(Long id) {
        vendors.removeIf(v -> v.getId().equals(id));
        repository.saveVendors(vendors);
    }

    public void sortVendorsByPrice(boolean ascending) {
        for (int i = 0; i < vendors.size() - 1; i++) {
            for (int j = 0; j < vendors.size() - i - 1; j++) {
                boolean condition = ascending ? vendors.get(j).getPrice() > vendors.get(j + 1).getPrice() :
                        vendors.get(j).getPrice() < vendors.get(j + 1).getPrice();
                if (condition) {
                    Vendor temp = vendors.get(j);
                    vendors.set(j, vendors.get(j + 1));
                    vendors.set(j + 1, temp);
                }
            }
        }
    }
}