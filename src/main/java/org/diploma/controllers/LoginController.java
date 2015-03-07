package org.diploma.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

//    @RequestMapping(value = "/check-credentials", method = RequestMethod.POST)
//    @ResponseBody
//    public String checkCredentials(@RequestParam String email,
//                                   @RequestParam String password)
//    {
////        CommonUtils.
//
//
//        return null;
//    }
}
