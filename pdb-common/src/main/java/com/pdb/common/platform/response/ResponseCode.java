package com.pdb.common.platform.response;

/**
 * 错误码文档
 */

public enum ResponseCode {

    // 公共
    API_99999(9999, "操作异常，请重新尝试"),
    API_00000(200, "操作成功"),
    API_00200(200, "操作成功"),
    API_OK(200, "操作成功"),
    SUCCESS(200, "操作成功"),
    FAIL(400, "fail"),

    ;

    private int code;
    private String msg;

    ResponseCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static ResponseCode getByCode(int code) {
        for (ResponseCode ec : values()) {
            if (ec.getCode() == code) {
                return ec;
            }
        }
        return null;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
