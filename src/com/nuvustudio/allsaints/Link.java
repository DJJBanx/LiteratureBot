package com.nuvustudio.allsaints;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class Link {
    private ArrayList<String> links = new ArrayList<>(3);
    private Random rand = new Random();

    public void addTransition(String key) {
        this.links.add(key); 
    }

    public double getProb(String key) {
        int nKey = 0;
        for (String s : this.links) {
            if (!s.equals(key)) continue;
            ++nKey;
        }
        return (double)nKey / (double)this.getSize();
    }

    public int getSize() {
        return this.links.size();
    }

    public HashSet<String> getTransitions() {
        HashSet<String> t = new HashSet<String>();
        for (String s : this.links) {
            t.add(s);
        }
        return t;
    }

    public String choose() {
    	return null;
    }
}