package com.abel.tokoonline.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class PenggunaRespone implements Serializable {
    private String message;
    private int status;
}
