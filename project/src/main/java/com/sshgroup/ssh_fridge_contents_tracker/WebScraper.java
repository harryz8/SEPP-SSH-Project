package com.sshgroup.ssh_fridge_contents_tracker;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.io.IOException;
import java.util.ArrayList;

public class WebScraper {
    protected String webpageHtml = "";

    public static void main(String[] args) {
        try {
            URL url = new URL("https://www.ocado.com/search?entry=milk%201l");
            WebScraper ws = new WebScraper(url);
            String body = ws.getWebpageHtml();
            System.out.println("Hello");
            System.out.println(body);
            //ArrayList<String> elems = WebScraper.getUnorderedListItems(body, "", "");
            //System.out.println(elems.get(0));
        }
        catch (MalformedURLException e) {
            System.out.println(e);
        }
    }
    /**
     * takes the html of the page at the url and stores it in the field webpageHtml
     * @param url the link to the webpage
     */
    public WebScraper(URL url) {
        try {
            StringBuilder html = new StringBuilder();
            String inputLine;
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            while((inputLine = in.readLine()) != null) {
                System.out.println(inputLine);
                html.append(inputLine);
            }
            this.webpageHtml = html.toString();
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }

    /**
     * Getter function for WebScraper
     * @return a string containing the html of the webpage
     */
    public String getWebpageHtml() {
        return webpageHtml;
    }

    /**
     * Gets all items of an unordered list
     * @param source the webpage html containing the unordered list
     * @param id the string id corresponding to the parameter defined in idElement
     * @param idElement the parameter that acts at the id in the element
     * @return an arrayList of strings of each <li></li>
     */
    public static ArrayList<String> getUnorderedListItems(String source, String id, String idElement) {
        if (source == null || id == null || idElement == null) {
            return null;
        }
        StringLocation firstUl = StringToolkit.getPositions(source, "<ul");
        if (firstUl.getLastPos() >= source.length()) {
            return null;
        }
        source = source.substring(firstUl.getLastPos());
        StringLocation lastUl = StringToolkit.getPositions(source, "</ul>");
        source = source.substring(firstUl.getLastPos()+1, lastUl.getFirstPos()-1);
        ArrayList<String> retArr = new ArrayList<>();
        int lastInt = 0;
        while (lastInt < source.length() && !(source.isEmpty())) {
            StringLocation first = StringToolkit.getPositions(source, "<li");
            StringLocation last = StringToolkit.getPositions(source, "</li>");
            if (first == null || last == null) {
                break;
            }
            lastInt = last.getLastPos();
            retArr.add(source.substring(first.getFirstPos(), last.getLastPos()));
            source = source.substring(last.getLastPos()+1);
        }
        return retArr;
    }

    public static String getElementByID() {
        return "";
    }
}
