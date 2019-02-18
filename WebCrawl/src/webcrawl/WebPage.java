/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package webcrawl;

import java.net.URL;
import java.net.HttpURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * Abstract base class for WebPage objects
 * @author John Edwardson
 */
public abstract class WebPage {
    //Fields
    private URL url; 
    private String title;
    protected String HTML;
    protected String type;
    protected ArrayList<Record> stats;
    
    //Methods

    /**
     * Creates a new web page with a given URL object and HTML content.
     * @param URL
     * @param HTML 
     */
    public WebPage(URL url, String HTML) {
        this.url = url;
        this.HTML = HTML;
        title = findTitle();
        stats = new ArrayList<>();
    }
    
    /**
     * Searches HTML to fill in title field.
     * @return The title if found, otherwise "None" 
     */
    private String findTitle() {
        String result = "None";
        int f, l;
        Pattern pattern = Pattern.compile("<title>");
        Matcher matcher = pattern.matcher(HTML);
        if (matcher.find()) {
            f = matcher.end();
            l = HTML.indexOf("</title>", f);
            result = HTML.substring(f,l);
        }
        return result;
    }


    
    /**
     * Reads and returns the HTML content from a given URL
     * @param urlString
     * @return The HTML content as a string
     */
    public static String getHTML(String urlString) 
            throws MalformedURLException, IOException {
        return WebPage.getHTML(new URL(urlString));

    }
    
    public static String getHTML(URL url) 
             {
        String html = "";
        String currentLine;
        
        //Read header to make sure page is html(not 100% effective)
        HttpURLConnection connection = null;
        String mime = "";
        BufferedReader in = null;
        
        try {
            connection = (HttpURLConnection)url.openConnection();
            connection.setAllowUserInteraction( false );
            connection.setDoInput( true );
            connection.setDoOutput( false );
            connection.setUseCaches( true );
            connection.setRequestMethod("GET");
            connection.connect();
            mime = connection.getContentType();
            //System.out.println(mime);
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            if (mime.toLowerCase().contains("text/html")) {
                
                while ((currentLine = in.readLine()) != null) {
                    html += currentLine + "\n";
                }
            }
            in.close();
        }catch (IOException e) {
            System.out.println(e.getMessage());
            html = "";
        }finally {
            if (connection != null) 
                connection.disconnect();
            in = null;
        }

        //Try needed to cleanly use resources    
        return html;
    }

    
    /**
     * Retrieves all of the links from this WebPage object.
     * Scans the HTML content using regular expressions to find
     * all links
     * @return An ArrayList of strings corresponding to the links found. 
     *
     */
    public ArrayList<URL> getLinks() {
        String next;
        URL newURL;
        ArrayList<String> links = getAttr("a", "href"); 
        ArrayList<URL> results = new ArrayList<>();
    
        
        for (String link: links){ 
            //link = iter.next();
            try {
                newURL = new URL(link);
            }catch(MalformedURLException e){
                if (link.equals("")) 
                    continue;
                if (link.charAt(0) == '#')
                    continue;
                try {
                    newURL = new URL(url, link);
                }catch (MalformedURLException f) {
                   System.out.println(f.getMessage());
                   continue;
                }
            }
           if (newURL.getProtocol().matches("(http|https)")) {
               results.add(newURL);
           }
           else {
               System.out.println("Unable to add + " + newURL.toString());
           }
        }
        return results;
    }
    
    /**
     * Searches the HTML document for a quoted attribute inside a given tag.
     * (e.g. getAttr("a", "href") finds links.
     * @param tag The HTML tag 
     * @param attr The attribute
     * @return The value of the attribute
     */
    protected ArrayList<String> getAttr(String tag, String attr) {
        ArrayList<String> result = new ArrayList<>();
        //Searches for HTML tag
        String tagReg = "<" + tag + "\\s[^>]*";
        //attribute prefix within tag
        String prefix = attr + "\\s*=\\s*[\'\"]\\s*";
        //Full attribute within tag
        String attrReg = prefix + "[^([\'\"]\\s)]*";                                                   
        
        
        Matcher tagMatch = Pattern.compile(tagReg).matcher(HTML);
        int first;
        char quote;
        String tagString;
        String attrString;
        String value;
        
        while (tagMatch.find()) {
            tagString = tagMatch.group();
            //System.out.println(tagString);
            Matcher attrMatch = Pattern.compile(attrReg).matcher(tagString);
            if (attrMatch.find())
                attrString = attrMatch.group();
            else
                continue;
            //System.out.println(attrString);
            //replace the attr = " part
            attrString = attrString.replaceFirst(prefix, "");
            //System.out.println(attrString);
            
            if (!(attrString.endsWith("\"") || attrString.endsWith("\'"))) {
                System.out.println("Parse Error: ");
                System.out.println(attrString);
                continue;
            }
            //Remove the trailing quote
            value = attrString.substring(0, attrString.length()-1);
            
            result.add(value);
        }
                
        
        return result;
    }
    
    /**
     * Searches the HTML to populates the stats fields and set the page type.
     * 
     */
    public abstract void scanPage();
    
    /**
     * Generates a report of the page's statistics.
     * @return A string with the report
     */
    public String getReport() {
        String report = "URL: " + url.toString() + "\n" +
                        "Title: " + title + "\n" + 
                        "Type: " + type + "\n";
        for (Record stat : stats) {
            report += stat.toString() + "\n";
        }
        return report;
                
    }
    
    public String getURLString() {
        return url.toString();
    }
     
    /**
     * Returns a string containing page title and type
     * @return 
     */
    @Override
    public String toString() {
        return title + ", + " + type + "page";
    }

    public String getTitle() {
       return title;
    }
    
    public String getType() {
        return type;
    }
    
    class Record {
        protected String label;
        protected int value;

        public Record(String label, int value) {
            this.label = label;
            this.value = value;
        }

        public String getLabel() {
            return label;
        }

        public int getValue() {
            return value;
        }

        @Override
        public String toString() {
            return label + ": " + value;
        }
    }
}
