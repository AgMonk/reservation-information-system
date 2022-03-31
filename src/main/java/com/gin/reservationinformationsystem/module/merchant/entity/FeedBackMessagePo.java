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
 * 反馈信息
 * @author bx002
 */
@Data
@TableName(value = "t_merchant_entity_feed_back_message", autoResultMap = true)
@TableComment("反馈信息")
@NoArgsConstructor
@AllArgsConstructor
public class FeedBackMessagePo implements Serializable {

    public static final String STATUS_NOT_PROCESSED = "未处理";
    public static final String STATUS_PROCESSED = "已处理";


    @TableId
    @IsKey
    @Column(length = 36, isNull = false)
    String uuid;

    @Column(comment = "商户UUID", length = 36, isNull = false)
    @Index
    String merchantUuid;

    @Column(comment = "正文", length = 2000, isNull = false)
    String content;

    @Column(comment = "创建时间", isNull = false)
    Long timestampCreated;

    @Column(comment = "状态", length = 20, isNull = false,defaultValue = STATUS_NOT_PROCESSED)
    String status;

    public void createUuid() {
        this.uuid = UUID.randomUUID().toString();
    }
}