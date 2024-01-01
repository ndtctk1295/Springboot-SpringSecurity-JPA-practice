package com.example.demo.controllers;

import com.example.demo.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
//@RequestMapping(path = "/view")
//@RequestMapping("/index")
public class ViewProductControllerAPI {
    @Autowired
    private ProductRepository repository;

    @GetMapping("/products")
    public ModelAndView getAllProducts(Model model) {
        ModelAndView mav = new ModelAndView("index");
//        mav.setViewName("index");
//        mav.addObject("products", repository.findAll());
        mav.addObject("message", "hello from view");
        return mav;
    }



}


