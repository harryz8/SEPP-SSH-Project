package com.sshgroup.ssh_fridge_contents_tracker;

public class StringLocation {
    int firstPos;
    int lastPos;
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
