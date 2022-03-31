package com.gin.reservationinformationsystem.sys.shiro;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * shiro运行参数
 * @author bx002
 */
@Repository
@ConfigurationProperties(prefix = "shiro.props")
@Data
public class ShiroProperties implements Serializable {
    Integer iterations;
}
