/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package webcrawl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author auxioruben
 */
public class FormPage extends WebPage {
    
        
    public FormPage(String urlString, String HTML) throws MalformedURLException {
        this(new URL(urlString), HTML);
    }
    
    public FormPage(String urlString) throws MalformedURLException, IOException {
        this(new URL(urlString), WebPage.getHTML(urlString));
    }
    
    public FormPage(URL url, String HTML) {
        super(url, HTML);
        type = "Form";
    }
    
    @Override
    public void scanPage() {
        String searches[] = {"input", "output", "select", "button"};
        
        for (String search : searches) {
            stats.add(new Record(search, countTags(search)));
        }    
    }
    
    private int countTags(String tag) {
        int count = 0;
        String tagReg = "<" + tag + "\\s[^>]";
        Matcher tagMatcher = Pattern.compile(tagReg).matcher(HTML.toLowerCase());
        
        while(tagMatcher.find()) {
            System.out.println(tagMatcher.group());
            count++;
        }
        return count;
    }
            
    
}
   
