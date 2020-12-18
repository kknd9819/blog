package com.zz.blog.controller;

import com.zz.blog.constant.BlogConstant;
import com.zz.blog.util.BlogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Controller
@RequestMapping("/image")
public class ImageController {

    @Autowired
    private BlogUtil blogUtil;

    @GetMapping("/{imgName}")
    public void getPicture(@PathVariable("imgName") String imgName,
                           HttpServletRequest request,
                           HttpServletResponse response) throws IOException {
        // 拼接文件地址
        String sb = blogUtil.getJarPath() + BlogConstant.UPLOAD_DIR + imgName;

        // 判断图片后缀名
        int lastIndexOf = imgName.lastIndexOf(".");
        String imgType = imgName.substring(lastIndexOf);
        if (imgType.equalsIgnoreCase("JPG")) {
            response.setContentType("image/jpeg");
        } else if (imgType.equalsIgnoreCase("PNG")) {
            response.setContentType("image/png");
        } else if (imgType.equalsIgnoreCase("GIF")) {
            response.setContentType("image/gif");
        } else {
            response.setContentType("application/octet-stream");
        }

        File file = new File(sb);
        response.setContentLengthLong(file.length());

        //设置header
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0L);

        // 准备下载
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
        BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
        byte[] buffer = new byte[1024];
        int c;
        while ( (c =bis.read(buffer)) != -1) {
            bos.write(buffer, 0 , c);
            bos.flush();
        }

    }
}
