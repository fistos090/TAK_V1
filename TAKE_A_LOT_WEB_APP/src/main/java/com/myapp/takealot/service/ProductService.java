/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myapp.takealot.service;

import com.myapp.takealot.data.ProductRepository;
import com.myapp.takealot.data.UserRepository;
import com.myapp.takealot.entity.Product;
import com.myapp.takealot.entity.UserTB;
import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.http.HttpSession;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author MANDELACOMP3
 */
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;

    public HashMap getAllShopProduct() {

        HashMap productsDetails = new HashMap();

        ArrayList<Product> products = getAllProducts();//convert Iterable into ArrayList

        products = getOnstockProduct(products);//Remove product with negative quantity
        productsDetails.put("products", products);

        return productsDetails;
    }

    public HashMap getByCategory(String category) {
        ArrayList<Product> products = productRepository.findByCategory(category);

        HashMap productsDetails = new HashMap();

        products = getOnstockProduct(products);//Remove product with negative quantity
        productsDetails.put("products", products);

        return productsDetails;
    }

    /*
    Method used to convert Iterable into ArrayList
     */
    public ArrayList<Product> getAllProducts() {

        Iterable<Product> allProducts = productRepository.findAll();

        ArrayList<Product> products = new ArrayList<>();

        allProducts.forEach(products::add);

        return products;
    }

    /*
    1)This method check product quantity 
    2)remove offstock products and 
    3)return products that have a positive quantity only [quantity > 0]
     */
    private ArrayList<Product> getOnstockProduct(ArrayList<Product> products) {

        ArrayList<Product> offStockProducts = new ArrayList<>();

        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);

            if (product.getQuantity() <= 0) {
                offStockProducts.add(product);
            }
        }

        products.removeAll(offStockProducts);

        return products;
    }

    public int loadProducts() {
        int i = 1;
        for (; i <= 15; i++) {
            Product p = new Product("1st item", 25.2 * i, "Books", 500);
            productRepository.save(p);
        }

        return i;
    }

    public HashMap storeProduct(String data) {

        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        System.out.println(data);
        HashMap response = new HashMap();

        JSONObject jsonData = new JSONObject(data);

        String sessionID = jsonData.getString("sessionID");
        Long adminID = jsonData.getLong("adminID");
        
        String message = "Adding a new product to database has failed";
        String status = "FAILED";
        String productPicName = "";

        UserTB dUser = userRepository.findOne(adminID);
        String loginID = dUser.getLoginID();

        if (sessionID.equals(loginID)) {

            JSONObject productData = (JSONObject) jsonData.get("product");
            System.out.println(productData);
            String productDesc = productData.getString("DESC");
            double price = productData.getDouble("PRICE");
            String category = productData.getString("CATEGORY");
            int quantity  = productData.getInt("QUANTITY");

            Product product = new Product(productDesc, price, category, quantity);
            
            productRepository.save(product);
            
            ArrayList<Product> categoryProducts = productRepository.findByCategory(category);
            int newProductID = categoryProducts.get(categoryProducts.size()-1).getId().intValue();
            
            productPicName = newProductID +"_prod.jpg";
            message = "A new product has been added to database :[ Please rename the product picture to the following name \" "+productPicName+" \" and put it inside the \"static/images/products \" directory.]";
            status = "CREATED";  
        }
        
        response.put("ppName", productPicName);
        response.put("message", message);
        response.put("status", status);

        return response;
    }

}
