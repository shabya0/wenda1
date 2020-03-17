package com.nowcoder.wenda.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.codehaus.plexus.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {
    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Before("execution(* com.nowcoder.wenda.controller.*Controller.*(..))")
    public void beforeMethod(JoinPoint joinPoint){
        logger.info("before------------------------------------------------------");
        logger.info(joinPoint.toLongString());
        StringBuffer sb = new StringBuffer();
        logger.info("argsLength:     "+String.valueOf(joinPoint.getArgs().length));
        for(Object arg: joinPoint.getArgs()){
            if(arg==null)   continue;
            sb.append("arg:"+arg.toString()+"  |  ");
        }
        logger.info("before:   "+sb.toString());
    }

    @After("execution( * com.nowcoder.wenda.controller.*Controller.*(..))")
    public void afterMethod(){
        logger.info("after method------------------------------------------------------");
    }
}
