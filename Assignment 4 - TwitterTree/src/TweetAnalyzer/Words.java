/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TweetAnalyzer;

public class Words {
    private String word = new String();
    private int times = 1;

    public Words(String word) {
        this.word = word;
    }
    
    public void add(){
        times++;
    }

    public String getWord() {
        return word;
    }

    public int getTimes() {
        return times;
    }
    
    
}
