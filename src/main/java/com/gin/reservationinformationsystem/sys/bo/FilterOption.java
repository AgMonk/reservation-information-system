package com.gin.reservationinformationsystem.sys.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.cglib.core.internal.Function;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 过滤条件
 * @author bx002
 */
@Data
@ApiModel("过滤条件")
@AllArgsConstructor
public class FilterOption implements Serializable {
    @ApiModelProperty(value = "标签")
    String label;

    @ApiModelProperty(value = "值")
    Serializable value;

    public static <T> List<FilterOption> build(List<T> source, Function<T, FilterOption> func) {
        return source.stream().map(func::apply).collect(Collectors.toList());
    }
}