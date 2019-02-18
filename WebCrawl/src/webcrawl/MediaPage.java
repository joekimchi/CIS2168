/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package webcrawl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 *
 * @author auxioruben
 */
public class MediaPage extends WebPage {
    
    
    public MediaPage(String urlString, String HTML) throws MalformedURLException {
        this(new URL(urlString), HTML);
        
    }
    
    public MediaPage(String urlString) throws MalformedURLException, IOException {
        this(new URL(urlString), WebPage.getHTML(urlString));
    }
    
    public MediaPage(URL url, String HTML) {
        super(url, HTML);
        type = "Media";
    }
    
    
    
    @Override
    public void scanPage() {
        ArrayList<String> imgs = this.getAttr("img", "src");
        ArrayList<String> types = new ArrayList();
        String[] parts;
        String next = "";
        
        
        for (String img : imgs) {
            //System.out.println(img);
            //Get next extention
            parts = img.split("/");
            if (parts.length > 1) {
                next = parts[parts.length-1];
                next = next.substring(next.indexOf(".")+1);
            }else
                next = parts[0].substring(parts[0].indexOf(".")+1);
            //System.out.println(next);
            //If extension is already in our list, update it,
            //otherwise create a new record
            boolean found = false;
            for (int i = 0; i < types.size(); i++) {
                if (next.equals(types.get(i))) {
                    found = true;
                    stats.get(i).value++;
                    break;
                }
            }
            if(!found)  {
                types.add(next);
                stats.add(new Record(next, 1)); }
        }
    
    }
            
    
}
