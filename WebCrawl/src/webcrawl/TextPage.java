/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package webcrawl;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Subclass of WebPage to contain textual pages 
 * @author John Edwardson
 */
public class TextPage extends WebPage {
    private String[] searchStrings;
    
    public TextPage(String urlString, String HTML) throws MalformedURLException {
        this(new URL(urlString), HTML);
    }
    
    public TextPage(String urlString) throws MalformedURLException, IOException {
        this(new URL(urlString), WebPage.getHTML(urlString));
    }
    
    public TextPage(URL url, String HTML) {
        super(url, HTML);
        searchStrings = new String[]{"temple", "computer", "university"};
        type = "Text";
    }
    
    @Override
    public void scanPage() {
   
        String searchPattern = "(";
        for (int i = 0; i < searchStrings.length ; i++) {
            searchPattern += searchStrings[i].toLowerCase();
            if (i < searchStrings.length-1)
                searchPattern += "|";
            else
                searchPattern += ")";
            stats.add(new Record(searchStrings[i].toLowerCase(), 0));
        }
        System.out.println(searchPattern);
        
        Matcher searcher = Pattern.compile(searchPattern).matcher(HTML.toLowerCase());
        while(searcher.find()) {
            for (Record stat : stats) {
                if (searcher.group().equals(stat.label))
                    stat.value++;
            }
        }
        
  
    }
    

    
  
            
    
    
}
