package com.sshgroup.ssh_fridge_contents_tracker;

/**
 * A class to hold the first and last positions of a substring in a string
 */
public class StringLocation {

    /**
     * holds the index of the first character of the substring in the source string
     */
    private int firstPos;

    /**
     * holds the index of the last character of the substring in the source string
     */
    private int lastPos;

    /**
     * the string that this class holds the first and last positions of in a source string
     */
    String subString;

    /**
     * The constructor to hold the following variables together in the class
     * @param subString the target string that was found in a larger source string
     * @param firstPos the index of the first character of the substring in the source string
     * @param lastPos the index of the last character of the substring in the source string
     */
    public StringLocation(String subString, int firstPos, int lastPos) {
        this.subString = subString;
        this.firstPos = firstPos;
        this.lastPos = lastPos;
    }

    /**
     * The getter method for {@link #firstPos}
     * @return the value from {@link #firstPos}
     */
    public int getFirstPos() {
        return firstPos;
    }

    /**
     * The getter method for {@link #lastPos}
     * @return the value from {@link #lastPos}
     */
    public int getLastPos() {
        return lastPos;
    }
}
