package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import model.HashtagCollectorModel.Pair;
import twitter4j.Status;
import controller.HashtagCollectorController;

public class HashtagCollectorUI extends JPanel {

    private static final long serialVersionUID = 1L;
    private final HashtagCollectorController controller;

    // Private class instance variables
    private final JLabel keyword;
    private final JTextField keywordTextbox;
    private final JTextArea topTweetsArea;
    private final JTextArea queryResults;
    private final JButton saveButton;
    private final JButton submitButton;
    private final JScrollPane queryScroll;
    private final JScrollPane topTweetScroll;
    private List<Status> currentTweets;

    public HashtagCollectorUI() {

        // Initialize all fields
        keyword = new JLabel("Keyword");

        keywordTextbox = new JTextField(50);

        topTweetsArea = new JTextArea(30, 40);
        queryResults = new JTextArea(30, 40);

        saveButton = new JButton("SAVE");
        submitButton = new JButton("SUBMIT");

        controller = new HashtagCollectorController();

        // Set layout of this content pane
        this.setLayout(new BorderLayout());

        // Our top Panel contains both labels
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());

        // Set a border around keyword and add it to EAST part of top panel
        keyword.setBorder(new EmptyBorder(5, 10, 5, 10));
        topPanel.add(keyword, BorderLayout.WEST);

        // Set a border around keyword and add it to EAST part of top panel
        keywordTextbox.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        topPanel.add(keywordTextbox, BorderLayout.EAST);

        // Our south panel contains both buttons
        JPanel southPanel = new JPanel();
        southPanel.setLayout(new BorderLayout());

        // Set a border around submit button and add it to the NORTH part of west panel
        submitButton.setBorder(BorderFactory.createLineBorder(Color.black));
        southPanel.add(submitButton, BorderLayout.WEST);

        // Set a border around save button and add it to the SOUTH part of west panel
        saveButton.setBorder(BorderFactory.createLineBorder(Color.black));
        southPanel.add(saveButton, BorderLayout.EAST);

        // Our center panel contains both text areas
        JPanel centerPanel = new JPanel();

        // Set a border around queryResults TextAreas and add it to the NORTH part of east panel
        queryResults.setEditable(false);
        queryScroll = new JScrollPane(queryResults,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        queryScroll.setBorder(BorderFactory.createTitledBorder("Query Results"));

        centerPanel.add(queryScroll);

        // Set a border around topTweetsArea and add it to the SOUTH part of east panel
        topTweetsArea.setEditable(false);
        topTweetScroll = new JScrollPane(topTweetsArea,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        topTweetScroll.setBorder(BorderFactory.createTitledBorder("Top Tweets"));

        centerPanel.add(topTweetScroll);

        // Create a border around topPanel and add to content pane
        topPanel.setBorder(new EmptyBorder(10, 10, 5, 10));
        this.add(topPanel, BorderLayout.NORTH);

        // Create a border around southPanel and add to content pane
        southPanel.setBorder(new EmptyBorder(10, 10, 5, 10));
        this.add(southPanel, BorderLayout.SOUTH);

        // Create a border around east and add to content pane
        centerPanel.setBorder(new EmptyBorder(10, 10, 5, 10));
        this.add(centerPanel, BorderLayout.CENTER);

        // Set initial values:
        keyword.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        saveButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        submitButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        queryResults.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
        topTweetsArea.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));

        // Add action listener for this button
        submitButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String queryKey = keywordTextbox.getText();

                if (queryKey.isEmpty()) {
                    JOptionPane.showMessageDialog(null,
                            "Please enter some query first", "Error Message", JOptionPane.ERROR_MESSAGE, null);
                    return;
                }

                keywordTextbox.setText("");

                // Get the tweets
                currentTweets = controller.handle(queryKey);

                if (currentTweets == null) {
                    JOptionPane.showMessageDialog(null, controller.getErrorMessage(),
                            "Error Message", JOptionPane.ERROR_MESSAGE, null);
                }

                // Create a new string builder
                StringBuilder sb = new StringBuilder();

                int count = 0, maxLimit = 10;

                // Add tweets to it
                for (Status tweet : currentTweets) {
                    sb.append(("#" + (count + 1)) + "] " + tweet.getText() + "\n\n");

                    count++;		// Increase the count
                    if (count >= maxLimit) {
                        break;
                    }
                }

                // Print the tweets
                queryResults.setText(sb.toString());
            }
        });

        saveButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentTweets == null) {
                    JOptionPane.showMessageDialog(null,
                            "No tweets to save.", "Error Message", JOptionPane.ERROR_MESSAGE, null);
                    return;
                } else {
                    List<Pair<String, Integer>> topHashtags = controller.saveTweets(currentTweets);
                    StringBuilder sb = new StringBuilder();

                    for (int i = 0; i < topHashtags.size(); i++) {
                        Pair<String, Integer> p = topHashtags.get(i);
                        sb.append(("#" + (i + 1)) + "]. " + p.getLeft() + " Count: " + p.getRight() + "\n\n");
                    }

                    topTweetsArea.setText(sb.toString());
                }
            }
        });

    }

    /**
     * This function sets up the GUI.
     */
    public static void createAndShowGUI() {

        JFrame frame = new JFrame("Tweet Collector");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JComponent newContentPane = new HashtagCollectorUI();
        newContentPane.setOpaque(true);
        frame.setContentPane(newContentPane);
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Main driver function.
     *
     * @param args
     */
    public static void main(String[] args) {
        // This uses a method reference, which we'll discuss in Module 13.
        SwingUtilities.invokeLater(HashtagCollectorUI::createAndShowGUI);
    }

}
