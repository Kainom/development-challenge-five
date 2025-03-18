package com.medcloud.challenge.dtos;

import java.util.Date;

public record ErrorDTO(Integer status,String message,Date timestamp) {
    
}
