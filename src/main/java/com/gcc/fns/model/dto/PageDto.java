package com.gcc.fns.model.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;

/**
 * @author xiaozhi
 * @description
 * @create 2022-10-2022/10/31 9:13
 */
@Data
public class PageDto implements Serializable {

    private static final long serialVersionUID = 319122316373120793L;

    private Integer cur;

    private Integer count;

    @Min(value = 1, message = "选择正确的类型")
    @Max(value = 5, message = "选择正确的类型")
    private Integer type;
}
