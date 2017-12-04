/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myapp.takealot.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


/**
 *
 * @author Sifiso
 */
@Entity
public class Product implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "productID")
    private Long id;  
    @Column(nullable=false, length=25)
    private String productDesc;
    @Column(nullable=false, length=15)
    private double price;
    @Column(nullable=false, length=25)
    private String category;
    @Column(nullable=false, length=25)
    private int quantity;

    public Product() {
    }
    
    public Product(String productDesc, double price, String category, int quantity) {
        this.productDesc = productDesc;
  
        this.price = price;
        this.category = category;
        this.quantity = quantity;
    }
    
    public Product(Long id,String productDesc, double price, String category, int quantity) {
        this.id = id;
        this.productDesc = productDesc;
        
       
        this.price = price;
        this.category = category;
        this.quantity = quantity;
    }
    
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    
    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImageURL(){
        return "images/products/" + id +"_prod.jpg" ;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Product)) {
            return false;
        }
        Product other = (Product) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Product{" + "productDesc=" + productDesc + ", price=" + price + ", category=" + category + ", quantity=" + quantity + '}';
    }

   

}
