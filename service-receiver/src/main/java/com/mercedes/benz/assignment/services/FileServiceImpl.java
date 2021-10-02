package com.mercedes.benz.assignment.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercedes.benz.assignment.constants.FileType;
import com.mercedes.benz.assignment.model.MessageInput;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

@Service
public class FileServiceImpl implements FileService{
    private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    private String storePath;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private EncryptionService encryptionService;

    @Autowired
    public FileServiceImpl(@Value("${data.store.path}")String path){
        this.storePath = path;
    }

    @JmsListener(destination = "${message.store.queue}")
    public void storeMessage(String encryptedContent) {
        logger.info("storeMessage() - Message received from activemq queue---"+encryptedContent);
        try {
            String decryptedContent = encryptionService.decrypt(encryptedContent);
            logger.info("Decryption successful");
            MessageInput message2 = deserialize(decryptedContent);
            logger.info("Deserialization success: {}", message2);
            store(message2);
        } catch (Exception e) {
            logger.error("Error while decrypting queue message: {}", e.getMessage(), e);
        }
    }

    @JmsListener(destination = "${message.update.queue}")
    public void updateMessage(String encryptedContent) {
        logger.info("updateMessage() - Message received from activemq queue---"+encryptedContent);
        try {
            String decryptedContent = encryptionService.decrypt(encryptedContent);
            logger.info("Decryption successful");
            MessageInput message2 = deserialize(decryptedContent);
            logger.info("Deserialization success: {}", message2);
            update(message2);
        } catch (Exception e) {
            logger.error("Error while decrypting queue message: {}", e.getMessage(), e);
        }
    }

    @Override
    public void update(MessageInput message) {
        File file = new File(getFilename(message));
        if(!file.exists()){
            logger.error("File doesn't exists with name: {}", file.getName());
            return;
        }

        try(FileOutputStream fos = new FileOutputStream(file);){
            fos.write(message.getContent().getBytes());
            fos.flush();
            logger.info("Successfully file updated with name: {}", file.getName());
        } catch (FileNotFoundException e) {
            logger.error("Error while update: {}", e.getMessage(), e);
        } catch (IOException e) {
            logger.error("Error while update: {}", e.getMessage(), e);
        }
    }

    @Override
    public void store(MessageInput messageInput) {
        logger.info("store() called");
        File file = new File(getFilename(messageInput));
        try(FileOutputStream fos = new FileOutputStream(file);){
            fos.write(messageInput.getContent().getBytes());
            fos.flush();
            logger.info("Successfully file saved with name: {}", file.getName());
        } catch (FileNotFoundException e) {
            logger.error("Error while store: {}", e.getMessage(), e);
        } catch (IOException e) {
            logger.error("Error while store: {}", e.getMessage(), e);
        }
    }

    private String getFilename(MessageInput messageInput){
        return storePath+"/"+messageInput.getId()+"."+messageInput.getFileType();
    }

    @Override
    public MessageInput read(MessageInput messageInput) {
        logger.info("read() called");
        String filename = getFilename(messageInput);
        try(FileInputStream fis = new FileInputStream(filename);){
            String message = IOUtils.toString(fis, Charset.forName("UTF-8"));
            logger.info("read() message: {}", message);
            MessageInput mi = new MessageInput();
            mi.setFileType(FileType.fromString(FilenameUtils.getExtension(filename)));
            mi.setId(messageInput.getId());
            mi.setContent(message);
            return mi;
        } catch (FileNotFoundException e) {
            logger.error("Error while store: {}", e.getMessage(), e);
        } catch (IOException e) {
            logger.error("Error while store: {}", e.getMessage(), e);
        }
        return null;
    }

    private MessageInput deserialize(String decryptedContent) {
        try {
            return mapper.readValue(decryptedContent, MessageInput.class);
        } catch (Exception e) {
            logger.error("Error while deserializing java object: {}", e.getMessage(), e);
        }
        return null;
    }
}
