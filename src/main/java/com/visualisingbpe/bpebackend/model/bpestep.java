package com.visualisingbpe.bpebackend.model;

import java.util.List;
import java.util.Map;

public class bpestep {
    private int step;
    private String type;          // "initial", "gather", "merge"
    private List<String> tokens;
    private List<String> merge;   // pair to merge
    private Map<String, Integer> frequencies;

    public int getStep() { return step; }
    public void setStep(int step) { this.step = step; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public List<String> getTokens() { return tokens; }
    public void setTokens(List<String> tokens) { this.tokens = tokens; }

    public List<String> getMerge() { return merge; }
    public void setMerge(List<String> merge) { this.merge = merge; }

    public Map<String, Integer> getFrequencies() { return frequencies; }
    public void setFrequencies(Map<String, Integer> frequencies) { this.frequencies = frequencies; }
}
