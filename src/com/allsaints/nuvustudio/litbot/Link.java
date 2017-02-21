package com.allsaints.nuvustudio.litbot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
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
        if (links.containsKey(key))
        	return (double)links.get(key) / (double)this.getSize();
        else
        	return 0;
    }

    public int getSize() {
        return totalLinks;
    }

    public Set<String> getTransitions() {
        Set<String> t = links.keySet(); // takes all elements and sets them to a probability
        return t;
    }

    public String choose() {
    	double[] probs = new double[getSize()];
    	String[] choices = new String[getSize()];
    	

    	double prob;
    	int i = 0;
		// from my understanding the problem is the fact that you're using a set under getTransitions and
		// sets don't allow for duplicates so you can't actually get the true probability
		// so right now this would go through 3 elements and set prob equal to the links value of k over the total number of links
    	
    	// the problem is duplicate probabilties! if two things have the same probablity


		for (String k : getTransitions()) {
			prob = getProb(k);
			probs[i] = prob;
			choices[i] = k;
			i++;
		}
		Arrays.sort(probs);
		Arrays.sort(choices, new Comparator<String>() {
			@Override
			public int compare(String x, String y) {
				double xp = getProb(x);
				double yp = getProb(y);
				if (xp < yp) {
					return -1;
				} else if (Math.abs(xp-yp) < 0.0000001) { // margin of error
					return 0;
				} else if (xp > yp) {
					return 1;
				}
				return 0;
			}
			
		});
		
    	double c = rand.nextDouble();
    	double prev = 0;
    	double upper;
    	
    	System.out.println();
    	
    	for (int j = probs.length - 1; j >= 0; j--) {
    		upper = prev + probs[j];
    		//System.out.printf("%f <= %f && %f < %f\n", prev, c, c, upper);
    		if (prev <= c && c <= upper) {
    			//System.out.printf("\t --> chose: %f\n", upper);
    			return choices[j];
    		}
    		prev = upper;
    	}
    	return ""; // should never reach here;
    }
}