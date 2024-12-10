package com.sshgroup.ssh_fridge_contents_tracker;

import org.junit.jupiter.api.Test;
import java.net.MalformedURLException;
import java.net.URL;
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
        element = WebScraper.getElement(incElement, "j");
        assertEquals(null, element);
        element = WebScraper.getElement(incElement, null);
        assertEquals(null, element);
        element = WebScraper.getElement("", "a");
        assertEquals(null, element);
        element = WebScraper.getElement(null, "a");
        assertEquals(null, element);
        element = WebScraper.getElement(incElement, "");
        assertEquals(null, element);
        element = WebScraper.getElement("", "");
        assertEquals(null, element);
        element = WebScraper.getElement(null, null);
        assertEquals(null, element);
    }
    @Test
    void getParameterTest() {
        String incElement = "<div class=\"test\"><p>Hello HTML</p><a href=\"Alink.com\">Some Link</a></div>";
        String element = WebScraper.getParameterValue(incElement, "class");
        assertEquals("test", element);
        element = WebScraper.getParameterValue(incElement, "href");
        assertEquals("Alink.com", element);
        element = WebScraper.getParameterValue(incElement, "text");
        assertEquals(null, element);
        incElement = "<div class=\"test\" id=\"one\"><p>Hello HTML</p><a href=\"Alink.com\">Some Link</a></div>";
        element = WebScraper.getParameterValue(incElement, "class");
        assertEquals("test", element);
        element = WebScraper.getParameterValue(incElement, "id");
        assertEquals("one", element);
        incElement = "<div class=\"test\" id=1><p>Hello HTML</p><a href=\"Alink.com\">Some Link</a></div>";
        element = WebScraper.getParameterValue(incElement, "id");
        assertEquals(null, element);
    }
    @Test
    void testOcadoPriceQuantityComparitorImplementation() {
        Loading loading = new Loading();
        Thread loadingThread = new Thread(loading);
        loadingThread.start();
        double quantityNeeded = 100.5;
        String ingredientName = "flour";
        CacheMap.cache.load();
        PriceQuantity minOcadoPrice = RecipeToolkit.getCheapestIngredient(ingredientName, quantityNeeded);
        loadingThread.interrupt();
        assertEquals(0.45, minOcadoPrice.getPrice());
    }
    @Test
    void testGetWebpageHTML() {
        WebScraper ws = new WebScraper(null);
        assertEquals(null, ws.getWebpageHtml());
        try {
            URL url = new URL("https://www.joerreia.sod/"); //a nonsense URL link
            ws = new WebScraper(url);
            assertEquals(null, ws.getWebpageHtml());
        }
        catch (MalformedURLException e) {
            System.out.println("Error: "+e.toString());
        }
    }
    @Test
    void testStringLocation() {
        String test1 = "";
        String test2 = "hello world";
        String test3 = "whatisthisString123";
        assertEquals(null, StringToolkit.getPositions(test1, "hello"));
        assertEquals(null, StringToolkit.getPositions(test1, ""));
        StringLocation sl1 = StringToolkit.getPositions(test2, "hello");
        assertEquals(0, sl1.getFirstPos());
        assertEquals(4, sl1.getLastPos());
        StringLocation sl2 = StringToolkit.getPositions(test2, "world");
        assertEquals(6, sl2.getFirstPos());
        assertEquals(10, sl2.getLastPos());
        StringLocation sl3 = StringToolkit.getPositions(test3, "this");
        assertEquals(6, sl3.getFirstPos());
        assertEquals(9, sl3.getLastPos());
        StringLocation sl4 = StringToolkit.getPositions(test3, "1");
        assertEquals(16, sl4.getLastPos());
        assertEquals(16, sl4.getFirstPos());
        StringLocation sl5 = StringToolkit.getPositions(test3, "search");
        assertEquals(null, sl5);
    }
    @Test
    void testContainsDigits() {
        assertTrue(StringToolkit.containsDigits("hello123hello"));
        assertTrue(StringToolkit.containsDigits("what1what"));
        assertTrue(StringToolkit.containsDigits("8 is the best number"));
        assertTrue(StringToolkit.containsDigits("Why do you like the number 7"));
        assertFalse(StringToolkit.containsDigits("Eighty Three"));
        assertFalse(StringToolkit.containsDigits(""));
        assertFalse(StringToolkit.containsDigits(null));
        assertTrue(StringToolkit.containsDigits("8"));
        assertTrue(StringToolkit.containsDigits(" 8j"));
        assertTrue(StringToolkit.containsDigits("z8 "));
    }
    @Test
    void getElementByIdNoCloseTagTest() {
        String incElement = "<div class=\"test\"><p>Hello HTML</p><a href=\"Alink.com\">Some Link</a></div>";
        assertEquals("<a href=\"Alink.com\">", WebScraper.getElementByIDNoCloseTag(incElement, "a", "Alink.com", "href"));
        assertNull(WebScraper.getElementByIDNoCloseTag(null, "a", "Alink.com", "href"));
        assertNull(WebScraper.getElementByIDNoCloseTag(incElement, null, "Alink.com", "href"));
        assertNull(WebScraper.getElementByIDNoCloseTag(incElement, "a", null, "href"));
        assertNull(WebScraper.getElementByIDNoCloseTag(incElement, "a", "Alink.com", null));
        assertNull(WebScraper.getElementByIDNoCloseTag("", "a", "Alink.com", "href"));
        assertNull(WebScraper.getElementByIDNoCloseTag(incElement, "", "Alink.com", "href"));
        assertNull(WebScraper.getElementByIDNoCloseTag(incElement, "a", "", "href"));
        assertNull(WebScraper.getElementByIDNoCloseTag(incElement, "a", "Alink.com", ""));
        assertEquals("<div class=\"test\">", WebScraper.getElementByIDNoCloseTag(incElement, "div", "test", "class"));
        assertNull(WebScraper.getElementByIDNoCloseTag(incElement, "q", "test", "class"));
        assertNull(WebScraper.getElementByIDNoCloseTag(incElement, "div", "red", "class"));
        assertNull(WebScraper.getElementByIDNoCloseTag(incElement, "div", "test", "id"));
    }
    @Test
    void getElementById() {
        String incElement = "<div class=\"test\"><p>Hello HTML</p><a href=\"Alink.com\">Some Link</a></div>";
        assertEquals("<div class=\"test\"><p>Hello HTML</p><a href=\"Alink.com\">Some Link</a></div>", WebScraper.getElementByID(incElement, "div", "test", "class"));
        //more
    }
}
