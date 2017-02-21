package com.allsaints.nuvustudio.litbot.Testing;
import java.util.HashMap;

import com.allsaints.nuvustudio.litbot.Link;

/**
 * Created by zfrielich on 2/14/17.
 */
public class Test {
    public static void main(String[] args) {
        Link l = new Link();
        int var = 10;
        l.addTransition("hello");
        l.addTransition("my name is");
        l.addTransition("James");
        l.addTransition("hello");
        
        HashMap<String, Integer> times = new HashMap<>();
        String s = "";
        for (int i = 0; i<var; i++) {
            s = l.choose();
            if (!times.containsKey(s))
            	times.put(s, 1);
            else
            	times.put(s, times.get(s) + 1);
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Expect hello " + (var*.5));
        System.out.println("Expect my " + (var*.25));
        System.out.println("Expect James " + (var *.25));
        System.out.println(times.get("hello"));
        System.out.println(times.get("my name is"));
        System.out.println(times.get("James"));
    }
}
