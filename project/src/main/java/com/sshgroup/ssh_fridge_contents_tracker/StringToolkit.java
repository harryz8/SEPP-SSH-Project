package com.sshgroup.ssh_fridge_contents_tracker;

public class StringToolkit {
    /**
     * takes a large string and a string to find within it and returns an object that contains the first and last positions of the substring in the larger string.
     * @param wholeString the string to find the other in
     * @param target the string to find the location of
     * @return an object that contains the first and last positions of the target string in the wholeString
     */
    public static StringLocation getPositions(String wholeString, String target) {
        if (wholeString == null || target == null || wholeString.isEmpty() || target.isEmpty()) {
            return null;
        }
        int first = 0;
        int last = target.length()-1;
        while (!wholeString.substring(first, last+1).equals(target)) {
            first++;
            last++;
            if (last >= wholeString.length()) {
                return null;
            }
        }
        return new StringLocation(target, first, last);
    }

    /**
     * Checks if a string contains any digit(s)
     * @param string the string to check for digits
     * @return a boolean value of whether there are/is a digit(s)
     */
    public static boolean containsDigits(String string) {
        if (string == null) {
            return false;
        }
        for (char each : string.toCharArray()) {
            if (Character.isDigit(each)) {
                return true;
            }
        }
        return false;
    }
}
