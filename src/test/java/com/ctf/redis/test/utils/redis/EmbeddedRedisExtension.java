package com.ctf.redis.test.utils.redis;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.SocketUtils;
import redis.embedded.RedisServer;

import static com.ctf.redis.test.utils.redis.EmbeddedRedis.FlushStrategy;

public class EmbeddedRedisExtension implements BeforeAllCallback, AfterAllCallback, AfterEachCallback, BeforeEachCallback {

    private static final Logger LOG = LoggerFactory.getLogger(EmbeddedRedisExtension.class);

    private static final String PROPERTY_NAME = "embedded.redis.value";

    private RedisServer redisServer;
    private StatefulRedisConnection<?, ?> redisConnection;

    private FlushStrategy flushStrategy;

    @Override
    public void beforeAll(ExtensionContext context) {
        checkAndProcessAnnotation(context);

        int port = getPortAndSetSystemProperty();

        this.redisServer = new RedisServer(port);
        this.redisServer.start();

        this.redisConnection = RedisClient.create("redis://localhost:" + port).connect();
        LOG.info("Embedded Redis is now running on port {}", port);

    }

    @Override
    public void beforeEach(ExtensionContext context) {
        if (this.flushStrategy.equals(FlushStrategy.BEFORE_EACH_TEST)) {
            LOG.info("Flushing Embedded Redis");
            this.redisConnection.sync().flushall();
        }
    }

    @Override
    public void afterAll(ExtensionContext context) {
        System.clearProperty(PROPERTY_NAME);
        this.redisConnection.close();
        this.redisServer.stop();
    }

    @Override
    public void afterEach(ExtensionContext context) {
        if (this.flushStrategy.equals(FlushStrategy.AFTER_EACH_TEST)) {
            LOG.info("Flushing Embedded Redis");
            this.redisConnection.sync().flushall();
        }
    }

    private int getPortAndSetSystemProperty() {
        final int randomPort = SocketUtils.findAvailableTcpPort(1001);
        System.setProperty(PROPERTY_NAME, String.valueOf(randomPort));

        return randomPort;
    }

    private void checkAndProcessAnnotation(final ExtensionContext context) {
        context.getElement().ifPresent(c -> {
            final EmbeddedRedis annotation = c.getAnnotation(EmbeddedRedis.class);
            if (annotation != null) {
                this.flushStrategy = annotation.flushStrategy();
            }
        });
    }

}
