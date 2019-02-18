/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package webcrawl;


import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.net.URL;
/**
 * Encapsulates the logic for the web crawling process..
 * 
 * 
 * @author John Edwardson
 */
public class Crawler {
   //Data fields 
   private String start;
   private String[] verboten;
   private int max;
   private LinkedList<URL> todo;
   private ArrayList<WebPage> results;
   private static final int MAX_TODO = 500;
   
   /**
    * Constructs a new crawler with the given parameters
    * @param start The initial url string
    * @param verboten Array of forbidden domain names 
    * @param max The maximum number of webpages to crawl
    */
   public Crawler(String start, String verboten, int max) {
       this.start = start;
       this.max = max;
       this.verboten = verboten.split("\\s*[;,]\\s*");
       todo = new LinkedList<>();
       results = new ArrayList<>(max);
   }
    
   /**
    * Runs the full Crawl process.
    * @return A log detailing the results of the crawl
    */
   public String run() {
       
       String current;
       String log = startCrawl();
       //Main Crawler Loop
       while ((current = nextCrawl()) != null) {
           log += current;
           
       }
       log += finishCrawl();
       return log;
   }
   
   /**
    * Begins a crawl process.
    * Attempts to create a new URL using the start parameter,
    * and initializes the todo list.
    * @return A log string detailing the results of the step
    */
   public String startCrawl()  {
       String log = "Initializing Crawler\n" + "Starting page: " + start + "\n" + 
               "Max Elements: " +  max + "\n" + "Forbidden Domains: "; 
       for (String domain : this.verboten)
           log += domain + " ";
       log += "\n-------------------------------\n";
       
       try {
           todo.add(new URL(start));
       }catch(MalformedURLException e) {
           log += e.getMessage() + "\n";
       }
       return log;
   }
   
   /**
    * Crawls to the next URL in the todo list.
    * Attempts to create a new WebPage of the correct subtype, and
    * add the links from that WebPage to the todo list.
    * @return A log string detailing the results of the step
    */
   
   public String nextCrawl() {
       String log = "";
       URL nextURL;
       if (results.size()<max && todo.size()> 0) {
           nextURL = todo.pop();
           log += "Scanning " + nextURL + "\n";
           try {
               WebPage current = classify(nextURL);
               log += "Title: " + current.getTitle() + "\n";
               log += "Classification: " + current.getType() + "\n";        
               log += updateTodo(current.getLinks());
               results.add(current);               
           }catch (Exception e) {
               log += e.getMessage() + "\n";
           }
           log += "\n-------------------------------\n";
       }
       else
           log = null;
       
       return log;
               
   }
   
   /**
    * Returns crawl completion message.
    * @return 
    */
   public String finishCrawl() {
       String log = "Crawl Finished: ";
       if (results.size() >= max) {
           log += "Reached max number of pages\n";
       }
       else if (todo.size() == 0) 
           log += "Ran out of links.\n";
       return log;
   }
      
   public ArrayList<WebPage> getResults() {
       return results;
   }
   /**
    * Adds new URL strings to todo list.
    * Enforces forbidden domains and prevents duplicate urls 
    * @param newlinks 
    */
   private String updateTodo(ArrayList<URL> newlinks){ 
       String log = "Links Found: " + newlinks.size() + "\n";
       int fc, dc, ec, bc, count;
       fc = dc = ec = bc = count = 0;
       
       Addloop:
       for (URL newURL: newlinks) {
           //System.out.println(newURL.toString());
           if (todo.size() > MAX_TODO )  
               break;
            //Enforce forbidden domains
            for (String forbid : verboten) {
                //System.out.println(newURL.getHost());
                if (newURL.getHost().endsWith(forbid)) {
                     fc++; 
                     continue Addloop; 
                }
            }
            //Prevent duplication.  Need a better solution here
            for (WebPage page : results) {
                if (newURL.toString().equals(page.getURLString())) {
                    dc++;
                    continue Addloop;
               }
            }
    
            //If we get to this point, all checks have passed
           
            todo.add(newURL);
            count++;
           
       }   
       log += "Duplicates: " + dc + "\n";
       log += "Forbidden: " + fc + "\n";
       log += "Links added: " + count + "\n";
       
       return log;
   }
   
   /**
    * Creates a new WebPage of the correct subtype.
    * Reads the content of a given URL and determines
    * whether the page should be instantiated as a TextPage
    * MediaPage or FormPage object
    * @param url The URL to be classified
    * @return 
    */
   private WebPage classify(URL url) throws IOException {
       WebPage result;
       String HTML = WebPage.getHTML(url);
       if(!(HTML.equals(""))) {
            Pattern pattern = Pattern.compile("(<form\\b|<img\\b)");
            Matcher matcher = pattern.matcher(HTML);
            if( matcher.find() ) {
                //System.out.println(matcher.group());
                if (matcher.group().equals("<form")){
                     result = new FormPage(url, HTML);
                }
                else{ 
                     result = new MediaPage(url, HTML);                   
                }
             }
             else {
                 result = new TextPage(url, HTML);
             }
       }else
           throw new IOException("No HTML Found");
       return result;
   }
}
