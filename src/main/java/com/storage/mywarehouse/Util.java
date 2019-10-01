/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.storage.mywarehouse;

import com.storage.mywarehouse.Dao.ProductDAO;
import com.storage.mywarehouse.Dao.QuantityHistoryViewDAO;
import com.storage.mywarehouse.Dao.WarehouseDAO;
import com.storage.mywarehouse.Entity.Product;
import com.storage.mywarehouse.Entity.Warehouse;
import com.storage.mywarehouse.View.QuantityHistoryView;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author bojan, Patroklos
 */
public class Util {

    private enum ProductHeader {
        BRAND, TYPE, DESCRIPTION, PRICE
    }

    private enum WarehouseHeader {
        NAME
    }

    public static String parseProducts(File file) throws IOException {
        CSVParser csvParser = CSVParser.parse(file, StandardCharsets.UTF_8, CSVFormat.EXCEL.withHeader(ProductHeader.class));
        int numberOfSuccessfulRows = 0;
        int numberOfFailedRows = 0;
        for (CSVRecord record : csvParser.getRecords()) {
            Product product = parseProduct(record);
            if (product == null) {
                numberOfFailedRows++;
            } else {
                numberOfSuccessfulRows++;
            }
        }
        return "Number of successfully inserted rows: " + numberOfSuccessfulRows + "\n Number of erronous rows: " + numberOfFailedRows;
    }

    private static Product parseProduct(CSVRecord record) {
        String brand = record.get(ProductHeader.BRAND);
        String type = record.get(ProductHeader.TYPE);
        if ("".equals(brand) || "".equals(type)) {
            return null;
        }
        if (ProductDAO.findByBrandAndName(brand, type) != null) {
            System.out.println("Product of brand " + brand + " and type " + type + " already exists!");
            return null;
        }

        String description = record.get(ProductHeader.DESCRIPTION);
        double price = Double.parseDouble(record.get(ProductHeader.PRICE));
        Product product = new Product(brand, type, description, price);
        return ProductDAO.save(product);
    }

    public static String parseWarehouses(File file, mainframe frame) throws IOException {
        CSVParser csvParser = CSVParser.parse(file, StandardCharsets.UTF_8, CSVFormat.EXCEL.withHeader(WarehouseHeader.class));
        int numberOfSuccessfulRows = 0;
        int numberOfFailedRows = 0;
        for (CSVRecord record : csvParser.getRecords()) {
            String name = record.get(WarehouseHeader.NAME);
            if ("".equals(name)) {
                numberOfFailedRows++;
                continue;
            }

            if (addWarehouse(name, frame) < 0) {
                numberOfFailedRows++;
            } else {
                numberOfSuccessfulRows++;
            }

        }
        return "Number of successfully inserted rows: " + numberOfSuccessfulRows + "\n Number of erronous rows: " + numberOfFailedRows;
    }

    public static void exportQuantityHistory(File file) {
        try (PrintWriter out = new PrintWriter(new FileWriter(file))) {
            out.println("Warehouse\tBrand\tType\tDate\tQuantity");
            for (QuantityHistoryView qhv : QuantityHistoryViewDAO.findAll()) {
                out.println(qhv.toString());
            }
        } catch (IOException ex) {
            Logger.getLogger(mainframe.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static int addWarehouse(String name, mainframe frame) {
        if (WarehouseDAO.findByName(name) != null) {
            System.out.println("Warehouse " + name + " already exists!");
            return -1;
        }

        Warehouse warehouse = new Warehouse(name);
        Warehouse w = WarehouseDAO.save(warehouse);

        List<storagepanel> panels = frame.getPanels();
        panels.add(new storagepanel(w, frame));
        frame.getWarehouses().add(w);
        frame.getTabs().add(name, panels.get(panels.size() - 1));
        return 0;
    }
}
