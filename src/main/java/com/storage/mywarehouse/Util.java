/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.storage.mywarehouse;

import com.storage.mywarehouse.Entity.Product;
import com.storage.mywarehouse.Hibernate.NewHibernateUtil;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;

/**
 *
 * @author bojan
 */
public class Util {
    
    private enum ProductHeader {
        BRAND,TYPE,DESCRIPTION,PRICE
    }
    
    public static String parseProducts(File file) throws IOException{
        CSVParser csvParser = CSVParser.parse(file, StandardCharsets.UTF_8, CSVFormat.EXCEL.withHeader(ProductHeader.class));
        Map<String,Integer> header = csvParser.getHeaderMap();
        int numberOfSuccessfulRows = 0;
        int numberOfFailedRows = 0;
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        for(CSVRecord record : csvParser.getRecords()){
            String brand = record.get(ProductHeader.BRAND);
            String type = record.get(ProductHeader.TYPE);
            String description = record.get(ProductHeader.DESCRIPTION);
            double price = Double.parseDouble(record.get(ProductHeader.PRICE));
            if("".equals(brand) || "".equals(type)){
                numberOfFailedRows++;
                continue;
            }
            int productId;
            Transaction tx = session.beginTransaction();
            Product productWithHighestId = (Product) session.createCriteria(Product.class).addOrder(Order.desc("productId")).setMaxResults(1).uniqueResult();
            tx.commit();
            if(productWithHighestId == null){
                productId = 0;
            }
            else{
                productId = productWithHighestId.getProductId();
            }
            Product p = new Product(++productId, brand, type,description, price);
            tx = session.beginTransaction();
            session.save(p);
            tx.commit();
            numberOfSuccessfulRows++;
        }
        session.close();
        return "Number of successfully inserted rows: "+numberOfSuccessfulRows+"\n Number of erronous rows: "+numberOfFailedRows;
    }
}
