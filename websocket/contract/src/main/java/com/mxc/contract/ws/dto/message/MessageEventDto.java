package com.mxc.contract.ws.dto.message;


import com.mxc.contract.ws.common.Channel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * channel 与dto 映射注解
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface MessageEventDto {
    /**
     * 标记 dto 对应的channel
     */
    Channel channel();

    /**
     * 标记dto数据是否是数组
     */
    boolean isList() default false;
}
