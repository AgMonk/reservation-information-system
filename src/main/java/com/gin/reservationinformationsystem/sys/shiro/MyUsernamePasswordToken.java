package com.gin.reservationinformationsystem.sys.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * 可以选择免密登陆的token
 * @author bx002
 */
public class MyUsernamePasswordToken extends UsernamePasswordToken {
    /**
     * 免密登陆
     */
    boolean checkPassword;

    public MyUsernamePasswordToken(String username, String password, boolean checkPassword) {
        super(username, password);
        this.checkPassword = checkPassword;
    }

    public boolean isCheckPassword() {
        return checkPassword;
    }

    public void setCheckPassword(boolean checkPassword) {
        this.checkPassword = checkPassword;
    }
}
