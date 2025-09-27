package com.visualisingbpe.bpebackend.service;

import com.visualisingbpe.bpebackend.model.bpestep;
import com.visualisingbpe.bpebackend.model.bperesponse;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class bpeservice {

    public bperesponse applyBpe(String sentence, int merges) {
        List<bpestep> steps = new ArrayList<>();

        // 1️⃣ Initial tokens
        List<String> tokens = new ArrayList<>();
        for (String word : sentence.split(" ")) {
            for (char c : word.toCharArray()) {
                tokens.add(String.valueOf(c));
            }
            tokens.add("</w>");
        }
        steps.add(createStep(0, "initial", new ArrayList<>(tokens), null));

        for (int i = 0; i < merges; i++) {
            // 2️⃣ Calculate pair frequencies
            Map<String, Integer> pairFreq = calculatePairFrequencies(tokens);

            // 2a️⃣ Filter out pairs that occur only once
            pairFreq.entrySet().removeIf(entry -> entry.getValue() < 2);

            // 3️⃣ Stop if no pair repeats
            if (pairFreq.isEmpty()) break;

            // 4️⃣ Find most frequent pair
            String maxPair = Collections.max(pairFreq.entrySet(), Map.Entry.comparingByValue()).getKey();
            String[] split = maxPair.split(" ");

            // 5️⃣ Gather step: highlight pair before merging
            steps.add(createStep(steps.size(), "gather", new ArrayList<>(tokens), Arrays.asList(split)));

            // 6️⃣ Merge step
            tokens = mergePair(tokens, split[0], split[1]);
            steps.add(createStep(steps.size(), "merge", new ArrayList<>(tokens), Arrays.asList(split)));
        }

        return new bperesponse(steps);
    }

    private Map<String, Integer> calculatePairFrequencies(List<String> tokens) {
        Map<String, Integer> freq = new HashMap<>();
        for (int i = 0; i < tokens.size() - 1; i++) {
            String pair = tokens.get(i) + " " + tokens.get(i + 1);
            freq.put(pair, freq.getOrDefault(pair, 0) + 1);
        }
        return freq;
    }

    private List<String> mergePair(List<String> tokens, String first, String second) {
        List<String> newTokens = new ArrayList<>();
        int i = 0;
        while (i < tokens.size()) {
            if (i < tokens.size() - 1 && tokens.get(i).equals(first) && tokens.get(i + 1).equals(second)) {
                newTokens.add(first + second);
                i += 2;
            } else {
                newTokens.add(tokens.get(i));
                i++;
            }
        }
        return newTokens;
    }

    private bpestep createStep(int stepNum, String type, List<String> tokens, List<String> merge) {
        bpestep step = new bpestep();
        step.setStep(stepNum);
        step.setType(type);
        step.setTokens(tokens);
        step.setMerge(merge);
        step.setFrequencies(calculateFrequencies(tokens));
        return step;
    }

    private Map<String, Integer> calculateFrequencies(List<String> tokens) {
        Map<String, Integer> freq = new LinkedHashMap<>();
        for (String token : tokens) {
            freq.put(token, freq.getOrDefault(token, 0) + 1);
        }
        return freq;
    }
}
