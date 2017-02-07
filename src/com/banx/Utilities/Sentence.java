package com.banx.Utilities;

import java.util.ArrayList;

/**
 * Created by shard on 2/1/2017.
 */
public class Sentence {
    ArrayList<Word> words = new ArrayList<>();

    public Sentence() {}

    public Sentence(Sentence sentence) {
        words = sentence.getWords();
    }

    public void printStats() {
        for (Word w:words) {
            w.printStats();
        }
    }

    public ArrayList<Word> getWords() {
        return new ArrayList<>(words);
    }

    public void addWord(Word word) {
        words.add(word);
    }
    public void addComma() {
        words.add(new Word("comma"));
    }

    public void addHyphen() {
        words.add(new Word("hyphen"));
    }
}
