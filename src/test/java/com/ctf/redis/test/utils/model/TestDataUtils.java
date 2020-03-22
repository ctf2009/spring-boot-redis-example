package com.ctf.redis.test.utils.model;

import com.ctf.redis.model.Message;

public class TestDataUtils {

    public static final String DEFAULT_ID = "default_id";
    public static final String DEFAULT_AUTHOR = "default_author";
    public static final String DEFAULT_CONTENT = "default_content";

    public static Message defaultMessage() {
        return Message.builder()
                .id(DEFAULT_ID)
                .author(DEFAULT_AUTHOR)
                .content(DEFAULT_CONTENT)
                .build();
    }

}
