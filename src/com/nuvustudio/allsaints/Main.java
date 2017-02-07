package com.nuvustudio.allsaints;

import com.nuvustudio.allsaints.Link;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Reader;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        HashMap<String, Link> tree = new HashMap<String, Link>();
        String seed = "";
        try {
            String line;
            BufferedReader b = new BufferedReader(new FileReader("seed.txt"));
            while ((line = b.readLine()) != null) {
                seed += line + "\n";
            }
        }
        catch (IOException e) {
            seed = "The quick brown fox jumped over the lazy dog.";
        }
        seed.replace('\n', ' ');
        String[] input = seed.split(" ");
        int width = 2;
        String key = "";
        String piece = "";
        int i = 0;
        while (i < input.length - (width + 1)) {
            piece = slice(input, i, i + width);
            if (!tree.containsKey(key)) {
                tree.put(key, new Link());
            }
            tree.get(key).addTransition(piece);
            key = piece;
            i += width;
        }
        int depth = 100;
        Random rand = new Random();
        Object[] keys = tree.keySet().toArray(); 
        String pos = (String)keys[rand.nextInt(keys.length)];
        //System.out.print(pos + " ");
        int i2 = 0;
        while (i2 < depth) {
        	if (tree.get(pos) == null);
        		//System.out.println(" !!! " + tree.get(pos));
            pos = tree.get(pos).choose();
            System.out.print(pos + " ");
            ++i2;
        }
    }

    public static String slice(String[] input, int start, int end) {
        String acc = "";
        int i = start;
        while (i < end) {
            acc = String.valueOf(acc) + input[i] + " ";
            ++i;
        }
        return acc.trim();
    }
}