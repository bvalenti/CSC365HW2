Project 2 for CSC365. Assignment instructions are provided below.

Assignment 2:
This extends Assignment 1 using persistent data structures and additional similarity metrics. It requires two programs.

Loader
Create a B-tree-based cache for data sources (web pages or their data). Test with K==8.
Create a persistent hash table for each data source maintaining word frequencies (and optionally other metrics)
Preload, collecting at least 1000 sites. Seed this with one or more root URLs, and then recursively add more.
Optionally, pre-categorize keys into 5 to 10 clusters using k-means, k-mediods, or a similar metric. (You can instead perform categorization in the application program.)
Application
Extend Assignment 1 to display a category (cluster) and most similar key from the above data structures. Check (or use time bound) if any cached pages change since loading and update if necessary.
