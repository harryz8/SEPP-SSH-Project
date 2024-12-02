package com.sshgroup.ssh_fridge_contents_tracker;

public class NoItemsException extends Exception {
    public NoItemsException(String errorMessage) {
        super(errorMessage);
    }
}
