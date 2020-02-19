package com.jelastic.util;

import lombok.Data;

@Data
public class UploadResponse {
    private String name;
    private String file;
    private String error;

    private int result;
    private int size;
}
