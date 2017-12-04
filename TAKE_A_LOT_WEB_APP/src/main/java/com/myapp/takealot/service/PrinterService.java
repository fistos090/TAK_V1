/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myapp.takealot.service;

import com.myapp.takealot.entity.ClientOrder;
import com.myapp.takealot.entity.LineProduct;
import com.myapp.takealot.entity.Product;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;

/**
 *
 * @author MANDELACOMP3
 */
@Service
public class PrinterService {
    
    public String printInvoice(List<ClientOrder> clientsOrders,List<Product> allStoreProducts) throws IOException {

        String message = "";
        
        if(clientsOrders.size() > 0){
                File file = new File("D:/TAKE_A_LOT_ORDERS_INVOICE.csv");

                BufferedWriter writer = new BufferedWriter(new FileWriter(file.getAbsoluteFile()));

                writer.write("ORDER NUMBER,BELONG TO (CUSTOMER NUMBER),ORDER DATE");
                writer.newLine();

                

                    for (int i = 0; i < clientsOrders.size(); i++) {
                        ClientOrder clientOrder = clientsOrders.get(i);

                        String data = clientOrder.getId() + "," + clientOrder.getUserID() + "," + clientOrder.getClientOrderDate();
                        writer.write(data);
                        writer.newLine();

                        List<LineProduct> lineProducts = clientOrder.getLineProducts();
                        double totalPrice = 0;
                        int totQuantity = 0;
                        String productsInfo = ",PRODUCT,CATEGORY,DESCRIPTION,QUANTITY,PRODUCT PRICE,SUB TOTAL \n";

                        for (int j = 0; j < lineProducts.size(); j++) {
                            LineProduct lineProduct = lineProducts.get(j);

                            for (int y = 0; y < allStoreProducts.size(); y++) {

                                if (allStoreProducts.get(y).getId() == lineProduct.getProductId()) {

                                    double subTot = allStoreProducts.get(y).getPrice() * lineProduct.getQuantity();
                                    totalPrice += subTot;
                                    totQuantity += lineProduct.getQuantity();
                                    
                                    productsInfo += ",,"+allStoreProducts.get(y).getCategory()+"," + allStoreProducts.get(y).getProductDesc() + "," 
                                            + lineProduct.getQuantity() + ",R" + allStoreProducts.get(y).getPrice() + ",R" + subTot + "\n";

                                }

                            }
                        }

                        productsInfo += ",,,TOTAL,"+totQuantity+",,R" + totalPrice + "\n";

                        writer.write(productsInfo);
                        writer.newLine();

                    }
          

                writer.close();

                    message = "Order Report for "
                        + clientsOrders.size() + " orders is successfully printed. The file is under the following path :"+file.getPath();
                    
                }else{
                    
                    message = "Order Report for "
                        + clientsOrders.size() + " orders is Unsuccessfully";
                }
                    

            return message;
        }

    
}
