package com.gin.reservationinformationsystem.module.merchant.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gin.reservationinformationsystem.module.merchant.bo.tag.MerchantTagBo;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.IsKey;
import com.gitee.sunchenbin.mybatis.actable.annotation.TableComment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * 商户
 * @author bx002
 */
@Data
@TableName(value = "t_merchant_entity_merchant", autoResultMap = true)
@TableComment("商户")
@NoArgsConstructor
@AllArgsConstructor
public class MerchantPo implements Serializable {
    public static final String STATUS_NORMAL = "正常";
    public static final String STATUS_PAUSE = "停用";

    @TableId
    @IsKey
    @Column(length = 36, isNull = false)
    String uuid;

    @Column(comment = "名称", length = 60, isNull = false)
    String name;

    @Column(comment = "商户类型uuid", length = 36, isNull = false)
    String typeUuid;

    @Column(comment = "电话", length = 30, isNull = false)
    String phone;

    @Column(comment = "地址", length = 200, isNull = false)
    String address;

    @Column(comment = "入驻日期", isNull = false)
    Long timestampCreated;

    @Column(comment = "地区", length = 100, isNull = false)
    String area;

    @Column(comment = "纬度",  decimalLength = 4)
    Double latitude;

    @Column(comment = "经度",  decimalLength = 4)
    Double longitude;

    @Column(comment = "状态", length = 20, isNull = false)
    String status;

    @Column(comment = "头像地址", length = 200)
    String avatar;

    @Column(comment = "介绍", length = 1000, isNull = false)
    String description;

    @TableField(exist = false)
    List<MerchantTagBo> tags;

    public void createUuid() {
        this.uuid = UUID.randomUUID().toString();
    }
}