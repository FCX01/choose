package com.webuse.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



@Controller
@RequestMapping("/hello")
public class HelloController {

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/analysis", method = RequestMethod.GET)
    public String analysis() {
        return "analysis";
    }

    @RequestMapping(value = "/createPage", method = RequestMethod.GET)
    public String createPage() {
        return "createPage";
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test() {
        return "test";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/templateUpdate", method = RequestMethod.GET)
    public String templateUpdate() {
        return "templateUpdate";
    }

    @RequestMapping(value = "/pageUpdate", method = RequestMethod.GET)
    public String pageUpdate() {
        return "pageUpdate";
    }

}

