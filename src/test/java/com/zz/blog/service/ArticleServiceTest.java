package com.zz.blog.service;

import com.zz.blog.entity.Article;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import java.util.List;

@SpringBootTest
public class ArticleServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private ArticleService articleService;

    @Test
    public void findArticleList() {
        List<Article> list = articleService.findArticleList();
        Assert.notEmpty(list, "不能为空");
    }

    @Test
    public void findUser() {
        UserDetails user = userService.loadUserByUsername("administrator");
        Assert.notNull(user, "User 不是空的");
    }

    @Test
    public void findById() {
        Article article = articleService.findById(1);
        Assert.isNull(article, "不能为空");
    }
}