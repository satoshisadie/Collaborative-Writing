package org.diploma.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Controller
public class TestController {

    @RequestMapping("/")
    @ResponseBody
    public ModelAndView home() {
        return new ModelAndView("test");
    }

    @RequestMapping(value = "/test", method = RequestMethod.POST)
    @ResponseBody
    public String test(@RequestParam String text) throws NoSuchAlgorithmException {
        return secureHashAlgorithm1(text);
    }

    public String secureHashAlgorithm1(String text) throws NoSuchAlgorithmException {
        final MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
        final byte[] digest = messageDigest.digest(text.getBytes());

        final StringBuilder stringBuilder = new StringBuilder();
        for (byte aDigest : digest) {
            stringBuilder.append(Integer.toString((aDigest & 0xff) + 0x100, 16).substring(1));
        }

        return stringBuilder.toString();
    }
}
