/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myapp.takealot.controller;

import com.myapp.takealot.entity.Product;
import com.myapp.takealot.service.ProductService;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author MANDELACOMP3
 */
@RestController

@RequestMapping("/TAKEALOT")
public class ProductController {
    
    @Autowired
    private ProductService productService;
    
    @RequestMapping(method = RequestMethod.GET,value = "/displayAllProducts")
    @ResponseBody
    @CrossOrigin(origins = "http://localhost:4200")
    public HashMap displayAllShopProduct(){
        
       
        
        HashMap products = productService.getAllShopProduct();
        System.out.println(products.toString());
        
        return products;
    }
    
    @RequestMapping(method = RequestMethod.GET,value = "/category/{category}")
    public HashMap displayByCategory(@PathVariable("category") String category){
        
        HashMap products = productService.getByCategory(category);
        
        return products;
    }
    
    
    @RequestMapping(method = RequestMethod.GET,value = "/load")
    public HashMap loadProducts(){
        
        int num = productService.loadProducts();
        HashMap h = new HashMap();
        h.put("s", "OK - "+num+" are loaded");
        
        return h;
    }
    
   @RequestMapping(method = RequestMethod.POST,value = "/loadNewProduct")
    public HashMap loadProduct(@RequestBody String data){
        
       
        HashMap outcome = productService.storeProduct(data);
        
        return outcome;
    }
    
    
    
}
