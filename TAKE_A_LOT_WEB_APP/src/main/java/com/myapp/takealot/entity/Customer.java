/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myapp.takealot.entity;

import java.io.Serializable;
import javax.persistence.Entity;

/**
 *
 * @author Sifiso
 */
@Entity
public class Customer extends UserTB implements Serializable {

    private static final long serialVersionUID = 1L;

    private String securityQuestuion;
    private String answer;

    public Customer() {
    }

    //is used when a new user registers
    public Customer(String securityQuestuion, String answer, String firstname, String lastname, String email, String password, String cellphonNumber, String gender, String dateOfBirth) {
        super(firstname, lastname, email, password, cellphonNumber, gender, dateOfBirth);
        this.securityQuestuion = securityQuestuion;
        this.answer = answer;
    }
    //Will be used during user update

    public Customer(String securityQuestuion, String answer, Long id, String firstname, String lastname, String email, String password, String userRole, String cellphonNumber, String gender, String dateOfBirth, String loginID) {
        super(id, firstname, lastname, email, password, userRole, cellphonNumber, gender, dateOfBirth, loginID);
        this.securityQuestuion = securityQuestuion;
        this.answer = answer;
    }

    public String getSecurityQuestuion() {
        return securityQuestuion;
    }

    public void setSecurityQuestuion(String securityQuestuion) {
        this.securityQuestuion = securityQuestuion;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

}
