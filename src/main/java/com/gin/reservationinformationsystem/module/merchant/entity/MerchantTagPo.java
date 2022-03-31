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
 * 商户标签
 * @author bx002
 */
@Data
@TableName(value = "t_merchant_entity_tag", autoResultMap = true)
@TableComment("商户标签")
@NoArgsConstructor
@AllArgsConstructor
public class MerchantTagPo implements Serializable {
    @TableId
    @IsKey
    @Column(length = 36, isNull = false)
    String uuid;

    @Column(comment = "名称", length = 60, isNull = false)
    String name;

    public void createUuid() {
        this.uuid = UUID.randomUUID().toString();
    }
}