package com.myapp.takealot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller

public class HomeController {

	@RequestMapping("/")
	public String redirect() {
            System.out.println("com.myapp.takealot.controller.HomeController.redirect()");
            return "home";
	}
        
        @RequestMapping("/{page}")
        public String redirectDynamicly(@PathVariable("page") String page){
            System.out.println(page);
            return page;
        }
}
