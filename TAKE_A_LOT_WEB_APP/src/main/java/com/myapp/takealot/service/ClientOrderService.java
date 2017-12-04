/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myapp.takealot.service;

import com.myapp.takealot.data.ClientOrderRepository;
import com.myapp.takealot.data.ProductRepository;
import com.myapp.takealot.data.UserRepository;
import com.myapp.takealot.entity.Address;
import com.myapp.takealot.entity.ClientOrder;
import com.myapp.takealot.entity.Customer;
import com.myapp.takealot.entity.LineProduct;
import com.myapp.takealot.entity.OrderDestination;
import com.myapp.takealot.entity.Product;
import com.myapp.takealot.entity.UserTB;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 *
 * @author MANDELACOMP3
 */
@Service
public class ClientOrderService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ClientOrderRepository clientOrderRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private PrinterService printerService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private UserRepository userRepository;

    public HashMap processOrder(String orderData) {

        HashMap response = new HashMap();

        JSONObject jObj = new JSONObject(orderData);

        JSONArray jArray = (JSONArray) jObj.get("orderItems");
//        JSONObject destinationObj = (JSONObject) jObj.get("destinationInfo");
//        Address address = (Address) jObj.get("addressInfo");
        JSONObject customerData = new JSONObject(jObj.getString("user"));

        Long customerID = customerData.getLong("id");

        String sessionID = (String) jObj.get("sessionID");

//        JSONObject addressObj = (JSONObject) destinationObj.get("addressInfo");
//        
//        String houseNumber = addressObj.getString("houseNumber");
//        String streetName = addressObj.getString("streetName");
//        String surburb = addressObj.getString("surburb");
//        String city = addressObj.getString("city");
//        String postalCode = addressObj.getString("postalCode");
//        String province = addressObj.getString("province");
//
//      Address address = new Address(houseNumber,streetName,surburb,city,postalCode,province);
        List<LineProduct> lineProducts = createLineProduct(jArray);

//        double shippingCost = destinationObj.getDouble("cost");
//        OrderDestination destination = new OrderDestination(shippingCost, address);
        //Create timestamp for the order
        Date date = new Date(System.currentTimeMillis());
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        //Create order date
        String orderDate = cal.get(Calendar.DAY_OF_MONTH) + "/" + (cal.get(Calendar.MONTH) + 1) + "/"
                + cal.get(Calendar.YEAR) + " - " + cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE);

        ClientOrder clientOrder = new ClientOrder(customerID, orderDate, null, lineProducts);

        String url = "/";
        String message = "";

        //find user login status
        UserTB dUser = userRepository.findOne(customerID);
        String loginID = dUser.getLoginID();

        //Persist customer order
        if (sessionID.equals(loginID)) {

            List<ClientOrder> orders = clientOrderRepository.findByUserID(customerID);

            //Get number of orders before persisting the current one
            int numOfBefore = orders.size();

            //Persist the client order
            clientOrderRepository.save(clientOrder);

            //Update the quantity of products left in store
            ArrayList<Product> allProducts = productService.getAllProducts();
            updateQuantity(allProducts, lineProducts);

            //Get number of orders after persisting the current one
            orders = clientOrderRepository.findByUserID(customerID);
            int numOfAfter = orders.size();

            //Get all specific customer orders
            //If the is an increase in number of client orders that means the order was persisted successfully
            if (numOfBefore != numOfAfter) {
                //Get customer's latest order number
                Long OrderNumber = orders.get(orders.size() - 1).getId();
                message = "Your order is successfully placed. You will recieve a confirmation email with your order number";

                String subject = "TAKE-A-LOT ORDER CONFIRMATION";
                String emailAddress = customerData.getString("email");

                String firstname = customerData.getString("firstname");
                String emailBody = createEmailBody(OrderNumber, firstname, allProducts, lineProducts);
                System.out.println(emailBody);

                try {

                    emailService.sendEmail(subject, emailBody, emailAddress);

                } catch (MessagingException ex) {
                    Logger.getLogger(ClientOrderService.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

            response.put("status", HttpStatus.CREATED);

        } else {

            response.put("HttpStatus", HttpStatus.CONFLICT);
            message = "It looks like you did not login";
            url = "/login";

        }

        response.put("message", message);
        response.put("url", url);

        return response;
    }

    private List<LineProduct> createLineProduct(JSONArray jArray) {

        List<LineProduct> lineProducts = new ArrayList<>();

        for (int i = 0; i < jArray.length(); i++) {
            JSONObject obj = (JSONObject) jArray.get(i);

            int quantity = obj.getInt("QUANTITY");
            int productID = obj.getInt("ID");

            LineProduct product = new LineProduct(productID, quantity);
            lineProducts.add(product);
        }

        return lineProducts;
    }

    public HashMap getAllOrders(String sessionID, Long adminID) {

        HashMap response = new HashMap();

        String message = "";
        String status = "";

        UserTB dUser = userRepository.findOne(adminID);
        String loginID = dUser.getLoginID();

        if (sessionID.equals(loginID)) {

            List<ClientOrder> allOrders = getAllOrders();

            if (allOrders.isEmpty()) {
                message = "There are no olders";
                status = "FAILED";
            } else {
                message = allOrders.size() + " order(s) found";
                status = "OK";
            }

            response.put("allOrders", allOrders);

        }

        response.put("message", message);
        response.put("status", status);

        return response;
    }

    private List<ClientOrder> getAllOrders() {

        Iterable<ClientOrder> tempAllUser = clientOrderRepository.findAll();
        List<ClientOrder> allOrders = new ArrayList<>();

        tempAllUser.forEach(allOrders::add);

        return allOrders;
    }

    private void updateQuantity(ArrayList<Product> allProducts, List<LineProduct> lineProducts) {

        for (int i = 0; i < allProducts.size(); i++) {
            Product product = allProducts.get(i);

            for (int j = 0; j < lineProducts.size(); j++) {
                LineProduct lineProduct = lineProducts.get(j);

                if (product.getId() == lineProduct.getProductId()) {
                    int remainingQuantity = product.getQuantity() - lineProduct.getQuantity();
                    product.setQuantity(remainingQuantity);

                    productRepository.save(product);
                }
            }

        }
    }

    public HashMap printInvoice(String sessionID,Long adminID) {
        HashMap response = new HashMap();

        String message = "";
        String status = "";

        UserTB dUser = userRepository.findOne(adminID);
        String loginID = dUser.getLoginID();

        if (sessionID.equals(loginID)) {

            try {
                ArrayList<Product> allProducts = productService.getAllProducts();
                List<ClientOrder> allOrders = getAllOrders();

                message = printerService.printInvoice(allOrders, allProducts);

                if (allOrders.isEmpty()) {
                    status = "FAILED";
                } else {

                    status = "OK";
                }

                response.put("allOrders", allOrders);

            } catch (IOException ex) {
                Logger.getLogger(ClientOrderService.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        response.put("message", message);
        response.put("status", status);

        return response;
    }

    private String createEmailBody(Long OrderNumber, String firstname, ArrayList<Product> allProducts, List<LineProduct> lineProducts) {

        String rows = "";
        double total = 0.00;
        int totalQuantity = 0;
        int number = 0;

        String emailBody = "<h4>Hi " + firstname + "</h4><br/><br/> Order Number : <b>" + OrderNumber + ""
                + "</b><br/><br/>Your order is successfully placed and it have the following item(s) :<br/><br/>"
                + "<table>"
                + "<thead>"
                + "<tr style=\"width: 450px;height: 35px;border: none;background-color: #1d78cb;\">"
                + "<td>#</td><td>Item Description</td><td>Item Price</td><td>Quantity</td><td>Sub Total</td>"
                + "</tr>"
                + "</thead>"
                + "<tbody>";

        for (int j = 0; j < lineProducts.size(); j++) {
            LineProduct lineProduct = lineProducts.get(j);

            for (int y = 0; y < allProducts.size(); y++) {

                if (allProducts.get(y).getId() == lineProduct.getProductId()) {

                    Product product = allProducts.get(y);
                    number = number + 1;

                    double subTotal = product.getPrice() * lineProduct.getQuantity();
                    total = total + subTotal;
                    totalQuantity += lineProduct.getQuantity();

                    rows += "<tr style=\"width: 70%;height: 35px;border: none;\">"
                            + "<td>" + number + "</td>"
                            + "<td>" + product.getProductDesc() + "<br/>" + product.getCategory() + "</td>"
                            + "<td> R " + product.getPrice() + "</td>"
                            + "<td>" + lineProduct.getQuantity() + "</td>"
                            + "<td> R " + subTotal + "</td>"
                            + "</tr>";

                    y = allProducts.size() + 1;
                }

            }
        }

        rows += "<tr style=\"width: 70%;height: 35px;border: none;\">"
                + "<td></td><td></td>"
                + "<td> TOTAL :</td>"
                + "<td>" + totalQuantity + "</td>"
                + "<td> R " + total + "</td>"
                + "</tr>"
                + "<tbody>"
                + "</table>";

        emailBody = emailBody + rows;

        return emailBody;
    }

}
