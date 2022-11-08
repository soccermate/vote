package com.example.vote.exceptions;

public class DuplicateOptionsException extends RuntimeException
{
    public DuplicateOptionsException(String msg)
    {
        super(msg);
    }
}
