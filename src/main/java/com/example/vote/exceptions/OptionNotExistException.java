package com.example.vote.exceptions;

public class OptionNotExistException extends RuntimeException
{
    public OptionNotExistException(String msg)
    {
        super(msg);
    }
}
