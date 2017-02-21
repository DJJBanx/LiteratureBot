package com.allsaints.nuvustudio.litbot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Link {
    private HashMap<String, Integer> links = new HashMap<>();
    private Random rand = new Random();
    private int totalLinks = 0;
    
    public void addTransition(String key) {
    	System.out.println(key);
        if (links.containsKey(key)) 
        	this.links.put(key, links.get(key) + 1);
        else
        	this.links.put(key, 1);
        totalLinks++;
        System.out.println(links.get(key));
    }

    public double getProb(String key) {
        return (double)links.get(key) / (double)this.getSize();
    }

    public int getSize() {
        return totalLinks;
    }

    public Set<String> getTransitions() {
        Set<String> t = links.keySet();
        return t;
    }

    public String choose() {
    	double[] probs = new double[getSize()];
    	String[] choices = new String[getSize()];
    	
    	HashMap<Double, String> map = new HashMap<>();

    	double prob;
    	int i = 0;
		// from my understanding the problem is the fact that you're using a set under getTransitions and
		// sets don't allow for duplicates so you can't actually get the true probability
		// so right now this would go through 3 elements and set prob equal to the links value of k over the total number of links
    	
    	// the problem is duplicate probabilties! if two things have the same probablity
		for (String k : getTransitions()) {
			prob = (double) links.get(k) / (double) getSize(); //does this check for all instances of String k or just one?
			probs[i] = prob;
			map.put(prob, k);
			System.out.printf("%f : %s\n", prob, map.get(prob));
			i++;
		}
		Arrays.sort(probs);

    	double c = rand.nextDouble();
    	double prev = 0;
    	double upper;
    	
    	for (double d : probs) {
    		System.out.printf("%f --> %s\n", d, map.get(d));
    	}
    	System.out.println();
    	
    	for (int j = probs.length - 1; j >= 0; j--) {
    		upper = prev + probs[j];
    		//System.out.printf("%f <= %f && %f < %f\n", prev, c, c, upper);
    		if (prev <= c && c <= upper) {
    			//System.out.printf("\t --> chose: %f\n", upper);
    			return map.get(probs[j]);
    		}
    		prev = upper;
    	}
    	return ""; // should never reach here;
    }
}