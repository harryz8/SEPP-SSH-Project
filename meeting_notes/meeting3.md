# Meeting 3

## What we have done since last time:

### Harry

I have attempted to build a web-scraping module which chooses the cheapest version of a specified item from the web, that has a quantity greater than or equal to that specified.
I have made changes to the EDR in order to make my module work, as I found that saving the prices in the database would be tricky as the quantity required for different recipes would be different and so the price found may not be relevent to other recipes that use that ingredient. My solution at the moment is to load the price as the recipes are being sorted by price, although this will lead to a much slower user experience as they have to wait for the information to be scraped from the web for multiple ingredients in multiple recipies. I will consider adding some kind of cache. I only have scraped one website (so far).

### Khaled 

I have made a table  for all entities that we need. Then I have modified it to separated 5 talbles but linked to each other where they should using many to one.

## Meeting Notes



