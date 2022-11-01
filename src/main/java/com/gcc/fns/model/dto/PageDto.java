package com.gcc.fns.model.dto;

import lombok.Data;

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
}
