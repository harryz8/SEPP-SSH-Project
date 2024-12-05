# Changes to the EDR

## Remove Webscraper-Database Integration

**Suggestion:**

I think that the price of ingredient returned from the webscraper should not be stored in the database.
There should be a separate database for the web-scraped prices instead, and then when the webscraping method is called, the smallest price returned for an item name and quantity can be stored in my new databse, or if already in the database, read from there.

**Motivation:**

The price obtained from the web-scraper is specific to the quantity used in one recipe, whereas the ingredient table in the database can be used by many recipes.

**Impact:**

1. The procedure creating the ordering of recipes will now need to call the webscraper directly.
2. The ingredient table in the database will need to be modified; e.g. the cost field will need to be removed.

**Acceptance Status:**

Accepted

## Remove Composite Keys

**Suggestion:**

Composite keys should be removed from the database outline and replaced with unique, atomic keys for each table.

**Motivation:**

It is quicker to code a Hybernate database with unique fields for ids, i.e. each table has its own atomic, unique primary key.

**Impact:**

Minimal outside the database, as these tables are just for linking tables either side many to many, and so likely wont be used often in the program code.

**Acceptance Status:**

Accepted
