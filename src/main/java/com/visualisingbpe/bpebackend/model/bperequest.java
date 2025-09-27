package com.visualisingbpe.bpebackend.model;

public class bperequest {
    private String sentence;
    private int merges;

    public String getSentence() { return sentence; }
    public void setSentence(String sentence) { this.sentence = sentence; }

    public int getMerges() { return merges; }
    public void setMerges(int merges) { this.merges = merges; }
}
