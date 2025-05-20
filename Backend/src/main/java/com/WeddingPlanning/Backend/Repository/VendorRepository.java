package com.WeddingPlanning.Backend.Repository;

import com.WeddingPlanning.Backend.Model.Vendor;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.LinkedList;

@Repository
public class VendorRepository {

    // File to save data - outside resources so we can write to it
    private final String saveFilePath = "src/main/resources/static/vendors.txt";

    // File to load from - inside resources
    private final String resourceFileName = "static/vendors.txt";

    public LinkedList<Vendor> loadVendors() {
        LinkedList<Vendor> list = new LinkedList<>();

        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(resourceFileName);
            if (inputStream == null) {
                throw new FileNotFoundException(resourceFileName + " not found in resources!");
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            String line;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 7) {
                    Vendor v = new Vendor(
                            Long.parseLong(parts[0]),
                            parts[1],
                            parts[2],
                            Double.parseDouble(parts[3]),
                            parts[4],
                            parts[5],
                            parts[6]
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

    public void saveVendors(LinkedList<Vendor> vendors) {
        try {
            // Make sure directory exists
            File file = new File(saveFilePath);
            file.getParentFile().mkdirs();

            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            for (Vendor v : vendors) {
                bw.write(v.toFileString());
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}