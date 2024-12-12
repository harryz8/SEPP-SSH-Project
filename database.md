# Database

We could write our own database or use the module __hibernate__.

## Database Outline


![database_outline_1](https://github.com/user-attachments/assets/c6210661-7275-4b3d-8372-53e51fa42b03)



## Entity Relationship Diagram

![image](https://github.com/user-attachments/assets/6e295a5d-179e-448a-8de2-ca2d0dc850c7)

# WebScraper Cache Database

This is just a table to make a cache of mappings from name and quantity of an ingredient to webscraped prices persist. This does not work in this prototype beacuse docker compose creates the database from scratch each time it is loaded, but is designed so that when connected to a database in the SSH Cloud which runs constantly, it will cache these for up to 7 days before they will be updated when the program is next run.

![image](https://github.com/user-attachments/assets/e16ed7f9-278c-4c60-abdc-6a34923c7a40)
