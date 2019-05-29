package login.com.supportUtil;

import lombok.Data;

@Data
public class PageBaseModel {
    /**
     * 一页显示数量
     */
    private Integer size;

    /**
     * 页数
     */
    private Integer page;
}
