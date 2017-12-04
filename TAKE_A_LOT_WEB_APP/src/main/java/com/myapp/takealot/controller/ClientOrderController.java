/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myapp.takealot.controller;

import com.myapp.takealot.service.ClientOrderService;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Mmetsa
 */
@RestController
@RequestMapping("/TAKEALOT")
public class ClientOrderController {
    
    @Autowired
    private ClientOrderService orderService;
    
    @RequestMapping(method = RequestMethod.POST,value = "/checkout")
    public HashMap checkout(@RequestBody String orderData){
       
       
        System.out.println(orderData);
        //HashMap hashMap = new HashMap();
        HashMap response = orderService.processOrder(orderData);
           
        return response;
    }
    
    @RequestMapping(method = RequestMethod.GET,value = "/displayAllOrders/{sessionID}/{adminID}")
    public HashMap displayAllOrders(@PathVariable("sessionID") String sessionID,@PathVariable("adminID") Long adminID){

        System.out.println(sessionID);
        
        HashMap response = orderService.getAllOrders(sessionID,adminID);
 
        System.out.println(response);
        return response;
    }
    
//    @RequestMapping(method = RequestMethod.GET,value = {"/deleteOrder/{sessionID}/{orderID}",})
//    public HashMap deleteOrder(@PathVariable() Long orderID,@PathVariable String sessionID,HttpServletRequest request){
//
//        HashMap response = orderService.getAllOrders(sessionID,request.getSession());
// 
//        return response;
//    }
    
    @RequestMapping(method = RequestMethod.GET,value = "/printInvoice/{sessionID}/{adminID}")
    public HashMap printInvoice(@PathVariable("sessionID") String sessionID,@PathVariable("adminID") Long adminID){

        System.out.println(sessionID);
        
        HashMap response = orderService.printInvoice(sessionID,adminID);
 
        System.out.println(response);
        return response;
    }
      
}
