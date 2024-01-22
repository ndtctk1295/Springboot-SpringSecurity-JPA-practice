package com.example.demo.controllers.admin;

import com.example.demo.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ViewProductController {

    @Autowired
    private ProductRepository repository;

    @GetMapping("/admin/products")
    public ModelAndView getAllProducts(Model model) {
        ModelAndView mav = new ModelAndView("product");
        mav.addObject("products", repository.findAll());
        return mav;
    }


}


