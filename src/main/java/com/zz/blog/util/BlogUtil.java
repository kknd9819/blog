package com.zz.blog.util;

import org.springframework.boot.system.ApplicationHome;
import org.springframework.stereotype.Component;

@Component
public class BlogUtil {

    public String getJarPath() {
        ApplicationHome home = new ApplicationHome(getClass());
        return home.getSource().getParentFile().getAbsolutePath();
    }
}
