package com.ex01.basic.exception.post;

public class PostAccessDeniedException extends RuntimeException{
    public PostAccessDeniedException(){}
    public PostAccessDeniedException(String msg){
        super(msg);
    }
}
