package com.gin.reservationinformationsystem.sys.initialization;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 初始化
 * @author bx002
 */
@Component
@Slf4j
public class Initialization implements ApplicationContextAware, ApplicationRunner {
    public static ApplicationContext context;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Initialization.context = applicationContext;

    }

    @Override
    public void run(ApplicationArguments args) {
    }
}
