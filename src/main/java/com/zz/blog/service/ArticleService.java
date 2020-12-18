package com.zz.blog.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zz.blog.constant.BlogConstant;
import com.zz.blog.entity.Article;
import com.zz.blog.mapper.ArticleMapper;
import com.zz.blog.util.BlogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ArticleService {

    @Resource
    private ArticleMapper articleMapper;

    @Autowired
    private BlogUtil blogUtil;

    public List<Article> findArticleList() {
        return articleMapper.findArticleList(new Article());
    }

    public Article findById(Integer id) {
        if (id == null) {
            return null;
        }
        return articleMapper.findById(id);
    }

    @Transactional
    public boolean save(HttpServletRequest request, Article article) {
        // 图片上传
        try {
            MultipartFile file = ((MultipartHttpServletRequest) request).getFile("file");

            // 获取源文件名
            String fileName = file.getOriginalFilename();
            int lastIndexOf = fileName.lastIndexOf(".");

            // 拿到后缀
            String suffix = fileName.substring(lastIndexOf);
            String uuid = UUID.randomUUID().toString();

            // 拼接uuid和后缀名
            String fName = uuid + suffix;
            String destDirPath = blogUtil.getJarPath() + BlogConstant.UPLOAD_DIR;
            File destDirs = new File(destDirPath);
            if (!destDirs.exists()) {
                destDirs.mkdirs();
            }
            File dest = new File(destDirPath + fName);

            // 文件传输
            file.transferTo(dest);

            article.setImgUrl(fName);
            article.setCreateDate(new Date());

            //保存数据库
            int count = articleMapper.insert(article);
            return count > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Transactional
    public boolean update(HttpServletRequest request, Article articleVO) {
        // 图片上传
        try {
            MultipartFile file = ((MultipartHttpServletRequest) request).getFile("file");

            // 获取源文件名
            String fileName = file.getOriginalFilename();
            int lastIndexOf = fileName.lastIndexOf(".");

            // 拿到后缀
            String suffix = fileName.substring(lastIndexOf);
            String uuid = UUID.randomUUID().toString();

            // 拼接uuid和后缀名
            String fName = uuid + suffix;
            String destDirPath = blogUtil.getJarPath() + BlogConstant.UPLOAD_DIR;
            File destDirs = new File(destDirPath);
            if (!destDirs.exists()) {
                destDirs.mkdirs();
            }
            File dest = new File(destDirPath + fName);

            // 文件传输
            file.transferTo(dest);

            Article article = articleMapper.findById(articleVO.getId());

            // 删除之前的图片
            File oldFile = new File(blogUtil.getJarPath() + BlogConstant.UPLOAD_DIR + article.getImgUrl());
            oldFile.delete();

            article.setTitle(articleVO.getTitle());
            article.setSummary(articleVO.getSummary());
            article.setContent(articleVO.getContent());
            article.setCreateDate(new Date());
            article.setImgUrl(fName);

            //保存数据库
            int count = articleMapper.update(article);

            return count > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public PageInfo<Article> findPage(Integer pageNum, Integer pageSize, String title) {
        PageHelper.startPage(pageNum, pageSize);
        Article article = new Article();
        article.setTitle(title);
        List<Article> list = articleMapper.findArticleList(article);
        return new PageInfo<>(list);
    }

    public void delete(Integer id) {
        articleMapper.deleteById(id);
    }
}
