package org.choviwu.npcjava.plugin.controller;

import org.choviwu.npcjava.plugin.App;
import org.choviwu.npcjava.plugin.domain.TClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/")
public class ConfController {

    @Autowired
    private App app;

    @RequestMapping
    public String config(HttpServletRequest request, Model modelAndView) {
        List<TClient> tClients = app.readList();
        modelAndView.addAttribute("data", tClients);

        return "index";
    }
}
