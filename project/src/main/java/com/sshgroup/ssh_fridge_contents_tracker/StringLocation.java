package com.sshgroup.ssh_fridge_contents_tracker;

/**
 * A class to hold the first and last positions of a substring in a string
 */
public class StringLocation {
    private int firstPos;
    private int lastPos;
    String subString;

    public StringLocation(String subString, int firstPos, int lastPos) {
        this.subString = subString;
        this.firstPos = firstPos;
        this.lastPos = lastPos;
    }

    public int getFirstPos() {
        return firstPos;
    }

    public int getLastPos() {
        return lastPos;
    }
}
