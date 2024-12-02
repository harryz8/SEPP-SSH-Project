package com.sshgroup.ssh_fridge_contents_tracker;

public class ParseWeight {

    /**
     * takes the string containing the weight and finds makes a double of it. Only can currently process numbers of items, kg, mg, g, l and ml
     * @param weight the string that contains the weight
     */
    public static double parseWeight(String weight) {
        double weightDouble = -1;
        if (weight != null) {
            String[] weightArray = weight.split(" ");
            for (String each : weightArray) {
                if (StringToolkit.containsDigits(each)) {
                    StringBuilder letterString = new StringBuilder();
                    for (char eachChar : each.toCharArray()) {
                        if (Character.isLetter(eachChar)) {
                            letterString.append(eachChar);
                        }
                    }
                    if (letterString.isEmpty()) {
                        weightDouble = Double.parseDouble(each);
                        break;
                    }
                    String letterStr = letterString.toString();
                    StringBuilder digitString = new StringBuilder();
                    for (char eachChar : each.toCharArray()) {
                        if (Character.isDigit(eachChar) || eachChar=='.') {
                            digitString.append(eachChar);
                        }
                    }
                    if (!digitString.isEmpty()) {
                        switch (letterStr.toLowerCase()) {
                            case "ml", "mg":
                                weightDouble = Double.parseDouble(digitString.toString()) / 1000;
                                break;
                            case "l", "g":
                                weightDouble = Double.parseDouble(digitString.toString());
                                break;
                            case "kg":
                                weightDouble = Double.parseDouble(digitString.toString()) * 1000;
                                break;
                        }
                    }
                }
            }
        }
        return weightDouble;
    }
}
