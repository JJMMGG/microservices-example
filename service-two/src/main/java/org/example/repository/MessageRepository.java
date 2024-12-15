package org.example.repository;

import org.example.entity.Message;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends MongoRepository<Message, String> {

    @Query("{'name' : ?0}")
    List<Message> findByName(String appName);

    @Query("{'name' : {$ne : ?0}}")
    List<Message> findReceivedMessages(String appName);
}
