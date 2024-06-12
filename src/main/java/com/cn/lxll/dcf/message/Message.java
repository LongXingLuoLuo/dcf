package com.cn.lxll.dcf.message;

import com.alibaba.fastjson2.JSONObject;

/**
 * Project dcf
 *
 * @author Lxll
 */
public enum Message {
    SUCCESS(200, "成功"), FAIL(400, "失败"), UNAUTHORIZED(401, "未授权"), FORBIDDEN(403, "禁止访问"), NOT_FOUND(404, "未找到"), INTERNAL_SERVER_ERROR(500, "服务器内部错误"),
    BAD_GATEWAY(502, "网关错误"), SERVICE_UNAVAILABLE(503, "服务不可用"), GATEWAY_TIMEOUT(504, "网关超时"), NOT_IMPLEMENTED(501, "未实现");

    private final int code;

    private final String message;

    Message(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public JSONObject toJSONObject() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", code);
        jsonObject.put("message", message);
        return jsonObject;
    }
}
