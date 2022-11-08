package com.example.vote.exceptions;

public class VoteIdAndGroupIdNotMatchingException extends RuntimeException{

    public VoteIdAndGroupIdNotMatchingException(String msg)
    {
        super(msg);
    }
}
