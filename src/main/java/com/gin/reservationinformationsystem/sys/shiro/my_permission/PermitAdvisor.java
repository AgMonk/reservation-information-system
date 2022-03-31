package com.gin.reservationinformationsystem.sys.shiro.my_permission;

import com.gin.reservationinformationsystem.sys.initialization.Initialization;
import com.gin.reservationinformationsystem.sys.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.apache.shiro.SecurityUtils;
import org.springframework.aop.support.StaticMethodMatcherPointcutAdvisor;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 静态自定义权限认证切面
 * @author bx002
 */
@Slf4j
public class PermitAdvisor extends StaticMethodMatcherPointcutAdvisor {

    private static final Class<? extends Annotation>[] AUTH_ANNOTATION_CLASSES = new Class[]{RequiresMyPermissions.class};


    public PermitAdvisor() {
        SpelExpressionParser parser = new SpelExpressionParser();
        // 构造一个通知，当方法上有加入Permitable注解时，会触发此通知执行权限校验
        MethodInterceptor advice = mi -> {
            Method method = mi.getMethod();
            Object targetObject = mi.getThis();
            Object[] args = mi.getArguments();
            RequiresMyPermissions requiresMyPermissions = method.getAnnotation(RequiresMyPermissions.class);
            // 前置权限认证

            checkPermission(parser, requiresMyPermissions, requiresMyPermissions.pre(), method, args, targetObject, null);
            Object proceed = mi.proceed();
            // 后置权限认证
            checkPermission(parser, requiresMyPermissions, requiresMyPermissions.post(), method, args, targetObject, proceed);
            return proceed;
        };
        setAdvice(advice);
    }


    private void checkPermission(SpelExpressionParser parser, RequiresMyPermissions requiresMyPermissions, String expr, Method method, Object[] args, Object target, Object result) {
//        表达式为空则通过
        if (StringUtils.isEmpty(expr)) {
            return;
        }
        Object resources = parser.parseExpression(expr).getValue(createEvaluationContext(method, args, target, result), Object.class);
        final String prefix = requiresMyPermissions.namespace() + ":" + requiresMyPermissions.action() + ":";
        // 调用Shiro进行权限校验
        log.info("校验权限 {} {}", prefix, resources);
        if (resources == null) {
            SecurityUtils.getSubject().checkPermission(prefix + "*");
        } else if (resources instanceof String) {
            SecurityUtils.getSubject().checkPermission(prefix + resources);
        } else if (resources instanceof List) {
            List<?> list = (List<?>) resources;
            list.stream().map(obj -> prefix + obj).forEach(SecurityUtils.getSubject()::checkPermission);
        }
    }

    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        return isAuthAnnotationPresent(method);
    }

    private boolean isAuthAnnotationPresent(Method method) {
        for (Class<? extends Annotation> annClass : AUTH_ANNOTATION_CLASSES) {
            Annotation a = AnnotationUtils.findAnnotation(method, annClass);
            if (a != null) {
                return true;
            }
        }
        return false;
    }


    /**
     * 构造SpEL表达式上下文
     */
    private EvaluationContext createEvaluationContext(Method method, Object[] args, Object target, Object result) {
        MethodBasedEvaluationContext evaluationContext = new MethodBasedEvaluationContext(
                target, method, args, new DefaultParameterNameDiscoverer());
        evaluationContext.setVariable("result", result);
        try {
            evaluationContext.registerFunction("resolve", PermissionResolver.class.getMethod("resolve", List.class));

            evaluationContext.setBeanResolver(new BeanFactoryResolver(Initialization.context));
        } catch (NoSuchMethodException e) {
            log.error("Get method error:", e);
        }
        return evaluationContext;
    }
}
