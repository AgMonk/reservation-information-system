package com.gin.reservationinformationsystem.sys.shiro;

import com.gin.reservationinformationsystem.sys.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.session.mgt.WebSessionKey;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import java.io.Serializable;

/**
 * 自定义ShiroSession管理器
 * @author bx002
 */
@Slf4j
@Component(value = "sessionManager")
public class ShiroSessionManager extends DefaultWebSessionManager {

    public ShiroSessionManager() {
        //自定义shiro使用的Cookie key 避开与tomcat的sessionId的冲突
        SimpleCookie sessionIdCookie = new SimpleCookie(StringUtils.getProjectName() + "_sessionId");
        sessionIdCookie.setMaxAge(7 * 24 * 60 * 60);
//        sessionIdCookie.setMaxAge(Math.toIntExact(TimeUnit.getDurationOfDay(7)) / 1000);
        sessionIdCookie.setPath("/");
        sessionIdCookie.setHttpOnly(false);

        //自定义cookie 中sessionId 的key
        setSessionIdCookieEnabled(true);
        setSessionIdCookie(sessionIdCookie);
        //删除过期session
        setDeleteInvalidSessions(true);
        //设置session 过期时间
        setGlobalSessionTimeout(30 * 60 * 1000);
        setSessionValidationSchedulerEnabled(true);
        //保存session到redis
//        setSessionDAO(redisSessionDao);
    }

    /**
     * 重写该方法以解决一次请求中多次读取Redis的问题
     * @param sessionKey sessionKey
     * @return Session
     */
    @Override
    protected Session retrieveSession(SessionKey sessionKey) {
        Serializable sessionId = getSessionId(sessionKey);
        ServletRequest request = null;
        if (sessionKey instanceof WebSessionKey) {
            request = ((WebSessionKey) sessionKey).getServletRequest();
        }
        if (request != null && sessionId != null) {
            Session session = (Session) request.getAttribute(sessionId.toString());
            if (session != null) {
                return session;
            }
        }
        Session session = super.retrieveSession(sessionKey);
        if (request != null && sessionId != null) {
            request.setAttribute(sessionId.toString(), session);
        }
        return session;
    }


}
