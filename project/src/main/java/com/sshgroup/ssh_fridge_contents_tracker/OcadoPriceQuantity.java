package com.sshgroup.ssh_fridge_contents_tracker;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * A class for Ocado items that takes its link and gets its price and quantity (if applicable)
 */
public class OcadoPriceQuantity extends PriceQuantity {

    /**
     * The constructor. It takes the link and uses it to scrape the price and quantity (if applicable) of the item from the Ocado website. Can throw errors
     * @param link the url of the item's webpage on Ocado
     * @throws MalformedURLException when the URL is not acceptable
     * @throws ItemNotFoundException when the URL does not link to an item, or the item has no accessable price element
     */
    public OcadoPriceQuantity(String link) throws MalformedURLException, ItemNotFoundException {
        setLink(link);
        // find the price and if possible weight of the item
        URL url = new URL(link);
        WebScraper ws = new WebScraper(url);
        String body = ws.getWebpageHtml();
        if (body == null) {
            throw new ItemNotFoundException("Item not found at link.");
        }
        String priceElementOuter = null;
        priceElementOuter = WebScraper.getElementByID(body, "h2", "bop-price__current ", "class");
        if (priceElementOuter == null) {
            priceElementOuter = WebScraper.getElementByID(body, "h2", "bop-price__current bop-price__current--offer", "class");
            if (priceElementOuter == null) {
                throw new ItemNotFoundException("Item at link has no price. Error code: 1");
            }
        }
        // get price element inner
        // System.out.println(priceElementOuter);
        String priceElement = WebScraper.getElementByIDNoCloseTag(priceElementOuter, "meta", "price", "itemProp");
        if (priceElement == null) {
            throw new ItemNotFoundException("Item at link has no price. Error code: 2");
        }
        String priceString = WebScraper.getParameterValue(priceElement, "content");
        if (priceString == null) {
            throw new ItemNotFoundException("Item at link has no price. Error code: 3");
        }
        this.setPrice(Double.valueOf(priceString));
        String weightElement = WebScraper.getElementByID(body, "span", "bop-catchWeight", "class");
        if (weightElement != null) {
            //Strip tags
            StringLocation endOfStartTag = StringToolkit.getPositions(weightElement, ">");
            if (endOfStartTag != null) {
                String weight = weightElement.substring(endOfStartTag.getLastPos()+1);
                StringLocation startOfEndString = StringToolkit.getPositions(weight, "<");
                if (startOfEndString != null) {
                    weight = weight.substring(0, startOfEndString.getFirstPos());
                    setQuantity(ParseWeight.parseWeight(weight));
                }
            }
        }
    }
}
