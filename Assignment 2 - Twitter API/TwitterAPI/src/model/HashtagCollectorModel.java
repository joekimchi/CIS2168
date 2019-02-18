package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Comparator;

import twitter4j.Status;

public class HashtagCollectorModel {

    // Sub class to reflect a pair of items
    public class Pair<L, R> {

        private final L left;
        private final R right;

        public Pair(L left, R right) {
            this.left = left;
            this.right = right;
        }

        public L getLeft() {
            return left;
        }

        public R getRight() {
            return right;
        }

        @Override
        public int hashCode() {
            return left.hashCode() ^ right.hashCode();
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Pair)) {
                return false;
            }
            Pair pairo = (Pair) o;
            return this.left.equals(pairo.getLeft())
                    && this.right.equals(pairo.getRight());
        }

    }

    /**
     * Function: Used to extract hash tags from a tweet.
     *
     * @param line : String
     * @return List<String>
     */
    private List<String> getHashTags(String line) {
        String[] words = line.split(" ");

        List<String> hashtags = new ArrayList<>();
        for (String word : words) {
            if (word.startsWith("#")) {
                hashtags.add(word);
            }
        }

        return hashtags;
    }

    private List<Pair<String, LinkedList<Status>>> modelDataStructure;

    // Limit on number of top tweets to return which we can change
    private static final int MAX_LIMIT = 10;

    /**
     * Public constructor.
     */
    public HashtagCollectorModel() {
        modelDataStructure = new ArrayList<>();
    }

    public void saveTweets(List<Status> tweets) {
        for (Status tweet : tweets) {
            List<String> hashTags = getHashTags(tweet.getText());

            for (String hashTag : hashTags) {
                addToHashTagList(hashTag, tweet);
            }
        }

        // Sort all the list after each function call
        Collections.sort(modelDataStructure, new PairComparator());
    }

    /**
     * Function: Used to add for a single hash-tag
     *
     * @param hashTag
     * @param tweet
     */
    private void addToHashTagList(String hashTag, Status tweet) {

        boolean found = false;

        for (Pair<String, LinkedList<Status>> p : this.modelDataStructure) {
            if (p.left.equals(hashTag)) {
                found = true;
                p.right.add(tweet);
                break;
            }
        }

        if (found == false) {
            LinkedList<Status> list = new LinkedList<>();
            list.add(tweet);
            this.modelDataStructure.add(new Pair<String, LinkedList<Status>>(hashTag, list));
        }

        return;
    }

    /**
     * Comparator class to compare custom pair
     *
     */
    public class PairComparator implements Comparator<Pair<String, LinkedList<Status>>> {

        @Override
        public int compare(Pair<String, LinkedList<Status>> o1,
                Pair<String, LinkedList<Status>> o2) {
            if (o1.right.size() > o2.right.size()) {
                return -1;
            } else if (o1.right.size() == o2.right.size()) {
                return 0;
            } else {
                return 1;
            }
        }
    }

    /**
     * Function: Returns the list of top hash-tags.
     *
     * @return
     */
    public List<Pair<String, Integer>> getTopHashTags() {
        List<Pair<String, Integer>> topHashTags
                = new ArrayList<>();

        for (int i = 0; i < MAX_LIMIT; i++) {
            topHashTags.add(new Pair<String, Integer>(
                    modelDataStructure.get(i).left, modelDataStructure.get(i).right.size()));
        }

        return topHashTags;
    }
}
