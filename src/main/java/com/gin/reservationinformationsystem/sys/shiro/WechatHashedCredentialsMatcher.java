package com.gin.reservationinformationsystem.sys.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;

/**
 * 微信免密登陆 Mathcer
 * @author bx002
 */
public class WechatHashedCredentialsMatcher extends HashedCredentialsMatcher {

    public WechatHashedCredentialsMatcher() {
    }

    public WechatHashedCredentialsMatcher(String hashAlgorithmName) {
        super(hashAlgorithmName);
    }

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        MyUsernamePasswordToken t = (MyUsernamePasswordToken) token;
        return !t.isCheckPassword() || super.doCredentialsMatch(token, info);
    }
}
