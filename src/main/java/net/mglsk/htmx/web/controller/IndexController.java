package net.mglsk.htmx.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;


@Controller
public class IndexController {

    @RequestMapping("/")
    public RedirectView index(RedirectAttributes attributes) {
        return new RedirectView("/contacts");
    }
    
    
}
