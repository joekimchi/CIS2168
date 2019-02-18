CIS 2168 Lab 2 
John Edwardson

WebCrawl:  This program implements a toy web-crawler.  To run the project, open in NetBeans, 
execute the program(Main class is CrawlerGUI.java).  You will be presented with an interface
that allows you to enter a URL, the max pages to crawl, and the forbidden domains.  There are
also three buttons, one to show HTML of the given URL, one to get the links from the given URL, and
one button to perform the web crawl.  The webcrawler works according to the following pseudocode:
     
    Create a todo list of URLs 
     Add the first page to the list 
     while (# results < max and todo is not empty)
           Pop the next url from the todo list
           Classify the page and create a new WebPage object of the appropriate type
           Add this webpage object to an ArrayList of results
           Scan the page for links, and add them to the todo list

Once this loop is complete, you will be presented with a results window.  Choosing a page
from the list will cause the page to be scanned according to its type(text, media, form), 
and the results are shown in the text area below the list.

The webcrawler logic is encapsulated in the class Crawler.  The WebPage objects are defined
in the WebPage, FormPage, MediaPage, and TextPage objects.  Finally, the CrawlerGUI and ReportPanel
objects contain the code for the user interface.  More details on the implementation can be found
by reading the code, or consulting the included javadocs.  