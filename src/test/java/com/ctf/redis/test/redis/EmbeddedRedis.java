package com.ctf.redis.test.redis;


import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@ExtendWith(EmbeddedRedisExtension.class)
public @interface EmbeddedRedis{

    enum FlushStrategy {
        AFTER_EACH_TEST,
        BEFORE_EACH_TEST,
        NEVER
    }

    FlushStrategy flushStrategy();

}