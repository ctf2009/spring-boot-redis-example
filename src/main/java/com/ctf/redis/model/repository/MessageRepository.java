package com.ctf.redis.model.repository;

import com.ctf.redis.model.Message;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends CrudRepository<Message, String> {


}
