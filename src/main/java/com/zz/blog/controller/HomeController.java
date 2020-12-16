package com.zz.blog.controller;

import com.zz.blog.entity.Article;
import com.zz.blog.service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class HomeController {

    @Resource
    private ArticleService articleService;

    @GetMapping("/")
    public String index(Model model) {
        List<Article> articles = articleService.findArticleList();
        model.addAttribute("articles", articles);
        return "index";
    }

    @GetMapping("/article/{id}")
    public String article(@PathVariable("id") Integer id, Model model) {
        Article article = articleService.findById(id);
        model.addAttribute("article", article);
        return "article";
    }

}
