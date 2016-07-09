package org.blusno.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Aspect
public class LoggingAspect {

    private static Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

    private String logStr = null;

    /**
     * 定义切点
     */
    @Pointcut("execution(* org.blusno..*.*(..))")
    public void logging() {
    }

    /**
     * 执行前输出
     *
     * @param joinPoint 切点
     */
    @Before("logging()")
    public void logBefore(JoinPoint joinPoint) {
        logStr = joinPoint.getTarget().getClass().getName() + " 类的 " + joinPoint.getSignature().getName() +
                " 方法开始执行 ***Start***";
        LOGGER.info(logStr);
    }

    /**
     * 执行后输出
     *
     * @param joinPoint 切点
     */
    @After("logging()")
    public void logAfter(JoinPoint joinPoint) {
        logStr = joinPoint.getTarget().getClass().getName() + " 类的 " + joinPoint.getSignature().getName() +
                " 方法执行结束 ***End***";
    }

    /**
     * @param pjp 切点
     * @return Object
     * @throws Throwable
     */
    @Around("logging()")
    public Object logAround(ProceedingJoinPoint pjp) throws Throwable {
        Object result = null;
        try {
            result = pjp.proceed();
        } catch (Exception e) {
            logStr = "方法：" + pjp.getTarget().getClass() + "." + pjp.getSignature().getName() + "()  ";
            logStr = logStr + "错误信息如下：[" + e + "]";
            LOGGER.info(logStr);
        }
        return result;
    }
}
