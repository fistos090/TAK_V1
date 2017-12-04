/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myapp.takealot.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Sifiso
 */
@Entity
public class UserTB implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String userRole;
    private String cellphonNumber;
    private String gender;
    private String dateOfBirth;
    private String loginID;
    
    public UserTB() {
        
    }

     //is used when a new user registers
    public UserTB(String firstname, String lastname, String email, String password, String cellphonNumber, String gender, String dateOfBirth) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.cellphonNumber = cellphonNumber;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
    }
    
    //Will be used during user update

    public UserTB(Long id, String firstname, String lastname, String email, String password, String userRole, String cellphonNumber, String gender, String dateOfBirth, String loginID) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.userRole = userRole;
        this.cellphonNumber = cellphonNumber;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.loginID = loginID;
    }
    
    
    public String getLoginID() {
        return loginID;
    }

    public void setLoginID(String loginID) {
        this.loginID = loginID;
    }
    
    public String getCellphonNumber() {
        return cellphonNumber;
    }

    public void setCellphonNumber(String cellphonNumber) {
        this.cellphonNumber = cellphonNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Long getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
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
        if (!(object instanceof UserTB)) {
            return false;
        }
        UserTB other = (UserTB) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.takealot_webapp.entity.User[ id=" + id + " ]";
    }
    
    public String signIn(HttpSession session){
       // session.setAttribute("userIn", this);
        return session.getId();
    }
    
    public void singOut( HttpSession session ){
       // session.removeAttribute("userIn");
        session.invalidate();
    }
    
}
