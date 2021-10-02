package com.mercedes.benz.assignment.services;

import com.mercedes.benz.assignment.model.ApiResponse;
import com.mercedes.benz.assignment.model.MessageInput;

public interface FileSenderService {
    void send(MessageInput message);
    ApiResponse read(MessageInput message);
    void update(MessageInput message);
}
