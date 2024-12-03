package com.sshgroup.ssh_fridge_contents_tracker;

public class ItemNotFoundException extends Exception {
    public ItemNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
