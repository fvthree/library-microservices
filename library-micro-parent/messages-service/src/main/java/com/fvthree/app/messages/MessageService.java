package com.fvthree.app.messages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MessageService {

    @Autowired
    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Message postMessage(Map<?, ?> request) {

        Message message = Message.builder()
                .title(request.get("title").toString())
                .question(request.get("question").toString())
                .userEmail(request.get("user_email").toString())
                .closed(false)
                .build();

        return messageRepository.save(message);
    }

    public Message closeMessage(Map<?,?> request) {

        Message message = messageRepository.findById(Long.parseLong(request.get("message_id").toString()))
                .orElseThrow(() -> new RuntimeException("message not found."));

        message.setAdminEmail(request.get("admin_email").toString());
        message.setResponse(request.get("response").toString());
        message.setClosed(true);

        return messageRepository.save(message);
    }


}
