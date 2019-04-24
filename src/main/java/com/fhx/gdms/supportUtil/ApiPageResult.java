package com.fhx.gdms.supportUtil;

import lombok.Data;

@Data
public class ApiPageResult extends ApiResult {
    /**
     * 一页显示数量
     */
    private Integer size;

    /**
     * 页数
     */
    private Integer page;

    /**
     * 总页数
     */
    private Integer totalPage;

    /**
     * 上一页
     */
    private Boolean pageUp;

    /**
     * 下一页
     */
    private Boolean pageDown;

    public ApiPageResult(Object data, Integer total, Integer page, Integer size) {

        if (page != null && size != null) {
            this.totalPage = total / size;
            if (total % size > 0) {
                this.totalPage++;
            }

            if (page != 0 && page <= this.totalPage - 1) {
                this.pageUp = true;
            } else {
                this.pageUp = false;
            }

            if (page < this.totalPage - 1 && page >= 0) {
                this.pageDown = true;
            } else {
                this.pageDown = false;
            }

            this.totalPage = total / size;
            if (total % size > 0) {
                this.totalPage++;
            }

            this.page = page;
            this.size = size;
        }
        this.setData(data);
    }
}
