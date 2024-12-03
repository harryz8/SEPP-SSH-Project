package com.sshgroup.ssh_fridge_contents_tracker;

import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;


public class MainApplicationTests {
    @Test
    void testJUnitIsWorking() {
        assertTrue(true);
    }
    @Test
    void testOcadoURLRead() {
        String body = "";
        try {
            URL url = new URL("https://www.ocado.com/search?entry=milk");
            WebScraper ws = new WebScraper(url);
            body = ws.getWebpageHtml();
        }
        catch (MalformedURLException e) {
            System.out.println(e.toString());
        }
        assertFalse(body.isEmpty());
    }
    @Test
    void testStringToolkitGetPositions() {
        String large = "HelloWorldHowAreYou=\"whatever should i do\"";
        String target = "You=\"whatever";
        StringLocation locs = StringToolkit.getPositions(large, target);
        if (locs != null) {
            assertTrue(large.substring(locs.getFirstPos(), locs.getLastPos()+1).equals(target));
        }
    }
    @Test
    void testWeightParser1() {
        String weightString = "6 per pack";
        double weight = ParseWeight.parseWeight(weightString);
        assertEquals(6, weight);
    }
    @Test
    void testWeightParser2() {
        String weightString = "1.5kg";
        double weight = ParseWeight.parseWeight(weightString);
        assertEquals(1500, weight);
    }
    @Test
    void testWeightParser3() {
        String weightString = "568ml";
        double weight = ParseWeight.parseWeight(weightString);
        System.out.println(weight);
        assertEquals(0.568, weight);
    }
    @Test
    void testWeightParser4() {
        String weightString = "1.136L";
        double weight = ParseWeight.parseWeight(weightString);
        System.out.println(weight);
        assertEquals(weight, 1.136);
    }
    @Test
    void getElementTest() {
        String incElement = "<div class=\"test\"><p>Hello HTML</p><a href=\"Alink.com\">Some Link</a></div>";
        String element = WebScraper.getElement(incElement, "a");
        assertEquals("<a href=\"Alink.com\">Some Link</a>", element);
    }
    @Test
    void getParameterTest() {
        String incElement = "<div class=\"test\"><p>Hello HTML</p><a href=\"Alink.com\">Some Link</a></div>";
        String element = WebScraper.getParameterValue(incElement, "class");
        assertEquals("test", element);
    }
    @Test
    void testOcadoPriceQuantityComparitorImplementation() {
        double quantityNeeded = 100.5;
        String ingredientName = "flour";
        double minOcadoPrice = RecipeToolkit.getCheapestPrice(ingredientName, quantityNeeded);
        assertEquals(0.45, minOcadoPrice);
    }
}
