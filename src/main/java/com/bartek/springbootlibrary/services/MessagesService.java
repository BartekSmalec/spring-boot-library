package com.bartek.springbootlibrary.services;

import com.bartek.springbootlibrary.dao.MessageRepository;
import com.bartek.springbootlibrary.entity.Message;
import com.bartek.springbootlibrary.requestsModels.AdminQuestionRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class MessagesService {
    private MessageRepository messageRepository;

    public void postMessage(Message messageRequest, String userEmail) {

        Message message = Message.builder().title(messageRequest.getTitle()).question(messageRequest.getQuestion()).build();

        message.setUserEmail(userEmail);
        messageRepository.save(message);
    }

    public void putMessage(AdminQuestionRequest adminQuestionRequest, String userEmail) throws Exception {
        Optional<Message> message = messageRepository.findById(adminQuestionRequest.getId());
        if (!message.isPresent()) {
            throw new Exception("Message not found");
        }

        message.get().setAdminEmail(userEmail);
        message.get().setResponse(adminQuestionRequest.getResponse());
        message.get().setClosed(true);

        messageRepository.save(message.get());
    }

}
