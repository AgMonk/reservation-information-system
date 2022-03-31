package com.gin.reservationinformationsystem.module.merchant.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Index;
import com.gitee.sunchenbin.mybatis.actable.annotation.IsKey;
import com.gitee.sunchenbin.mybatis.actable.annotation.TableComment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

/**
 * 商户持有的标签
 * @author bx002
 */
@Data
@TableName(value = "t_merchant_relation_merchant_tag", autoResultMap = true)
@TableComment("商户持有的标签")
@NoArgsConstructor
@AllArgsConstructor
public class RelationMerchantTagPo implements Serializable {
    @TableId
    @IsKey
    @Column(length = 36, isNull = false)
    String uuid;

    @Column(comment = "商户UUID", length = 36, isNull = false)
    @Index
    String merchantUuid;

    @Column(comment = "标签UUID", length = 36, isNull = false)
    @Index
    String tagUuid;


    public void createUuid() {
        this.uuid = UUID.randomUUID().toString();
    }
}