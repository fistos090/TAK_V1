/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myapp.takealot.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

/**
 *
 * @author Sifiso
 */
@Entity
public class ClientOrder implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(length = 9, nullable = false, unique = false)
    private Long userID;
    
    @Column(length = 30, nullable = false)
    private String clientOrderDate;
    
    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private OrderDestination destination;
    
    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<LineProduct> lineProducts;

    public ClientOrder() {
    }

    public ClientOrder(Long userID, String clientOrderDate, OrderDestination destination, List<LineProduct> lineProducts) {
        this.userID = userID;
        this.clientOrderDate = clientOrderDate;
        this.destination = destination;
        this.lineProducts = lineProducts;
    }

    public OrderDestination getDestination() {
        return destination;
    }

    public void setDestination(OrderDestination destination) {
        this.destination = destination;
    }
    
    public List<LineProduct> getLineProducts() {
        return lineProducts;
    }

    public Long getId() {
        return id;
    }

   

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public String getClientOrderDate() {
        return clientOrderDate;
    }

    public void setClientOrderDate(String clientOrderDate) {
        this.clientOrderDate = clientOrderDate;
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
        if (!(object instanceof ClientOrder)) {
            return false;
        }
        ClientOrder other = (ClientOrder) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "za.ac.tut.entity.Order[ id=" + id + " ]";
    }
}