package com.gin.reservationinformationsystem.module.merchant.service;

import com.gin.reservationinformationsystem.sys.exception.BusinessException;
import com.gin.reservationinformationsystem.sys.utils.FileUtils;
import com.gin.reservationinformationsystem.sys.utils.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

import static com.gin.reservationinformationsystem.sys.utils.StringUtils.isWindows;

/**
 * @author bx002
 */
public interface IAvatarService {
    /**
     * 根目录
     * @return 根目录
     */
    default String rootPath(){
        return (isWindows()?"d:":"")+"/home";
    }

    /**
     * 目录名
     * @return 目录名
     */
    String dirName();

    /**
     * 保存头像
      * @param uuid uuid
     * @param file 文件
     * @return 保存先谷底路径
     * @throws IOException 异常
     */
    default String saveAvatar(String uuid, MultipartFile file) throws IOException {
        final String originalFilename = file.getOriginalFilename();
        if (StringUtils.isEmpty(originalFilename)){
            throw  new BusinessException(4000,"缺少文件名");
        }
        final String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));

        StringBuilder path = new StringBuilder();
        path.append("/")
                .append("avatar").append("/")
                .append(dirName()).append("/")
                .append(uuid).append(suffix);

        FileUtils.saveMultipartFile(file, new File(rootPath()+ path));

        return path.toString();
    }
}
