package com.mercedes.benz.assignment.controller;

import com.mercedes.benz.assignment.constants.FileType;
import com.mercedes.benz.assignment.exceptions.InvalidFileInputException;
import com.mercedes.benz.assignment.model.ApiResponse;
import com.mercedes.benz.assignment.model.MessageInput;
import com.mercedes.benz.assignment.services.FileSenderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Random;

@RestController
@RequestMapping("/v1/data")
public class SenderController {

    private Logger logger = LoggerFactory.getLogger(SenderController.class);

    @Autowired
    private FileSenderService fileSenderService;

    private Random random = new Random();

    @PostMapping("/store")
    public ApiResponse store(@RequestHeader("fileType") String fileTypeString, @RequestBody String message) {
        logger.info("store() fileType: {}, message: {}", fileTypeString, message);
        FileType fileType = FileType.fromString(fileTypeString);
        if (fileType == null) {
            throw new InvalidFileInputException(fileTypeString + " not supported");
        }
        int messageId = random.nextInt(Integer.MAX_VALUE-1);
        fileSenderService.send(new MessageInput(messageId, fileType, message));
        return new ApiResponse("Successfully stored message with ID: "+messageId);
    }

    @PostMapping("/update")
    public ApiResponse update(@RequestHeader("fileType") String fileTypeString,
                              @RequestHeader("id") String id,
                              @RequestBody String message) {
        logger.info("update() message: {}", message);
        MessageInput mi = new MessageInput();
        mi.setContent(message);
        mi.setFileType(FileType.fromString(fileTypeString));
        mi.setId(Integer.valueOf(id));
        fileSenderService.update(mi);
        return new ApiResponse("Success");
    }

    @PostMapping("/read")
    public ApiResponse read(@RequestHeader("fileType") String fileTypeString, @RequestBody MessageInput message) {
        logger.info("read() message fileTypeString: {}, id: {}", fileTypeString, message.getId());
        message.setFileType(FileType.fromString(fileTypeString));
        return fileSenderService.read(message);
    }

    @ExceptionHandler(InvalidFileInputException.class)
    public ApiResponse handleError(InvalidFileInputException ex){
        return new ApiResponse(ex.getMessage());
    }
}