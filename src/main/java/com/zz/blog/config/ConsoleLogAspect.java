package com.zz.blog.config;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


@Aspect
@Component
public class ConsoleLogAspect {

    private final Logger log = LoggerFactory.getLogger(getClass());

    //设置切面点
    @Pointcut(value = "(execution(* com.zz.blog.controller.*.*(..)))")
    public void webLog() {}

    //指定切点前的处理方法
    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Exception {
        //获取request对象
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        StringBuilder sb = new StringBuilder();
        //拼接请求内容
        sb.append("\n请求路径:").append(request.getRequestURL().toString()).append("  ").append(request.getMethod()).append("\n");
        //判断请求是什么请求
        if (request.getMethod().equalsIgnoreCase(RequestMethod.GET.name())) {
            Map<String, String[]> parameterMap = request.getParameterMap();
            Map<String, String> paramMap = new HashMap<>();
            parameterMap.forEach((key, value) -> paramMap.put(key, String.join(",", value)));
            sb.append("请求参数:").append(JSON.toJSONString(paramMap));
        } else if (request.getMethod().equalsIgnoreCase(RequestMethod.POST.name())) {
            Object[] args = joinPoint.getArgs();
            StringBuilder stringBuilder = new StringBuilder();
            Arrays.stream(args).forEach(object -> stringBuilder.append(object.toString().replace("=",":")));
            if (stringBuilder.length() == 0){
                stringBuilder.append("{}");
            }
            sb.append("请求参数:").append(stringBuilder.toString());
        }
        log.info(sb.toString());
    }

    //指定切点前的处理方法
    @AfterReturning(pointcut = "webLog()",returning = "result")
    public void doAfterReturning(Object result) {
        if (ObjectUtils.isEmpty(result)){
            return;
        }
        log.info("\n返回結果:" + JSON.toJSONString(result));
    }
}
