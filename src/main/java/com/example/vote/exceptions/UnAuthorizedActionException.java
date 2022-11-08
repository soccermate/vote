package com.example.vote.exceptions;

public class UnAuthorizedActionException extends RuntimeException
{
    public UnAuthorizedActionException(String msg)
    {
        super(msg);
    }
}
