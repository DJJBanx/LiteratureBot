package com.banx.Utilities;

import java.io.*;
import java.util.Scanner;

import static com.banx.Utilities.Word.Type.*;


/**
 * Created by shard on 2/1/2017.
 */
public class Word {
    private String word;
    private int lcount;
    public enum Type {
        Noun,
        Verb,
        Adjective,
        Adverb,
        Pronoun,
        Preposition,
        Conjunction,
        Determiner,
        Exclamation;
    }
    private Type type;
    private BufferedReader br = null;
    private FileReader fr = null;
    private BufferedWriter bw = null;
    private FileWriter fw = null;

    public Word(String word) {
        if (word.equals("comma")){
            this.word = ",";
            lcount = 0;
        } else if (word.equals("hyphen")) {
            this.word = "-";
            lcount = 0;
        }
        else {
            this.word = word.toLowerCase();
            lcount = word.length();
            checkStatus();
        }
    }

    public void printStats() {
        System.out.println("Stats of " + word + ":");
        System.out.println("Length: " + lcount);
        System.out.println("Type: " + type);
    }

    private void checkStatus() {
        try {
            //Debug
            //System.out.println(word.charAt(0));
            fr = new FileReader("src/com/banx/Library/" + word.charAt(0) + ".txt");
            br = new BufferedReader(fr);
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                if (sCurrentLine.contains(word)) {
                    type = getType(sCurrentLine.substring(word.length()+1));
                }
            }
            if (type==null) askForMeaning();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fr!=null) fr.close();
                if (br!=null) br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //Cannot write to file
    private void askForMeaning() {
        Scanner s = new Scanner(System.in);
        Type type = null;
        String t = null;
        while (type == null) {
            System.out.println("What is the TYPE of " + word + "?");
            t = s.nextLine();
            type = getType(t);
        }
        try {
            fw = new FileWriter("src/com/banx/Library/" + word.charAt(0) + ".txt", true);
            bw = new BufferedWriter(fw);

            bw.newLine();
            bw.write(word + "=" + t);

            System.out.println("Done");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bw != null) bw.close();
                if (fw != null) fw.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        checkStatus();
    }

    public Type getType(String type) {
        switch(type.toLowerCase()) {
            case "noun":
                return Noun;
            case "verb":
                return Verb;
            case "adjective":
            case "adj":
                return Adjective;
            case "adverb":
                return Adverb;
            case "pronoun":
                return Pronoun;
            case "preposition":
            case "prep":
                return Preposition;
            case "conjunction":
            case "conj":
                return Conjunction;
            case "determiner":
            case "det":
                return Determiner;
            case "exclamation":
            case "ex":
                return Exclamation;
        }
        return null;
    }
}
