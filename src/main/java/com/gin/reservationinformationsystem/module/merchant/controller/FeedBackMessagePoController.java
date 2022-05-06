package com.gin.reservationinformationsystem.module.merchant.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gin.reservationinformationsystem.module.merchant.bo.FeedBackMessagePo4Create;
import com.gin.reservationinformationsystem.module.merchant.bo.Filter4FeedBackMessagePo;
import com.gin.reservationinformationsystem.module.merchant.entity.FeedBackMessagePo;
import com.gin.reservationinformationsystem.module.merchant.service.FeedBackMessagePoService;
import com.gin.reservationinformationsystem.module.merchant.service.MerchantPoService;
import com.gin.reservationinformationsystem.sys.request.PageParams;
import com.gin.reservationinformationsystem.sys.response.Res;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.gin.reservationinformationsystem.module.merchant.entity.FeedBackMessagePo.STATUS_NOT_PROCESSED;
import static com.gin.reservationinformationsystem.module.merchant.entity.FeedBackMessagePo.STATUS_PROCESSED;

/**
 * 反馈信息管理接口
 * @author bx002
 */
@RestController
@Slf4j
@Validated
@RequiredArgsConstructor
@RequestMapping("merchant/feedback")
@Api(tags = FeedBackMessagePoController.NAMESPACE + "相关接口")
@Transactional(rollbackFor = Exception.class)
public class FeedBackMessagePoController {
    public static final String NAMESPACE = "反馈信息";

    private final FeedBackMessagePoService service;
    private final MerchantPoService merchantPoService;

    @PostMapping("add")
    @ApiOperation(value = "添加" + NAMESPACE)
    public Res<Void> add(@RequestBody @Validated FeedBackMessagePo4Create entity) {
        merchantPoService.assertUuidExits(entity.getMerchantUuid());
        service.save(entity.toFeedBackMessagePo());
        return Res.success("反馈成功");
    }

    @PostMapping("getNotProcessed")
    @ApiOperation(value = "获取未处理反馈数量")
    public Res<Long> getNotProcessed() {
        QueryWrapper<FeedBackMessagePo> qw = new QueryWrapper<>();
        qw.eq("status",STATUS_NOT_PROCESSED);
        qw.orderByDesc("timestamp_created");
        return Res.success("成功",service.count(qw));
    }

    @PostMapping("complete/{uuid}")
    @ApiOperation(value = "标记反馈信息为已处理")
    public Res<Void> complete(@PathVariable String uuid) {
        service.assertUuidExits(uuid);
        final FeedBackMessagePo entity = new FeedBackMessagePo();
        entity.setUuid(uuid);
        entity.setStatus(STATUS_PROCESSED);
        service.updateById(entity);
        return Res.success("标记反馈信息为已处理成功");
    }

    @PostMapping("page")
    @RequiresPermissions(NAMESPACE + ":查询:分页")
    @ApiOperation(value = "查询分页" + NAMESPACE)
    public Res<Page<FeedBackMessagePo>> page(@RequestBody @Validated PageParams<Filter4FeedBackMessagePo> param) {
        QueryWrapper<FeedBackMessagePo> qw = new QueryWrapper<>();
//      应用过滤条件
        qw.orderByDesc("timestamp_created");

//        查询
        Page<FeedBackMessagePo> pageData = service.pageWithParams(param, qw);
//      后续处理
        List<FeedBackMessagePo> records = pageData.getRecords();

        return Res.success("查询" + NAMESPACE + "分页数据成功", pageData);
    }
}
