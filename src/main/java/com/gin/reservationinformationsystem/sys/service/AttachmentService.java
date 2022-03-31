package com.gin.reservationinformationsystem.sys.service;

import com.gin.reservationinformationsystem.sys.utils.FileUtils;
import com.gin.reservationinformationsystem.sys.utils.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.gin.reservationinformationsystem.sys.utils.StringUtils.isWindows;
import static com.gin.reservationinformationsystem.sys.utils.TimeUtils.DATE_FORMATTER;


/**
 * 需要保存上传文件的业务
 * @author bx002
 */

public interface AttachmentService {

    org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(AttachmentService.class);

    /**
     * 根目录
     * @return 根目录
     */
    default String getRootPath() {
        return String.format("%s/home", isWindows() ? "d:" : "");
    }

    /**
     * 项目名称
     * @return 项目名称
     */
    default String getProjectName() {
        return StringUtils.getProjectName();
    }

    /**
     * 项目目录
     * @return 业务目录
     */
    default String getProjectPath() {
        return String.format("%s/%s", getRootPath(), getProjectName());
    }


    /**
     * 业务名称
     * @return 业务名称
     */
    default String getServiceName() {
        return this.getClass().getName();
    }

    /**
     * 业务目录
     * @return 业务目录
     */
    default String getServicePath() {
        return String.format("%s/%s", getProjectPath(), getServiceName());
    }


    /**
     * 文件命名规则
     * @param file 文件
     * @return 文件名
     */
    default String getDestFileName(MultipartFile file) {
        return file.getOriginalFilename();
    }

    /*文件保存*/

    /**
     * 以uuid分组保存文件
     * @param file 文件
     * @param uuid uuid
     * @return 上传的文件
     * @throws IOException 异常
     */
    default File saveWithUuid(MultipartFile file, String uuid) throws IOException {
        String destPath = String.format("%s/%s/%s", getServicePath(), uuid, getDestFileName(file));
        return FileUtils.saveMultipartFile(file, new File(destPath));
    }

    /**
     * 以时间分组保存文件
     * @param file 文件
     * @return 上传的文件
     * @throws IOException 异常
     */
    default File saveWithDate(MultipartFile file) throws IOException {
        String destPath = String.format("%s/%s/%s", getServicePath(), DATE_FORMATTER.format(ZonedDateTime.now()), getDestFileName(file));
        return FileUtils.saveMultipartFile(file, new File(destPath));
    }

    /**
     * 以uuid分组查询文件列表
     * @param uuid uuid
     * @return 文件列表
     */
    default List<File> listWithUuid(String uuid) {
        String dirPath = String.format("%s/%s", getServicePath(), uuid);
        try {
            return FileUtils.listAllFiles(dirPath);
        } catch (IOException e) {
            if (!e.getMessage().startsWith("指定文件不存在")) {
                e.printStackTrace();
            }
            return new ArrayList<>();
        }
    }

    /*文件删除*/

    /**
     * 按uuid删除一个文件
     * @param uuid     uuid
     * @param filename 文件名
     * @return 是否删除成功
     */
    default boolean deleteWithUuid(String uuid, String filename) {
        String destPath = String.format("%s/%s/%s", getServicePath(), uuid, filename);
        return FileUtils.deleteFile(new File(destPath));
    }

    /**
     * 按路径删除一个文件
     * @param path 路径
     * @return 是否删除成功
     */
    default boolean deleteWithPath(String path) {
        String destPath = String.format("%s/%s", getServicePath(), path);
        return FileUtils.deleteFile(new File(destPath));
    }

}
