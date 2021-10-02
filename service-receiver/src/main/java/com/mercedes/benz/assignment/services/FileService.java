package com.mercedes.benz.assignment.services;

import com.mercedes.benz.assignment.model.MessageInput;

public interface FileService {
    void store(MessageInput messageInput);
    void update(MessageInput message);
    MessageInput read(MessageInput messageInput);
}
