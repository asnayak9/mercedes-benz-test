package com.mercedes.benz.assignment.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercedes.benz.assignment.constants.FileType;
import com.mercedes.benz.assignment.exceptions.EncryptionException;
import com.mercedes.benz.assignment.model.MessageInput;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import javax.jms.Queue;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FileSenderServiceImplTest {

    @MockBean
    private FileSenderServiceImpl fileSenderService;

    @Mock
    private EncryptionService encryptionService;

    @Mock
    private ObjectMapper mapper;

    @Mock
    private JmsTemplate jmsTemplate;

    @Autowired @Qualifier("soreQueue")
    private Queue storeQueue;

    @Autowired @Qualifier("updateQueue")
    private Queue updateQueue;

    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void testSend() throws EncryptionException {
        MessageInput message = new MessageInput();
        message.setFileType(FileType.CSV);
        message.setId(1);
        message.setContent("abc");

        when(encryptionService.encrypt(any())).thenReturn("aaa");
        doNothing().when(jmsTemplate).convertAndSend(any(Queue.class), anyString());

        fileSenderService.send(message);
        assertTrue(true);
    }
}
