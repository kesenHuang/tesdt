package com.kesen.tech.messagegateway.aop;

import com.kesen.tech.messagegateway.common.exception.enums.GlobalErrorCode;
import com.kesen.tech.messagegateway.common.pojo.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @className: com.kesen.tech.messagegateway.aop-> UnifiedErrorHandler
 * @description:
 * @author: kesen
 * @createDate: 2022-01-04 17:31
 * @version: 1.0
 */
@RestControllerAdvice
@Slf4j
public class UnifiedErrorHandler {

    @ExceptionHandler(value = Exception.class)
    public CommonResult<?> processException(HttpServletRequest req, Exception e) {
        return CommonResult.error(GlobalErrorCode.FAILED.getCode(), GlobalErrorCode.FAILED.getMessage());
    }
}
