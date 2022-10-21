package com.cis.springboot.error;

import org.springframework.http.HttpStatus;

public class NotFoundException extends APIBaseException{

    public NotFoundException(String message) {
        super(message);
    }
    @Override
    public HttpStatus getStatusCode(){
        return HttpStatus.NOT_FOUND;
    }
}
