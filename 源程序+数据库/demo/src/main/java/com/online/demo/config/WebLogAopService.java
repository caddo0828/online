package com.online.demo.config;

import com.online.demo.util.DataSourceTemplate;
import com.online.demo.util.MapUtils;
import com.online.demo.util.UUIDUtils;
import com.online.demo.util.Util;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Aspect
@Component
public class WebLogAopService {

    @Autowired @Qualifier("mysqlOnDataSourceTemplate")
    private DataSourceTemplate template;

    @Pointcut("execution(public * com.online.demo.service..*.*(..))")
    public void webLog() {
    }

    @After("webLog()")
    public void doBefore(JoinPoint joinPoint) {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String ip=Util.getIpAddr(request);
        Map user= (Map) request.getSession().getAttribute("user");
        String email= MapUtils.getMapNullString(user,"email");
        MethodSignature methodSignature= (MethodSignature) joinPoint.getSignature();
        logInfoContent log=methodSignature.getMethod().getAnnotation(logInfoContent.class);
        if(log!=null)
            if(log.isInsertLog())
                insertLog(email,log.content(),ip);
    }

    /**
     * 插入日志使用表
     * @param email
     * @param content
     * @param ip
     */
    private void insertLog(String email,String content,String ip){
        String sql="insert into on_log(id,email,content,time,ip) values(?,?,?,now(),?)";
        template.update(sql,new Object[]{UUIDUtils.getId(),email,content,ip});
    }
}
