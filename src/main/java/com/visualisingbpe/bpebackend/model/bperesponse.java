package com.visualisingbpe.bpebackend.model;

import java.util.List;

public class bperesponse {
    private List<bpestep> steps;

    public bperesponse(List<bpestep> steps) {
        this.steps = steps;
    }

    public List<bpestep> getSteps() {
        return steps;
    }

    public void setSteps(List<bpestep> steps) {
        this.steps = steps;
    }
}
