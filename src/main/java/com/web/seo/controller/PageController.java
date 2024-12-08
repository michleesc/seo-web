package com.web.seo.controller;

import com.web.seo.entity.Content;
import com.web.seo.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
    @Autowired
    private ContentService contentService;

    @GetMapping("/")
    public String index(Model model) {
        Content lastContent = contentService.getLatestContent();
        model.addAttribute("lastContent", lastContent);
        return "index";
    }

    @GetMapping("/artikel")
    public String artikel() {
        return "article";
    }
}
