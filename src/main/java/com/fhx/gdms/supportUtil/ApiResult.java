package com.fhx.gdms.supportUtil;

import lombok.Data;

@Data
public class ApiResult {
    private int code;
    private String msg;
    private Object data;
}
