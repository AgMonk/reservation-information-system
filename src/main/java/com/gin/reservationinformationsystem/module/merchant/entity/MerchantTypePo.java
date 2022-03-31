package com.gin.reservationinformationsystem.module.merchant.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.IsKey;
import com.gitee.sunchenbin.mybatis.actable.annotation.TableComment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

/**
 * 商户类型
 * @author bx002
 */
@Data
@TableName(value = "t_merchant_entity_type", autoResultMap = true)
@TableComment("商户类型")
@NoArgsConstructor
@AllArgsConstructor
public class MerchantTypePo implements Serializable {
    @TableId
    @IsKey
    @Column(length = 36, isNull = false)
    String uuid;

    @Column(comment = "名称", length = 60, isNull = false)
    String name;

    @Column(comment = "头像地址", length = 200)
    String avatar;

    public void createUuid() {
        this.uuid = UUID.randomUUID().toString();
    }

    public MerchantTypePo(String name) {
        this.name = name;
    }
}