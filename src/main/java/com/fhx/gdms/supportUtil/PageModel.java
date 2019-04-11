package com.fhx.gdms.supportUtil;

import lombok.Data;

import java.util.List;

@Data
public class PageModel {
    /**
     * 一页显示数量
     */
    private Integer size;

    /**
     * 页数
     */
    private Integer page;

    /**
     * 上一页
     */
    private Boolean pageUp;

    /**
     * 下一页
     */
    private Boolean pageDown;

    List<Object> container;
}
