package com.ctf.redis.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Setter
@Getter
@ConfigurationProperties(prefix = "redis")
public class RedisConfigurationProperties {

    private String hostName = "localhost";

    private int port;

}
