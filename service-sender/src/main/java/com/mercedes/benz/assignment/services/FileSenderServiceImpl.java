package com.mercedes.benz.assignment.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercedes.benz.assignment.exceptions.EncryptionException;
import com.mercedes.benz.assignment.model.ApiResponse;
import com.mercedes.benz.assignment.model.MessageInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.jms.Queue;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
public class FileSenderServiceImpl implements FileSenderService {

    private Logger logger = LoggerFactory.getLogger(FileSenderServiceImpl.class);

    @Autowired
    private EncryptionService encryptionService;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired @Qualifier("soreQueue")
    private Queue storeQueue;

    @Autowired @Qualifier("updateQueue")
    private Queue updateQueue;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void send(MessageInput message) {
        String content = serialize(message);
        try{
            String encryptedContent = encryptionService.encrypt(content);
            logger.info("Encryption successful");
            jmsTemplate.convertAndSend(storeQueue, encryptedContent);
        } catch (EncryptionException e) {
            logger.error("Error while encrypt and send {}", e.getMessage(), e);
        }
    }

    private String serialize(MessageInput message) {
        try {
            return mapper.writeValueAsString(message);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public ApiResponse read(MessageInput message) {
        logger.info("Read message : {}", message);
        String svcUrl = "http://localhost:8989/v1/data/read";
        HttpEntity<MessageInput> entity = new HttpEntity<MessageInput>(message);
        ResponseEntity<ApiResponse> response = restTemplate.exchange(svcUrl, HttpMethod.POST, entity, ApiResponse.class);
        logger.info("response: {}", response);
        if(response.getStatusCode().equals(HttpStatus.OK)){
            logger.info("Read message from other API success");
            return response.getBody();
        }
        return new ApiResponse();
    }

    @Override
    public void update(MessageInput message) {
        String content = serialize(message);
        try{
            String encryptedContent = encryptionService.encrypt(content);
            logger.info("Encryption successful");
            jmsTemplate.convertAndSend(updateQueue, encryptedContent);
        } catch (EncryptionException e) {
            logger.error("Error while encrypt and send {}", e.getMessage(), e);
        }
    }
}
