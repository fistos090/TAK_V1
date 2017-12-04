package com.myapp.takealot.controller;

import com.myapp.takealot.entity.Customer;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.myapp.takealot.service.UserService;

import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/TAKEALOT")
public class UserController {

    @Autowired
    private UserService service;

    @RequestMapping(method = RequestMethod.POST, value = "/register")
    public HashMap registerNewUser(@RequestBody Customer cus) {

        HashMap responseDetails = service.registerCustomer(cus);

        System.out.println(cus);

        System.out.println(cus.getEmail());
        return responseDetails;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public HashMap signIn(@RequestBody Customer cus, HttpServletRequest request) {

        HttpSession session = request.getSession();

        HashMap responseDetails = service.login(cus, session);
//        System.out.println(responseDetails.toString());
        return responseDetails;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/logout/{sessionID}/{userID}")
    public HashMap signout(@PathVariable("sessionID") String sessionID, @PathVariable("userID") Long userID) {

        HashMap response = service.logout(sessionID, userID);

        return response;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/updateProfile")
    public HashMap updateProfile(@RequestBody Customer cus) {

      System.out.println(cus);
        HashMap response = service.updateProfile(cus);

        return response;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/forgot")
    public HashMap retrievePassword(@RequestBody String email) {
        System.out.println("YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY");
        HashMap response = service.findCustomer(email);
        System.out.println(email);
        return response;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/passAnswer")
    public HashMap completePasswordRetrival(@RequestBody String customerData) {

        HashMap response = service.completePasswordRetrival(customerData);

        return response;
    }

}
