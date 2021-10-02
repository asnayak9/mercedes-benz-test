package com.mercedes.benz.assignment.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FileType {
    CSV("csv"), XML("xml");

    private String fileType;

    public static FileType fromString(String fileTypeString) {
        for(FileType f : values()){
            if(f.fileType.equalsIgnoreCase(fileTypeString)){
                return f;
            }
        }
        return null;
    }
}
