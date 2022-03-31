package com.gin.reservationinformationsystem.sys.shiro;

import org.apache.shiro.crypto.hash.Md5Hash;

import java.util.UUID;

/**
 * Shiro工具类
 * @author bx002
 */
public class ShiroUtils {

    public static String createSalt() {
        return UUID.randomUUID().toString().substring(0, 8);
    }

    /**
     * 执行md5计算
     * @param s              待编码字符串
     * @param salt           盐
     * @param hashIterations 散列次数
     * @return md5
     */
    public static String doMd5(String s, String salt, Integer hashIterations) {
        return new Md5Hash(s, salt, hashIterations).toHex();
    }
}
