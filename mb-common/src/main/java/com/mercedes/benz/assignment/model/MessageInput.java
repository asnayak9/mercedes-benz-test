package com.mercedes.benz.assignment.model;

import com.mercedes.benz.assignment.constants.FileType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MessageInput implements Serializable {
    private int id;
    private FileType fileType;
    private String content;
}
