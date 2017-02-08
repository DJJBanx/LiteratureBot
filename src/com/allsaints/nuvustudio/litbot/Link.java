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
        if (links.containsKey(key)) 
        	this.links.put(key, links.get(key) + 1);
        else
        	this.links.put(key, 1);
        totalLinks++;
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
    	HashMap<Double, String> map = new HashMap<>();

    	double prob;
    	int i = 0;
		for (String k : getTransitions()) {
			prob = (double) links.get(k) / (double) getSize();
			probs[i] = prob;
			map.put(prob, k);
			i++;
		}
		Arrays.sort(probs);
    	double c = rand.nextDouble();
    	double prev = 0;
    	double upper;
    	
    	for (int j = probs.length - 1; j >= 0; j--) {
    		upper = prev + probs[j];
    		//System.out.printf("%f <= %f && %f < %f\n", prev, c, c, upper);
    		if (prev <= c && c < upper) {
    			//System.out.printf("\t --> chose: %f\n", upper);
    			return map.get(probs[j]);
    		}
    		prev = upper;
    	}
    	return ""; // should never reach here;
    }
}