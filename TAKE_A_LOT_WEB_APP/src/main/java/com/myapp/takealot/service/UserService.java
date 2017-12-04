/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myapp.takealot.service;

import com.myapp.takealot.data.UserRepository;
import com.myapp.takealot.entity.Customer;
import com.myapp.takealot.entity.UserTB;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 *
 * @author MANDELACOMP3
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailService emailService;

    public HashMap registerCustomer(Customer user) {

//        JSONObject jObject = new JSONObject(userData);
//        
//        String securityQuestuion = jObject.getString("securityQuestuion");
//        String answer = jObject.getString("answer");
//        String firstname = jObject.getString("firstname");
//        String lastname = jObject.getString("lastname");
//        String email  = jObject.getString("email");
//        String password = jObject.getString("password");
//        String cellphonNumber = jObject.getString("cellphonNumber");
//        String gender = jObject.getString("gender");
//        String dateOfBirth = jObject.getString("dateOfBirth");
//        if(email.contains("[REPLACE]")){
//            email = email.replace("[REPLACE]", ".");
//        }
//
//        UserTB user = new Customer(securityQuestuion, answer,firstname, lastname, email, password, cellphonNumber, gender, dateOfBirth);
        user.setUserRole("customer");

        List<UserTB> allUsers = getAllUsers();

        HashMap response = new HashMap();
        boolean isUnique = true;
        String message = "";
        String url = "";

        for (int i = 0; i < allUsers.size(); i++) {
            UserTB arrayUser = allUsers.get(i);

            if (arrayUser.getEmail().equals(user.getEmail())) {
                isUnique = false;
            }
        }

        if (isUnique) {
            userRepository.save(user);

            String emailBody = "Hi " + user.getFirstname() + "<br/></br> Thank you for creating an account on takealot.com."
                    + " Your registered email address is <b>" + user.getEmail() + ". </b><br><br>Once again Thank you for using Takealot.com Online Store.";
            String subject = "Takealot.com Registration Confirmation";
            try {
                emailService.sendEmail(subject, emailBody, user.getEmail());
            } catch (MessagingException ex) {
                Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
            }

            message = "You are successfully registered and a confirmation email was sent to your email";
            url = "/";
            response.put("HttpStatus", HttpStatus.CREATED);

        } else {
            message = "You are already registered, Please use your details to login.";
            url = "/login";
            response.put("HttpStatus", HttpStatus.CONFLICT);
        }

        response.put("message", message);
        response.put("url", url);

        return response;
    }

    public List<UserTB> getAllUserByRole(String role) {

        return userRepository.findByUserRole(role);
    }

    public HashMap login(Customer customer, HttpSession session) {

        List<UserTB> allUsers = getAllUsers();

        String email = customer.getEmail();
        String password = customer.getPassword();

        String message = "Please register first";
        String url = "/login";
        HttpStatus status = null;
        String sessionID = "";

        UserTB userIn = null;

        HashMap response = new HashMap();

        for (int i = 0; i < allUsers.size(); i++) {
            UserTB arrayUser = allUsers.get(i);

            if (arrayUser.getEmail().equals(email)) {

                if (arrayUser.getPassword().equals(password)) {
                    status = HttpStatus.FOUND;
                    message = "You have successfully logged in";
                    url = "/";
                    userIn = arrayUser;
                    sessionID = session.getId();

                    userIn.setLoginID(sessionID);
                    userRepository.save(userIn);

                    System.out.println(sessionID);

                    break;
                } else {
                    status = HttpStatus.NOT_FOUND;
                    message = "Enter the correct password";
                }
                //break to outside of the loop if the email exist
                i = allUsers.size() + 1;
            } else {

                message = "Email address entered doesn't exist, Please enter the correct one.";
            }

        }

        response.put("HttpStatus", status);
        response.put("url", url);
        response.put("message", message);
        response.put("sessionID", sessionID);
        response.put("userIn", userIn);

        return response;
    }

    public List<UserTB> getAllUsers() {
        Iterable<UserTB> tempAllUser = userRepository.findAll();
        List<UserTB> allUsers = new ArrayList<>();

        tempAllUser.forEach(allUsers::add);

        return allUsers;
    }

    public HashMap updateProfile(Customer cus) {

        HashMap response = new HashMap();
//        JSONObject jObject = new JSONObject(data);
//
//        String sessionID = jObject.getString("sessionID");
//
//        JSONObject newData = new JSONObject(jObject.getString("newData"));
//
//        String securityQuestuion = newData.getString("securityQuestuion");
//        String answer = newData.getString("answer");
//        Long id = newData.getLong("id");
//        String firstname = newData.getString("firstname");
//        String lastname = newData.getString("lastname");
//        String email = newData.getString("email");
//        String password = newData.getString("password");
//        String userRole = newData.getString("userRole");
//        String cellphonNumber = newData.getString("cellphonNumber");
//        String gender = newData.getString("gender");
//        String dateOfBirth = newData.getString("dateOfBirth");
//
//        UserTB user = new Customer(securityQuestuion, answer, id, firstname, lastname, email, password, userRole, cellphonNumber, gender, dateOfBirth, sessionID);

        String message = cus.getFirstname() + " " + cus.getLastname() + " your profile is not updated";
        HttpStatus status = HttpStatus.NOT_FOUND;

        System.out.println(cus.getLoginID());

        //find user login status
        UserTB dUser = userRepository.findOne(cus.getId());
        String loginID = dUser.getLoginID();

        System.out.println(loginID);

        if (cus.getLoginID().equals(loginID)) {

            cus = userRepository.save(cus);
            
            status = HttpStatus.FOUND;
            message = cus.getFirstname() + " " + cus.getLastname() + " your profile is updated";
        }

        response.put("HttpStatus", status);
        response.put("userIn",cus);
        response.put("message", message);
        response.put("sessionID", cus.getLoginID());

        return response;
    }

    public HashMap findCustomer(String email) {

        HashMap response = new HashMap();
        String message = "";
        System.out.println(email);
        Customer customer = (Customer) userRepository.findByEmail(email);

        System.out.println(customer);

        if (customer == null) {
            message = "There is no record for email enter, Please enter the correct one.";
            response.put("message", message);
            response.put("status", "FAILED");

        } else {

            response.put("status", "OK");
            response.put("customerQ", customer.getSecurityQuestuion());
        }

        return response;
    }

    public HashMap completePasswordRetrival(String customerData) {

        HashMap response = new HashMap();

        JSONObject obj = new JSONObject(customerData);

        String answer = obj.getString("answer");
        String email = obj.getString("email");

        Customer customer = (Customer) userRepository.findByEmail(email);

        String message = "You have provided the wrong answer, Please enter the correct one.";
        String status = "FAILED";

        if (answer.equals(customer.getAnswer())) {

            //send an email to customer
            String emailBody = "Hi " + customer.getFirstname() + "Your Password is: <b>" + customer.getPassword() + "</b><br><br>Thank you for using Takealot.com Online Store.";
            String subject = "Email Recovery";

            try {

                emailService.sendEmail(subject, emailBody, email);

            } catch (MessagingException ex) {
                Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
            }
            status = "OK";
            message = "An email was sent to you with your password";
        }

        response.put("email", email);
        response.put("status", status);
        response.put("message", message);

        return response;
    }

    public HashMap getQuestion(String email) {

        HashMap response = new HashMap();

        Customer customer = (Customer) userRepository.findByEmail(email);

        String question = customer.getSecurityQuestuion();

        response.put("question", question);

        return response;
    }

    public HashMap logout(String sessionID, Long userID) {

        HashMap response = new HashMap();

        //find user login status
        UserTB dUser = userRepository.findOne(userID);
        String loginID = dUser.getLoginID();

        System.out.println(loginID);

        if (sessionID.equals(loginID)) {

            dUser.setLoginID(null);
            userRepository.save(dUser);
            String status = "OK";
            response.put("status", status);

            
        }

        return response;
    }
}
