package com.allsaints.nuvustudio.litbot;

import com.allsaints.nuvustudio.litbot.Link;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

public class Main {
	private static int width = 2;
	private static int depth = 100;
	
    public static void main(String[] args) {
        HashMap<String, Link> tree = new HashMap<String, Link>();
        String seed = "";
        
        try {
            String line;
            BufferedReader b = new BufferedReader(new FileReader("seed.txt"));
            while ((line = b.readLine()) != null) {
                seed += line + "\n";
            }
            b.close();
        } catch (IOException e) {
            seed = "The quick brown fox jumped over the lazy dog.";
        }
        
        seed.replace('\n', ' ');
        String[] input = seed.split(" ");
        
        String key = "";
        String piece = "";
        
        // train the markov chain
        for (int i = 0; i < input.length - (width + 1); i += width) {
            piece = slice(input, i, i + width);
            if (!tree.containsKey(key)) {
                tree.put(key, new Link());
            }
            tree.get(key).addTransition(piece);
            key = piece;
            i += width;
        }
        
        
        Random rand = new Random();
        Object[] keys = tree.keySet().toArray(); 
        
        // pick a random starting location
        String pos = (String)keys[rand.nextInt(keys.length)];
        
        // generate the text
        Link l;
        for (int j = 0; j < depth; j++) {
        	l = tree.get(pos);
        	if (l == null) {
        		break;
        	}
        	pos = l.choose();
            System.out.print(pos + " ");
        }
    }
   
    // get a slice of the seed, which is a series of words added together
    // from index start to index end-1
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