package com.ctf.redis.service;

import com.ctf.redis.model.Message;
import com.ctf.redis.model.repository.MessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RedisService {

    private static final Logger LOG = LoggerFactory.getLogger(RedisService.class);

    private MessageRepository messageRepository;

    public RedisService(final MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public void createMessage(final String id, final String author, final String content) {
        final Message message = Message.builder()
                .id(id)
                .author(author)
                .content(content)
                .build();

        LOG.info("Saving Message with Id {}", id);
        this.messageRepository.save(message);
    }

    public Optional<Message> getMessageById(final String id) {
        return this.messageRepository.findById(id);
    }

    public Iterable<Message> getAllMessages() {
        return this.messageRepository.findAll();
    }

    public void removeMessage(final String id) {
        this.messageRepository.deleteById(id);
    }

}
