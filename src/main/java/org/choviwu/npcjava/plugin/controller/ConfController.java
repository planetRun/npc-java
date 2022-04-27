package org.choviwu.npcjava.plugin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/")
public class ConfController {

    @RequestMapping
    public String config(HttpServletRequest request) {
        return "index";
    }
}
