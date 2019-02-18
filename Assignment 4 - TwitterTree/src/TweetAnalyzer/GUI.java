package TweetAnalyzer;

import java.awt.*;
import java.awt.event.*;
import java.util.PriorityQueue;
import javax.swing.*;

public class GUI {
    private BinaryTree a;
    private PriorityQueue pq;
    private int K;
    private Huffman h;
    
    GUIListener AL = new GUIListener();

    //stuff in first window
    JFrame guiFrame = new JFrame("Work on tweets");
    JButton submit = new JButton("submit");
    JTextField inputK = new JTextField(20);
    JTextArea StopWords = new JTextArea(20,40);
    JButton subtreeb = new JButton("Subtree");
    JButton Huffmanb = new JButton("Huffman");
    
    //binary window
    JFrame guiFrame2 = new JFrame("Binary subtree");
    JTextField inputS = new JTextField(20);
    JButton display = new JButton("Display");
    JTextArea subtree = new JTextArea(20,40);
    JScrollPane spsw = new JScrollPane(subtree, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    
    //huffman window
    JFrame guiFrame3 = new JFrame("Huffman code");
    JTextField inputH = new JTextField(20);
    JButton displayH = new JButton("Display");
    JTextArea huffman = new JTextArea(20,40);
    JScrollPane hcsp = new JScrollPane(huffman, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    
    //constructor
    public GUI(BinaryTree a, PriorityQueue pq) {
        this.a = a;
        this.pq = pq;
        this.K = 10;
    }
    
    //default
    public void firstWindow(){
        guiFrame.setSize(700, 500);
        
        guiFrame.add(new JLabel("There are " + pq.size() + " words in the BST. "
                + "Enter the number of stop words you want to display:"));
        guiFrame.add(inputK);
        guiFrame.add(submit);
        submit.addActionListener(AL);
        StopWords.setLineWrap(true);
        StopWords.setWrapStyleWord(true);
        guiFrame.add(StopWords);
        guiFrame.add(subtreeb);
        guiFrame.add(Huffmanb);
        subtreeb.addActionListener(AL);
        Huffmanb.addActionListener(AL);
        guiFrame.setLocationRelativeTo(null);
        guiFrame.setLayout(new FlowLayout());
        guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        guiFrame.setVisible(true);
    }

    //subtree window
    public void TreeWindow(){
        guiFrame2.setSize(700, 500);
        
        guiFrame2.add(new JLabel("Enter word to find it's subtree: "));
        guiFrame2.add(inputS);
        guiFrame2.add(display);
        display.addActionListener(AL);
        
        subtree.setLineWrap(true);
        subtree.setWrapStyleWord(true);
        guiFrame2.add(spsw);
        guiFrame2.setLocationRelativeTo(null);
        guiFrame2.setLayout(new FlowLayout());
        guiFrame2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        guiFrame2.setVisible(true);
    }
    
    //huffman window
    public void HuffmanWindow(){
        guiFrame3.setSize(700, 500);
        
        guiFrame3.add(new JLabel("Enter word to find its Huffman code: "));
        guiFrame3.add(inputH);
        guiFrame3.add(displayH);
        displayH.addActionListener(AL);
        
        huffman.setLineWrap(true);
        huffman.setWrapStyleWord(true);
        guiFrame3.add(hcsp);
        guiFrame3.setLocationRelativeTo(null);
        guiFrame3.setLayout(new FlowLayout());
        guiFrame3.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        guiFrame3.setVisible(true);
    }
    
    public class GUIListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            //first window
            //get stop words
            if (e.getSource() == submit){
                if(inputK != null)
                    K = Integer.valueOf(inputK.getText()); 
                StopWords.append("Stop words:\n");
                for (int i = 1; i <= K; i++){
                    String temp = pq.poll().toString();
                    StopWords.append(i + ". " + temp + "\n");
                    a.remove(new Words(temp));
                }
            }
            //open subtree window
            if (e.getSource() == subtreeb){
                TreeWindow();
            }
            //open huffman window
            if (e.getSource() == Huffmanb){
                HuffmanWindow();
                h = new Huffman(a);
            }
            
            //Second window
            //display subtree
            if (e.getSource() == display){
                subtree.setText(a.subtree(inputS.getText()));
            }
            
            //Third window
            //print the huffman code
            if (e.getSource() == displayH){
                huffman.setText(h.decode(inputH.getText()));
            }
        }
    }
}
