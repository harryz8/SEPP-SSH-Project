package com.sshgroup.ssh_fridge_contents_tracker;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;
import java.io.IOException;
import java.util.ArrayList;

/**
 * A class which contains object methods that get html code from a given URL and static methods which can be used to navigate that html code
 */
public class WebScraper {

    /**
     * Holds the html scraped after the constructor is run
     */
    String webpageHtml;

    /**
     * takes the html of the page at the url and stores it in the field webpageHtml
     * @param url the link to the webpage
     */
    public WebScraper(URL url) {
        if (url == null) {
            webpageHtml = null;
        }
        else {
            //Accepts any cookies
            CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));
            try {
                StringBuilder html = new StringBuilder();
                String inputLine;
                //Creates a connection using the provided url
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.connect();
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                //appends each line read to the string builder, returns null and so the while loop breaks when the end of the html file is reached
                while ((inputLine = in.readLine()) != null) {
                    html.append(inputLine);
                }
                this.webpageHtml = html.toString();
            } catch (IOException e) {
                System.out.println(e.toString());
            }
        }
    }

    /**
     * Getter function for WebScraper
     * @return a string containing the html of the webpage. Returns "" on error.
     */
    public String getWebpageHtml() {
        return webpageHtml;
    }

    /**
     * Gets all elements of a tag type in an element of a tag type
     * @param source the html string containing the `outerElementTagType` element
     * @param innerElementTagType the string type of the inner tags
     * @param outerElementTagType the string tpye of the outer tag
     * @return an arrayList of strings of each element of type `innerElementTagType` from in the outer element
     */
    public static ArrayList<String> getMultipleElementsFromElement(String source, String outerElementTagType, String innerElementTagType) {
        if (source == null || outerElementTagType == null || innerElementTagType == null || outerElementTagType.isEmpty() || innerElementTagType.isEmpty() || source.isEmpty()) {
            return null;
        }
        // finds the start of the outer element
        StringLocation firstUl = StringToolkit.getPositions(source, "<"+outerElementTagType);
        if (firstUl == null) {
            return null;
        }
        if (firstUl.getLastPos() >= source.length()) {
            return null;
        }
        // find the end of the outer element
        source = source.substring(firstUl.getLastPos());
        StringLocation lastUl = StringToolkit.getPositions(source, "</"+outerElementTagType+">");
        if (lastUl == null) {
            return null;
        }
        source = source.substring(firstUl.getLastPos()+1, lastUl.getFirstPos());
        String origSource = source;
        // find all the elements of type innerElementTagType in the outer element
        ArrayList<String> retArr = new ArrayList<>();
        int lastInt = 0;
        while (lastInt < origSource.length() && !(source.isEmpty())) {
            StringLocation first = StringToolkit.getPositions(source, "<"+innerElementTagType);
            StringLocation last = StringToolkit.getPositions(source, "</"+innerElementTagType+">");
            if (first == null || last == null) {
                break;
            }
            lastInt += last.getLastPos();
            retArr.add(source.substring(first.getFirstPos(), last.getLastPos()+1));
            source = source.substring(last.getLastPos()+1);
            lastInt++;
        }
        return retArr;
    }

    /**
     * Gets all elements of a tag type in an element of a tag type with the specified id in the specified parameter
     * @param source the html string containing the `outerElementTagType` element
     * @param innerElementTagType the string type of the inner tags
     * @param outerElementTagType the string tpye of the outer tag
     * @param outerElementID the string id corresponding to the parameter defined in `outerElementIDParameterName`
     * @param outerElementIDParameterName the parameter that holds the unique identifier for the element
     * @return an arrayList of strings of each element of type `innerElementTagType` from in the outer element
     */
    public static ArrayList<String> getMultipleElementsFromElement(String source, String outerElementTagType, String outerElementID, String outerElementIDParameterName, String innerElementTagType) {
        if (source == null || outerElementTagType == null || outerElementID == null || outerElementIDParameterName == null || innerElementTagType == null || innerElementTagType.isEmpty() || outerElementID.isEmpty() || outerElementIDParameterName.isEmpty() || outerElementTagType.isEmpty() || source.isEmpty()) {
            return null;
        }
        String newSource = getElementByID(source, outerElementTagType, outerElementID, outerElementIDParameterName);
        if (newSource == null || newSource.isEmpty()) {
            return null;
        }
        return getMultipleElementsFromElement(newSource, outerElementTagType, innerElementTagType);
    }
    /**
     * Gets all items of an unordered list
     * @param source the html string containing the unordered list
     * @return an arrayList of strings of each <li></li>
     */
    public static ArrayList<String> getUnorderedListItems(String source) {
        if (source == null || source.isEmpty()) {
            return null;
        }
        return getMultipleElementsFromElement(source, "ul", "li");
    }

    /**
     * A private method that finds the close tag of the element
     * @param source the html string containing the element that you are wanting to find the close tag of
     * @param elementType the type of element that you want to find the close tag of
     * @param openTag the location of the open tag corresponding to the close tag that you want to find
     * @return the location of the end of the close tag in `source` or -1 on error
     */
    private static int getCloseTagLoc(String source, String elementType, StringLocation openTag) {
        if (source == null || elementType == null || openTag == null || source.isEmpty() || elementType.isEmpty()) {
            return -1;
        }
        int openTags = 0;
        StringLocation closeTag;
        int closeTagLoc = openTag.getLastPos()+1;
        String restOfSource = source.substring(openTag.getLastPos()+1);
        while (true) {
            openTag = StringToolkit.getPositions(restOfSource, "<"+elementType);
            closeTag = StringToolkit.getPositions(restOfSource, "</"+elementType+">");
            if (closeTag == null) {
                return -1;
            }
            else if (openTag == null) {
                closeTagLoc += closeTag.getLastPos();
                break;
            }
            else {
                if ((openTag.getLastPos() > closeTag.getLastPos()) && openTags == 0) {
                    closeTagLoc += closeTag.getLastPos();
                    break;
                }
                else if (openTag.getLastPos() <= closeTag.getLastPos()){
                    openTags++;
                    restOfSource = restOfSource.substring(openTag.getLastPos()+1);
                    closeTagLoc += openTag.getLastPos()+1;
                }
                else {
                    openTags--;
                    restOfSource = restOfSource.substring(closeTag.getLastPos() + 1);
                    closeTagLoc += closeTag.getLastPos()+1;
                }
            }
        }
        if (closeTag.getLastPos()+1 > source.length()) {
            return -1;
        }
        return closeTagLoc;
    }

    /**
     * Gets the location of the open triangle of the element with the specified id
     * @param source the html string containing the element with your chosen id
     * @param elementType the type of element with your id, e.g. div
     * @param id the string id corresponding to the parameter defined in idElement
     * @param idElement the parameter that acts at the id in the element
     * @return the location of the open triangle (int)
     */
    private static int getElementStartByID(String source, String elementType, String id, String idElement) {
        if (source == null || id == null || idElement == null || elementType == null || source.isEmpty() || elementType.isEmpty() || id.isEmpty() || idElement.isEmpty()) {
            return -1;
        }
        StringLocation identLoc = StringToolkit.getPositions(source, idElement+"=\""+id+"\"");
        if (identLoc == null) {
            return -1;
        }
        //finds start of the element
        int locOfOpenTriangle = identLoc.getFirstPos();
        char[] charsInSource = source.toCharArray();
        while (charsInSource[locOfOpenTriangle] != '<') {
            locOfOpenTriangle--;
            if (locOfOpenTriangle < 0) {
                return -1;
            }
        }
        if (!source.substring(locOfOpenTriangle+1).startsWith(elementType)) {
            return -1;
        }
        return locOfOpenTriangle;
    }

    /**
     * Gets the element with the specified id. It must be an element with a close tag
     * @param source the html string containing the element with your chosen id
     * @param elementType the type of element with your id, e.g. div
     * @param id the string id corresponding to the parameter defined in idElement
     * @param idElement the parameter that acts at the id in the element
     * @return a string of the element with your id
     */
    public static String getElementByID(String source, String elementType, String id, String idElement) {
        if (source == null || id == null || idElement == null || elementType == null || source.isEmpty() || elementType.isEmpty() || id.isEmpty() || idElement.isEmpty()) {
            return null;
        }
        StringLocation identLoc = StringToolkit.getPositions(source, idElement+"=\""+id+"\"");
        if (identLoc == null) {
            return null;
        }
        // find open tag
        int locOfOpenTriangle = getElementStartByID(source, elementType, id, idElement);
        if (locOfOpenTriangle == -1) {
            return null;
        }
        //finds end of the element by looking for the close tag
        int closeTagLoc = WebScraper.getCloseTagLoc(source, elementType, identLoc);
        if (closeTagLoc == -1) {
            return null;
        }
        return source.substring(locOfOpenTriangle, closeTagLoc+1);
    }

    /**
     * Gets the element with the specified id. It must end at the end of the start tag
     * @param source the html string containing the element with your chosen id
     * @param elementType the type of element with your id, e.g. div
     * @param id the string id corresponding to the parameter defined in idElement
     * @param idElement the parameter that acts at the id in the element
     * @return a string of the element with your id
     */
    public static String getElementByIDNoCloseTag(String source, String elementType, String id, String idElement) {
        if (source == null || id == null || idElement == null || elementType == null || source.isEmpty() || elementType.isEmpty() || id.isEmpty() || idElement.isEmpty()) {
            return null;
        }
        StringLocation identLoc = StringToolkit.getPositions(source, idElement+"=\""+id+"\"");
        if (identLoc == null) {
            return null;
        }
        // find start of tag
        int locOfOpenTriangle = getElementStartByID(source, elementType, id, idElement);
        if (locOfOpenTriangle == -1) {
            return null;
        }
        //finds end of the tag
        int locOfCloseTriangle = identLoc.getLastPos();
        char[] charsInSource = source.toCharArray();
        while (charsInSource[locOfCloseTriangle] != '>') {
            locOfCloseTriangle++;
            if (locOfCloseTriangle >= source.length()) {
                return null;
            }
        }
        return source.substring(locOfOpenTriangle, locOfCloseTriangle+1);
    }

    /**
     * Gets the first element in `source` that has type `elementType`. It must have a close tag
     * @param source the html string in which to find your chosen element
     * @param elementType the type of element to be found
     * @return the string containing just the requested element or null on error
     */
    public static String getElement(String source, String elementType) {
        if (source == null || elementType == null || source.isEmpty() || elementType.isEmpty()) {
            return null;
        }
        //find open tag
        StringLocation openTag = StringToolkit.getPositions(source, "<"+elementType);
        if (openTag == null) {
            return null;
        }
        //find close tag
        int closeTagLoc = WebScraper.getCloseTagLoc(source, elementType, openTag);
        if (closeTagLoc == -1) {
            return null;
        }
        return source.substring(openTag.getFirstPos(), closeTagLoc+1);
    }

    /**
     * Gets the string value of a specified parameter in an element. Only finds parameters with string values
     * @param element the string containing the element in which to find your parameter
     * @param parameterName the name of the parameter which holds the value you want to get
     * @return the string value of the parameter you wanted or null on error
     */
    public static String getParameterValue(String element, String parameterName) {
        if (element == null || parameterName == null || element.isEmpty() || parameterName.isEmpty()) {
            return null;
        }
        StringLocation parameter = StringToolkit.getPositions(element, parameterName+"=\"");
        if (parameter == null) {
            return null;
        }
        StringLocation endOfParameterValue = StringToolkit.getPositions(element.substring(parameter.getLastPos()+1), "\"");
        if (endOfParameterValue == null) {
            return null;
        }
        return element.substring(parameter.getLastPos()+1, endOfParameterValue.getLastPos()+parameter.getLastPos()+1);
    }
}
