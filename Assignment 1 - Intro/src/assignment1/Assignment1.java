/*
Joseph Kim
Assigment 1
Due 9/12/17
 */
package assignment1;

import java.util.*;
import java.net.*;
import java.io.*;

public class Assignment1 {

    public static void main(String[] args) throws Exception {
        Scanner scan = new Scanner(System.in);
        String Tokenizer, input;
        TextFileScrambler scramble = new TextFileScrambler();
        while (true) {
            System.out.println("Welcome to Word File scrambling.");
            System.out.println("(a) read file from disk");
            System.out.println("(b) read file from Web");
            System.out.println("(c) exit");
            System.out.println("From what source would you like to scramble from:");
            input = scan.nextLine();
            if (input.matches("a")) {
                Tokenizer = scramble.textfile();
                scramble.text_scrambler(input, Tokenizer);
            } else if (input.matches("b")) {
                Tokenizer = scramble.webpage();
                scramble.text_scrambler(input, Tokenizer);
            } else if (input.matches("c")) {
                System.exit(1); //creates error and exits
            }
        }
    }
}

class TextFileScrambler {

    Scanner scan, scantxt;
    String textfile, full_text, inputLine, finalString;
    String[] wordArray;
    char option;
    char[] temp;
    boolean exists, empty;
    int word_length, randomtext, counter;
    FileReader reader;
    StringBuilder full;
    Random random;
    Writer write_file;

    String textfile() throws IOException { //scamble textfile method (option a)
        scan = new Scanner(System.in);
        while (exists == false) {
            exists = true;
            // input text file name
            System.out.println("Name of text file (.txt):");
            textfile = scan.nextLine();
            try {
                reader = new FileReader(textfile); //reads from file
            } catch (FileNotFoundException i) {
                exists = false; //file not found
                System.out.println("\nError: File '" + textfile + "' cannot be found.");
            }
        }
        exists = false;  //make it reloop if no file is found
        // read text file
        scantxt = new Scanner(new BufferedReader(reader));
        full_text = scantxt.nextLine();
        return full_text;
    }

    String webpage() throws IOException { //scamble webpage method (option b)
        scan = new Scanner(System.in);
        while (exists == false) {
            exists = true;
            System.out.println("Enter URL (include http://):");
            textfile = scan.nextLine();
            try {
                URL oracle = new URL(textfile);
                try (BufferedReader in = new BufferedReader(
                        new InputStreamReader(oracle.openStream()))) { //reads whatever user input as a url and opens stream - connects to web
                    full = new StringBuilder();
                    while ((inputLine = in.readLine()) != null) {
                        full.append(inputLine);
                    }
                    finalString = full.toString();
                }
            } catch (MalformedURLException e) { //url doesn't exist
                exists = false;
                System.out.println("\nError: The URL '" + textfile + "' cannot be found.");
            }
        }
        exists = false;
        return finalString;
    }

    void text_scrambler(String input, String text) {
        full_text = text;
        // splits into array
        wordArray = full_text.split("\\s+");
        if (input.matches("a")) {
            // parse array
            for (int i = 0; i < wordArray.length; i++) {
                // deal with words that are four letters long or greater
                if (wordArray[i].length() >= 4) {
                    word_length = wordArray[i].length();
                    temp = wordArray[i].toCharArray(); // store word as array of characters
                    for (int j = 1; j <= (word_length - 2); j++) {
                        temp[j] = '0';
                    }
                    // word scrambler
                    for (int x = 1; x <= (word_length - 2); x++) {
                        empty = false;
                        random = new Random();
                        while (empty == false) {
                            randomtext = random.nextInt(word_length - 2) + 1;
                            if (temp[randomtext] == '0') {
                                empty = true;
                            }
                        }
                        temp[randomtext] = wordArray[i].charAt(x);
                    }
                    wordArray[i] = new String(temp); // store array into string
                }
            }
            random = new Random(); // generates new random file name to save into directory
            randomtext = random.nextInt(1000000);
            textfile = "Scrambled" + Integer.toString(randomtext) + ".txt";
            write_file = null;
            try {            // new text file
                write_file = new BufferedWriter(new OutputStreamWriter(
                        new FileOutputStream(textfile), "utf-8"));
                for (String split_text1 : wordArray) {
                    // write scrambled text to file
                    write_file.write(split_text1 + " ");
                }
                write_file.close();
                System.out.println("\nScrambled text file: " + textfile + "\nCheck"
                        + " Netbeans Project folder.");
            } catch (IOException fi) {
                System.err.println("Error: " + fi.getMessage());
            }
        } else if (input.matches("b")) {
            // parse words in array
            for (int i = 0; i < wordArray.length; i++) {
                // words 4 letters <=
                if (wordArray[i].length() >= 4) {
                    if (wordArray[i].matches(".*[^A-Za-z].*") == true) {
                        //does nothing
                    } else {
                        word_length = wordArray[i].length(); //gets length
                        temp = wordArray[i].toCharArray(); //stores characters as array

                        for (int j = 1; j <= (word_length - 2); j++) {
                            temp[j] = '0';
                        }

                        for (int k = 1; k <= (word_length - 2); k++) {
                            empty = false;
                            random = new Random();
                            while (empty == false) /*don't write in same directory twice */ {
                                randomtext = random.nextInt(word_length - 2) + 1;
                                if (temp[randomtext] == '0') {
                                    empty = true;
                                }
                            }
                            temp[randomtext] = wordArray[i].charAt(k);
                        }
                        // store array characters as string
                        wordArray[i] = new String(temp);
                    }
                }
            }
            // generate random text file name
            random = new Random();
            randomtext = random.nextInt(1000000);
            textfile = "Scrambled" + Integer.toString(randomtext) + ".html";
            write_file = null;
            // create new text file
            try {
                write_file = new BufferedWriter(new OutputStreamWriter(
                        new FileOutputStream(textfile), "utf-8"));
                // write scrambled text to file
                for (String split_text1 : wordArray) {
                    write_file.write(split_text1 + " ");
                }
                write_file.close();
                System.out.println("\nScrambled file name: " + textfile + "\nCheck"
                        + " Netbeans Project folder.");
            } catch (IOException fi) {
                System.err.println("Error: " + fi.getMessage());
            }
        }
    }
}
