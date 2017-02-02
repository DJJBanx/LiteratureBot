package com.banx;

import com.banx.Utilities.Sentence;
import com.banx.Utilities.Word;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by shard on 2/1/2017.
 */
public class Reader {
    public static final String RED = "\u001B[31m";
    public static final String RESET = "\u001B[0m";

    private String book;
    private String title;

    private BufferedReader br = null;
    private FileReader fr = null;

    private ArrayList<Sentence> sentences;

    public Reader(String book) {
        this.book = book;
        sentences = new ArrayList<>();
        analyzeWords();
    }

    public void printStats() {
        for (Sentence s: sentences) {
            s.printStats();
        }
    }

    public void analyzeWords() {
        try {
            fr = new FileReader("src/com/banx/Books/"+book);
            br = new BufferedReader(fr);

            String sCurrentLine;
            Sentence currentSentence = new Sentence();
            String currentWordS="";
            char[] line;

            while ((sCurrentLine = br.readLine()) != null) {
                if (sCurrentLine.contains("#Book")) title = sCurrentLine.substring(5);
                else {
                    line = sCurrentLine.toCharArray();
                    for (int i=0;i<line.length;i++) {
                        char x = line[i];
                        //Debug
                        /**
                        try {
                            Thread.sleep(250);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        **/
                        //System.out.println(RED+x+RESET);
                        if (x!=' ' && x!='.' && i!=(line.length-1)) {
                            currentWordS+=x;
                            //Debug
                            System.out.println(currentWordS);
                        } else if (x==' ') {
                            if (!currentWordS.equals("")) {
                                currentSentence.addWord(new Word(currentWordS));
                                currentWordS = "";
                            }
                        } else if (x=='.') {
                            currentSentence.addWord(new Word(currentWordS));
                            sentences.add(new Sentence(currentSentence));
                            currentSentence.flush();
                            currentWordS="";
                        } else if (i==(line.length-1)) {
                            currentWordS+=x;
                            //Debug
                            System.out.println(currentWordS);
                            if (!currentWordS.equals("")) {
                                currentSentence.addWord(new Word(currentWordS));
                                currentWordS = "";
                            }
                        }
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) br.close();
                if (fr != null) fr.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
