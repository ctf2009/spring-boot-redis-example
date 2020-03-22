package com.ctf.redis.controller;

import com.ctf.redis.model.Message;
import com.ctf.redis.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class RedisController {

    private RedisService redisService;

    @Autowired
    public RedisController(final RedisService redisService) {
        this.redisService = redisService;
    }

    private static final Logger LOG = LoggerFactory.getLogger(RedisController.class);

    // Should be Post but wanted to make it simple to perform from browser
    @GetMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addMessage(
            @RequestParam final String id,
            @RequestParam final String author,
            @RequestParam final String content) {
        LOG.info("Received addMessage request with id {}", id);
        this.redisService.createMessage(id, author, content);
        return ResponseEntity.ok("OK");
    }

    @GetMapping(value =  { "/get", "/get/{id}" }, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getMessage(@PathVariable final Optional<String> id) {
        if (id.isPresent()) {
            LOG.info("Getting Message with Id {}", id.get());
            final Optional<Message> message = this.redisService.getMessageById(id.get());

            if (message.isPresent()) {
                return  ResponseEntity.ok(message.get());
            } else {
                return new ResponseEntity("No Message Found", HttpStatus.NOT_FOUND);
            }

        } else {
            LOG.info("Getting All Messages");
            return ResponseEntity.ok(this.redisService.getAllMessages());
        }
    }

    // Should be Delete but wanted to make it simple to perform from browser
    @GetMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity removeMessage(@PathVariable final String id) {
        LOG.info("Getting Message with Id {}", id);
        this.redisService.removeMessage(id);
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }

}
