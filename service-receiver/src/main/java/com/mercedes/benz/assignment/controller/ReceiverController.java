package com.mercedes.benz.assignment.controller;

import com.mercedes.benz.assignment.model.ApiResponse;
import com.mercedes.benz.assignment.model.MessageInput;
import com.mercedes.benz.assignment.services.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/data")
public class ReceiverController {
    private Logger logger = LoggerFactory.getLogger(ReceiverController.class);

    @Autowired
    private FileService fileService;

    @PostMapping("/read")
    public ApiResponse read(@RequestBody MessageInput message) {
        logger.info("read() message id: {}", message.getId());
        return new ApiResponse(fileService.read(message));
    }
}
