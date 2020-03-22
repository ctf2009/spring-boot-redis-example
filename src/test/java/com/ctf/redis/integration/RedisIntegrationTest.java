package com.ctf.redis.integration;

import com.ctf.redis.model.repository.MessageRepository;
import com.ctf.redis.service.RedisService;
import com.ctf.redis.test.utils.redis.EmbeddedRedis;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;

import static com.ctf.redis.test.utils.model.TestDataUtils.*;
import static com.ctf.redis.test.utils.redis.EmbeddedRedis.FlushStrategy;
import static java.time.temporal.ChronoUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

@EmbeddedRedis(flushStrategy = FlushStrategy.AFTER_EACH_TEST)
@SpringBootTest
public class RedisIntegrationTest {

    @Autowired
    private RedisService redisService;

    @Autowired
    private MessageRepository messageRepository;

    @Test
    public void successfullyStoredMessage() {
        givenDefaultMessageIsAdded();
        thenRedisShouldHaveEntryWithId(DEFAULT_ID);
    }

    @Test
    public void successfullyFlushedBetweenTests() {
        assertThat(this.messageRepository.count()).isEqualTo(0L);
    }

    private void givenDefaultMessageIsAdded() {
        this.redisService.createMessage(DEFAULT_ID, DEFAULT_AUTHOR, DEFAULT_CONTENT);
    }

    private void thenRedisShouldHaveEntryWithId(final String id) {
        await().atMost(Duration.of(10, SECONDS)).until(() -> messageRepository.findById(id) != null);
        assertThat(this.messageRepository.findById(id)).isNotNull();
    }

}
