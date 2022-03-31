package com.gin.reservationinformationsystem.sys.user_info.inter;

/**
 * 含有用户字段
 * @author bx002
 */
public interface HasUser {
    String getUserUuid();

    void setUserUuid(String uuid);

    String getUserName();

    void setUserName(String name);
}
