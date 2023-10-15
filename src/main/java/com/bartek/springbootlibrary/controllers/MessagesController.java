package com.bartek.springbootlibrary.controllers;

import com.bartek.springbootlibrary.entity.Message;
import com.bartek.springbootlibrary.requestsModels.AdminQuestionRequest;
import com.bartek.springbootlibrary.services.MessagesService;
import com.bartek.springbootlibrary.utils.ExtractJWT;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/messages")
@AllArgsConstructor
public class MessagesController {
    private MessagesService messagesService;
    public static final String SUB = "\"sub\"";
    public static final String USER_TYPE = "\"userType\"";


    @PostMapping("/secure/add/message")
    public void postMessage(@RequestHeader(value = "Authorization") String auth, @RequestBody Message message) {
        String userEmail = ExtractJWT.payloadJWTExtraction(auth, SUB);
        messagesService.postMessage(message, userEmail);
    }

    @PutMapping("/secure/admin/message")
    public void putMessage(@RequestHeader(value = "Authorization") String auth,
                           @RequestBody AdminQuestionRequest request) throws Exception {
        String userEmail = ExtractJWT.payloadJWTExtraction(auth, SUB);
        String admin = ExtractJWT.payloadJWTExtraction(auth, USER_TYPE);

        if (admin == null || !admin.equals("admin")) {
            throw new Exception("Administrattion page only");
        }

        messagesService.putMessage(request, userEmail);
    }
}
