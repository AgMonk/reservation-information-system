package com.gin.reservationinformationsystem.sys.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 分页查询参数
 * @author bx002
 */
@Getter
@Setter
@ApiModel("分页查询条件")
@Validated
public class PageParams<T extends IFilter> implements Serializable {
    @ApiModelProperty(value = "当前页", example = "1")
    @Min(value = 1L, message = "页码最小为1")
    Integer page = 1;
    @ApiModelProperty(value = "每页条数", example = "10")
    @Min(value = 10L, message = "每页数量最小为10")
    @Max(value = 50L, message = "每页数量最大为50")
    Integer size = 10;
    @ApiModelProperty(value = "其他过滤条件", required = true)
    @NotNull(message = "过滤条件可以为空对象，不能为null")
    @Valid
    T filter;
}
