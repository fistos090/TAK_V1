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
import javax.persistence.SequenceGenerator;

/**
 *
 * @author Sifiso
 */
@Entity
//@SequenceGenerator(name = LineProduct.LINE_PRODUCT_ID_SEQ, sequenceName = LineProduct.LINE_PRODUCT_ID_SEQ,initialValue = 10,allocationSize = 53)
public class LineProduct implements Serializable {
    //private static final long serialVersionUID = 1L;
//    public static final String LINE_PRODUCT_ID_SEQ = "LINE_PRODUCT_ID_SEQ";
    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = LINE_PRODUCT_ID_SEQ)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(nullable=false, length=15)
    private long productId;
    @Column(nullable=false, length=15)
    private int quantity;


    public LineProduct(){
    }

    public LineProduct(long productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
        
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }
     
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
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
        if (!(object instanceof LineProduct)) {
            return false;
        }
        LineProduct other = (LineProduct) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "za.ac.tut.entity.LineProduct[ id=" + id + " ]";
    }
    
}
