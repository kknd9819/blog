package com.zz.blog.service;

import com.zz.blog.entity.Article;
import com.zz.blog.mapper.ArticleMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;
import static org.mockito.Mockito.*;

import java.util.List;

@SpringBootTest
public class ArticleServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private ArticleService articleService;

    @MockBean
    ArticleMapper articleMapper;

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

    @Test
    public void testMock() {
        List list = mock(List.class);
        when(list.get(0)).thenReturn("Hello World");
        System.out.println(list.get(0));
        System.out.println(list.size());
    }
}