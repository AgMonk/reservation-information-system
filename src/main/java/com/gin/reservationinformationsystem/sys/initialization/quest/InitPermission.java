package com.gin.reservationinformationsystem.sys.initialization.quest;

import com.gin.reservationinformationsystem.sys.initialization.Initialization;
import com.gin.reservationinformationsystem.sys.user_info.entity.SysPermission;
import com.gin.reservationinformationsystem.sys.user_info.service.SysPermissionService;
import com.gin.reservationinformationsystem.sys.utils.apiUtils.ApiInfo;
import com.gin.reservationinformationsystem.sys.utils.apiUtils.ApiUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.AnnotatedElement;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 初始化权限
 * @author bx002
 */
@Component
@Getter
@RequiredArgsConstructor
@Slf4j
@Order(100)
public class InitPermission implements ApplicationRunner {

    private final SysPermissionService sysPermissionService;

    /**
     * 任务内容
     */
    @Override
    public void run(ApplicationArguments args) {
        log.info("权限：检查接口所需的权限是否存在");

        //        准备工作
        Map<String, Object> beans = Initialization.context.getBeansWithAnnotation(RequestMapping.class);
        Set<String> permissions = new HashSet<>();

//        遍历接口查找定义的权限
        beans.forEach((s, o) -> {
            String className = o.toString();
            className = className.substring(0, className.lastIndexOf("@"));
            try {
                Class<?> clazz = Class.forName(className);
                HashMap<String, ApiInfo> map = ApiUtils.parseController(clazz);
                map.values().stream()
                        .filter(i -> i.getLogical() != null).flatMap(i -> Arrays.stream(i.getRequiresPermissions()))
                        .forEach(permissions::add);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });


//        检查权限在数据库是否存在
//        移除数据库中已存在的权限
        sysPermissionService.list().stream().map(SysPermission::getUuid).collect(Collectors.toList()).forEach(permissions::remove);

        if (permissions.size() > 0) {
            List<SysPermission> p = permissions.stream().map(SysPermission::parse).filter(SysPermission::validate).collect(Collectors.toList());
            if (p.size() > 0) {
                sysPermissionService.saveBatch(p);
                log.info("缺少权限 {} 个 已添加", p.size());
            }
        }
    }

    private static String[] getApiPath(AnnotatedElement annotatedElement) {
        RequestMapping a1 = annotatedElement.getAnnotation(RequestMapping.class);
        PostMapping a2 = annotatedElement.getAnnotation(PostMapping.class);
        GetMapping a3 = annotatedElement.getAnnotation(GetMapping.class);
        if (a1 != null) {
            return a1.value();
        }
        if (a2 != null) {
            return a2.value();
        }
        if (a3 != null) {
            return a3.value();
        }
        return null;
    }
}
