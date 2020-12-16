package com.zz.blog.mapper;

import com.zz.blog.entity.Article;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArticleMapper {

    List<Article> findArticleList(Article article);

    Article findById(@Param("id") Integer id);

    int insert(Article article);

    int update(Article article);

    int deleteById(Integer id);
}
