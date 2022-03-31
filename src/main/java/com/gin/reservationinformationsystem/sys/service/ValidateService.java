package com.gin.reservationinformationsystem.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

import static com.gin.reservationinformationsystem.sys.exception.BusinessExceptionEnum.*;

/**
 * @author bx002
 */
public interface ValidateService<T> extends IService<T> {

    /**
     * 断言uuid不存在
     * @param uuid uuid
     * @return void
     */
    default void assertUuidNotExits(String uuid) {
        UUID_NOT_EMPTY.assertNotEmpty(uuid);
        RECORD_UUID_NOT_EXISTS.assertNull(getById(uuid));
    }

    /**
     * 断言uuid全部不存在
     * @param uuid uuid
     */
    default void assertUuidNotExits(List<String> uuid) {
        UUID_NOT_EMPTY.assertNotEmpty(uuid);
        RECORD_UUID_NOT_EXISTS.assertTrue(listByIds(uuid).size() == 0);
    }

    /**
     * 断言uuid已存在
     * @param uuid uuid
     * @return void
     */
    default void assertUuidExits(String uuid) {
        UUID_NOT_EMPTY.assertNotEmpty(uuid);
        RECORD_UUID_EXISTS.assertNotNull(getById(uuid));
    }

    /**
     * 断言uuid全部存在
     * @param uuid uuid
     */
    default void assertUuidExits(List<String> uuid) {
        UUID_NOT_EMPTY.assertNotEmpty(uuid);
        RECORD_UUID_EXISTS.assertTrue(listByIds(uuid).size() == uuid.size());
    }

}
