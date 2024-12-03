# Changes to the EDR

## Webscraper-Database Integration

**Suggestion:**

I think that the price of ingredient returned from the webscraper should not be stored in the database.
The webscraper should be run every time a price needs to be determined for ordering instead, despite the running-time implications.

**Motivation:**

The price obtained from the web-scraper is specific to the quantity used in one recipe, whereas the ingredient table in the database can be used by many recipes.

**Impact:**

1. The procedure creating the ordering of recipes will now need to call the webscraper directly.
2. The ingredient table in the database will need to be modified; e.g. the cost field will need to be removed.

**Acceptance Status:**

In consideration.
