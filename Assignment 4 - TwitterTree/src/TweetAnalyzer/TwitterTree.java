 package TweetAnalyzer;

import java.util.List;
import twitter4j.*;
import twitter4j.auth.*;
import java.io.*;
import java.util.PriorityQueue;
import java.util.Scanner;

public class TwitterTree {
    public static void saveDoc(String contents, String filename) {
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new FileWriter(new File(filename)));
            pw.print(contents);
        } catch (IOException ioe) {
            ioe.printStackTrace();
            System.err.println("Error writing to file:  " + filename);
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
    }

    public static void main(String[] args) {
        /*
        //get tweets
        Twitter twitter = new TwitterFactory().getInstance();
        //Twitter Consumer key & Consumer Secret
        twitter.setOAuthConsumer("257q5IWoegGKaFY6Yy0xbjXHF", "rJ7IrmOxTfis2b8TKuxew5NWZbRc5bgCMlHdhO9ycwyCAwELwy");
        //Twitter Access token & Access token Secret
        twitter.setOAuthAccessToken(new AccessToken("53442479-eokgZwZcLiDD3DjHccbEBCONDJEJ7y6QJFRlnpy9s", "DIijAWEgCSJcDWmIZkOJ5kDtdCz9F4Dulh2LtfD8VeuIG"));
        try {
            int index = 0;
            String tweets = "";
            for (int i = 1; i <= 4; i++) {
                Paging page = new Paging(i, 200);     //page number, number per page
                List<Status> statuses = twitter.getHomeTimeline(page);
                //System.out.println("Showing home timeline.");
                for (Status status : statuses) {
                    //System.out.println(status.getUser().getName() + ":" + status.getText());
                    tweets += status.getText() + "\n";
                    index++;
                }
            }
            saveDoc(tweets, "tweets.txt");              //a function that save the tweets into a file
            System.out.println("index " + index);
        } catch (TwitterException ex) {
            //java.util.logging.Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
*/
        //create the binary tree
        BinaryTree a = new BinaryTree();
        Scanner scan = null;
        try {
            scan = new Scanner(new File("text file.txt"));
            scan.useDelimiter("\\W+");
            a.add(new Words(scan.next()));
            while (scan.hasNext()) {
                Words temp = new Words(scan.next().toLowerCase());
                if (a.find(temp) == null) {
                    a.add(temp);
                } else {
                    a.find(temp).word.add();
                }
            }
        } catch (FileNotFoundException exception) {
            System.out.println("Could not locate file.");
        } catch (IOException ioe) {
            System.out.println("Error reading from file.");
        }
        //System.out.println(a);        //check the binary tree
        PriorityQueue pq = a.GetPQ();   //method body in BinaryTree class
        GUI gui = new GUI(a, pq); //GUI
        gui.firstWindow();
    }
}
