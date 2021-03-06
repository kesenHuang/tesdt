package com.kesen.tech.messagegateway.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @className: com.kesen.tech.messagegateway.anno-> DistriLimitAnno
 * @description:
 * @author: kesen
 * @createDate: 2022-01-04 17:28
 * @version: 1.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DistriLimitAnno {
    String limitKey() default "limit";

    int limit() default 1;
}