package controller;

import java.util.List;

import model.HashtagCollectorModel;
import model.HashtagCollectorModel.Pair;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class HashtagCollectorController {

    private final Twitter twitter;
    private final HashtagCollectorModel model;
    private String errorMessage;
    private static final String CONSUMER_KEY = "257q5IWoegGKaFY6Yy0xbjXHF";
    private static final String CONSUMER_SECRET = "rJ7IrmOxTfis2b8TKuxew5NWZbRc5bgCMlHdhO9ycwyCAwELwy";
    private static final String OAUTH_ACCESS_TOKEN = "53442479-eokgZwZcLiDD3DjHccbEBCONDJEJ7y6QJFRlnpy9s";
    private static final String OAUTH_ACCESS_TOKEN_SECRET = "DIijAWEgCSJcDWmIZkOJ5kDtdCz9F4Dulh2LtfD8VeuIG";

    /**
     * Public constructor.
     */
    public HashtagCollectorController() {
        // We will programmatically set the authentication properties.
        ConfigurationBuilder cb = new ConfigurationBuilder();

        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(CONSUMER_KEY)
                .setOAuthConsumerSecret(CONSUMER_SECRET)
                .setOAuthAccessToken(OAUTH_ACCESS_TOKEN)
                .setOAuthAccessTokenSecret(OAUTH_ACCESS_TOKEN_SECRET);

        model = new HashtagCollectorModel();

        TwitterFactory tf = new TwitterFactory(cb.build());

        // Initialize the twitter object
        twitter = tf.getInstance();
    }

    /**
     * Function: Used to fetch the tweets.
     *
     * @param queryKey
     * @return
     */
    public List<Status> handle(String queryKey) {
        try {
            Query query = new Query(queryKey);
            QueryResult result = twitter.search(query);
            List<Status> tweets = result.getTweets();
//            for (Status tweet : tweets) {
//                System.out.println("@" + tweet.getUser().getScreenName() + " - " + tweet.getText());
//            }


            return tweets;
        } catch (TwitterException te) {
            this.setErrorMessage("Failed to search tweets: " + te.getMessage());
            return null;
        }
    }

    // Setter for error message
    private void setErrorMessage(String msg) {
        this.errorMessage = msg;
    }

    // Getter for error message
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Function: Used to save the tweets and returns the list of top-hashtags
     *
     * @param tweets
     * @return
     */
    public List<Pair<String, Integer>> saveTweets(List<Status> tweets) {
        model.saveTweets(tweets);

        List<Pair<String, Integer>> topHashtags = model.getTopHashTags();
        return topHashtags;
    }
}
