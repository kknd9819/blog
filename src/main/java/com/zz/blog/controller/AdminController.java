package com.zz.blog.controller;

import com.zz.blog.entity.Article;
import com.zz.blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("")
    public String admin(Model model) {
        List<Article> articles = articleService.findArticleList();
        model.addAttribute("articles", articles);
        return "article/list";
    }

    @GetMapping("/add")
    public String add(HttpServletRequest request) {
        String token = UUID.randomUUID().toString();
        request.getSession().setAttribute("token", token);
        return "article/add";
    }

    @PostMapping("/save")
    @ResponseBody
    public Map<String, String> save(HttpServletRequest request, Article article) {
        Map<String,String> map = new HashMap<>();
        String validToken = (String)request.getSession().getAttribute("token");
        String token = request.getParameter("token");
        if (StringUtils.isEmpty(validToken)) {
            map.put("msg", "服务端没有token");
        }
        if (token.equals(validToken)) {
            boolean flag = articleService.save(request,article);
            if (flag) {
                map.put("msg", "发布成功");
            } else {
                map.put("msg", "数据库异常");
            }
        } else {
            map.put("msg", "发布失败,表单重复提交");
        }
        return map;
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model, HttpServletRequest request) {
        String token = UUID.randomUUID().toString();
        request.getSession().setAttribute("token", token);
        Article article = articleService.findById(id);
        model.addAttribute("article", article);
        return "article/edit";
    }

    @PostMapping("/update")
    @ResponseBody
    public Map<String, String> update(HttpServletRequest request, Article article) {
        Map<String,String> map = new HashMap<>();
        String validToken = (String)request.getSession().getAttribute("token");
        String token = request.getParameter("token");
        if (StringUtils.isEmpty(validToken)) {
            map.put("msg", "服务端没有token");
        }
        if (token.equals(validToken)) {
            boolean flag = articleService.update(request,article);
            if (flag) {
                map.put("msg", "修改成功");
            } else {
                map.put("msg", "数据库异常");
            }
        } else {
            map.put("msg", "发布失败,表单重复提交");
        }
        return map;
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        articleService.delete(id);
        return "redirect:/admin/";
    }

}
