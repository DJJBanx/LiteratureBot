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
        HashMap<String, Link> wordChain = new HashMap<String, Link>(); //able to change String to link
        HashMap<String, Link> puncChain = new HashMap<String, Link>();
        
        String seed = ""; // starts with the input of Strings
        
        try {
            String line;
            BufferedReader b = new BufferedReader(new FileReader("seed.txt")); //reads line by line
            while ((line = b.readLine()) != null) {
                seed += line + "\n"; // while there's still something there, add the line and an enter to the seed
            }
            b.close();
        } catch (IOException e) {
            seed = "The quick brown fox jumped over the lazy dog."; // if it doesn't work print this
        }
        
        seed.replace('\n', ' '); // replace new lines with spaces
        String[] input = seed.split(" "); // splits lines into words and puts the words in an array
        
        String key = ""; // key is the current word in the tree
        String piece = ""; // piece is the words following the key
        
        // train the markov chain
        for (int i = 0; i < input.length - (width + 1); i += width) {
            piece = slice(input, i, i + width);
            if (!wordChain.containsKey(key)) {
                wordChain.put(key, new Link()); // if the key is not in the hashmap, add the key to the hashmap
            }
            wordChain.get(key).addTransition(piece); // adds piece as a transition from key to piece (like dog to eats)
            key = piece; // change key to equal piece to move onto the next word to make the words logical in placement
        }
        
        
        Random rand = new Random();
        Object[] keys = wordChain.keySet().toArray(); 
        
        // pick a random starting location
        String pos = (String)keys[rand.nextInt(keys.length)];
        
        // generate the text
        Link l;
        for (int j = 0; j < depth; j++) {
        	l = wordChain.get(pos);
        	if (l == null) {
        		break;
        	}
        	pos = l.choose();
        	System.out.print(pos + " ");
        }
    }
   
    // get a slice of the seed, which is a series of words added together
    // from index start to index end-1
    // puts all elements of the array of words into a sentence
    public static String slice(String[] input, int start, int end) {
        String acc = "";
        int i = start;
        while (i < end) {
            acc = acc + input[i] + " ";
            ++i;
        }
        return acc.trim();
    }
}