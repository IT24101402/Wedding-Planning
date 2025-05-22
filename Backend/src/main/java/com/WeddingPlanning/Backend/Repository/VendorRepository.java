package com.WeddingPlanning.Backend.Repository;

import com.WeddingPlanning.Backend.Model.Vendor;
import com.WeddingPlanning.Backend.DataStructure.VendorLinkedList;
import org.springframework.stereotype.Repository;

import java.io.*;

@Repository
public class VendorRepository {
    private final String saveFilePath = "src/main/resources/static/vendors.txt";
    private final String resourceFileName = "static/vendors.txt";

    public VendorLinkedList loadVendors() {
        VendorLinkedList list = new VendorLinkedList();

        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(resourceFileName);
            if (inputStream == null) throw new FileNotFoundException(resourceFileName + " not found in resources!");

            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 7) {
                    Vendor v = new Vendor(
                            Long.parseLong(parts[0]), parts[1], parts[2],
                            Double.parseDouble(parts[3]), parts[4], parts[5], parts[6]
                    );
                    list.add(v);
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }

    public void saveVendors(VendorLinkedList vendors) {
        try {
            File file = new File(saveFilePath);
            file.getParentFile().mkdirs();

            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            for (Vendor v : vendors.toList()) {
                bw.write(v.toFileString());
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}