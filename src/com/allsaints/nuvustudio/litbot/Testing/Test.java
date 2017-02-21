package com.allsaints.nuvustudio.litbot.Testing;
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
        int numTimesHello = 0;
        int numTimesMy=0;
        int numTimesJames = 0;

        String s = "";
        for (int i = 0; i<var; i++) {
            s = l.choose();
            System.out.println(s);
            if (s.equals("hello")) {
                numTimesHello++;
            } else if(s.equals("my name is")) {
                numTimesMy++;
            } else if (s.equals("James")) {
                numTimesJames++;
            } else {
                System.out.println("poo");
            }
            /*try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
        }
        System.out.println("Expect hello " + (var*.5));
        System.out.println("Expect my " + (var*.25));
        System.out.println("Expect James " + (var *.25));
        System.out.println(numTimesHello/(double)var);
        System.out.println(numTimesMy/(double)var);
        System.out.println(numTimesJames/(double)var);
    }
}
