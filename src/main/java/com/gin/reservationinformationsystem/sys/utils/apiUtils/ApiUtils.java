package com.gin.reservationinformationsystem.sys.utils.apiUtils;

import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 接口工具类
 * @author bx002
 */
public class ApiUtils {

    public static HashMap<String, ApiInfo> parseController(Class<?> clazz) {
        List<String> rootPath = getApiPath(clazz);
        if (rootPath.size() == 0) {
            return new HashMap<>();
        }
        HashMap<String,ApiInfo> map = new HashMap<>();

//        过滤出有请求路径的方法
        List<Method> methods = Arrays.stream(clazz.getDeclaredMethods()).filter(method -> getApiPath(method).size() > 0).collect(Collectors.toList());
        for (Method method : methods) {
            List<String> methodPath = getApiPath(method);
            for (String p1 : rootPath) {
                for (String p2 : methodPath) {
                    String path = String.format("/%s/%s", p1, p2).replace("//", "/");
                    ApiInfo apiInfo = new ApiInfo(path);
                    RequiresPermissions requiresPermissions = method.getAnnotation(RequiresPermissions.class);
                    if (requiresPermissions != null) {
                        apiInfo.setLogical(requiresPermissions.logical());
                        apiInfo.setRequiresPermissions(requiresPermissions.value());
                    }
                    final ApiOperation apiOperation = method.getAnnotation(ApiOperation.class);
                    if (apiOperation != null) {
                        apiInfo.setOperation(apiOperation.value());
                    }
                    map.put(path, apiInfo);
                }
            }
        }

        return map;
    }

    public static List<ApiInfo> getAvailableApis(Class<?> clazz) {
        return parseController(clazz).values().stream().filter(ApiInfo::hasPermission).collect(Collectors.toList());
    }


    private static List<String> getApiPath(AnnotatedElement annotatedElement) {
        RequestMapping a1 = annotatedElement.getAnnotation(RequestMapping.class);
        if (a1 != null) {
            return Arrays.asList(a1.value());
        }
        PostMapping a2 = annotatedElement.getAnnotation(PostMapping.class);
        if (a2 != null) {
            return Arrays.asList(a2.value());
        }
        GetMapping a3 = annotatedElement.getAnnotation(GetMapping.class);
        if (a3 != null) {
            return Arrays.asList(a3.value());
        }
        return new ArrayList<>();
    }
}
