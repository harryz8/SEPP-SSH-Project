# Meeting 3

## What we have done since last time:

### Harry

I have attempted to build a web-scraping module which chooses the cheapest version of a specified item from the web, that has a quantity greater than or equal to that specified.
I have made changes to the EDR in order to make my module work, as I found that saving the prices in the database would be tricky as the quantity required for different recipes would be different and so the price found may not be relevent to other recipes that use that ingredient. My solution at the moment is to load the price as the recipes are being sorted by price, although this will lead to a much slower user experience as they have to wait for the information to be scraped from the web for multiple ingredients in multiple recipies. I will consider adding some kind of cache. I only have scraped one website (so far).

### Khaled 

I have made a table  for all entities that we need. Then I have modified it to separated 5 tables but linked to each other where they should, using many to one.

### Tom

I have begun working on the basics of the form where users can add new recipes to the recipe database. To this point I have created basic variables to store the name and instructions of each recipe. I have also created ArrayLists to store the ingredients for each new recipe, there is one for the ingredient names and one for the amount required. I am currently using a scanner to take the user's input for the recipe. I plan on using this meeting to discuss the implementation of the database with Khaled to determine how I should go about adding the collected data to the tables. 


### Phoebe

I have made two methods used for sorting the list of recipes to be displayed to the user. The recipes can be either filtered by category or sorted by the price of the remaining items. I have used undefined methods in order to query the database but I plan to talk with Khaled to use the methods he has created in order to fully implement my methods

## Meeting Notes



