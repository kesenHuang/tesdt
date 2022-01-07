package com.kesen.tech.messagegateway.common.enums;

/**
 * @className: com.kesen.tech.messagegateway.common.enums-> Token
 * @description: 令牌
 * @author: kesen
 * @createDate: 2022-01-05 11:28
 * @version: 1.0
 */
public enum Token {
    SUCCESS,
    FAILED;

    public boolean isSuccess() {
        return this.equals(SUCCESS);
    }

    public boolean isFailed() {
        return this.equals(FAILED);
    }
}
