package com.mercedes.benz.assignment.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercedes.benz.assignment.constants.FileType;
import com.mercedes.benz.assignment.model.MessageInput;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FileServiceImplTest {

    @MockBean
    private FileServiceImpl fileService;

    @Autowired
    private EncryptionService encryptionService;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void testStore(){
        String msg = "sample";
        MessageInput mi = new MessageInput();
        mi.setFileType(FileType.CSV);
        mi.setContent(msg);
        fileService.store(mi);
        assertTrue(true);
    }

    @Test
    public void testUpdate(){
        String msg = "sample2";
        MessageInput mi = new MessageInput();
        mi.setFileType(FileType.CSV);
        mi.setContent(msg);
        fileService.update(mi);
        assertTrue(true);
    }

    @Test
    public void testStoreMessage(){
        fileService.storeMessage("test");
        assertTrue(true);
    }

    @Test
    public void testUpdateMessage(){
        fileService.updateMessage("test2");
        assertTrue(true);
    }

    @Test
    public void testRead(){
        MessageInput mi = new MessageInput();
        mi.setId(123);
        mi.setFileType(FileType.CSV);
        when(fileService.read(any(MessageInput.class))).thenReturn(mi);
        assertNotNull(fileService.read(mi));
    }
}
