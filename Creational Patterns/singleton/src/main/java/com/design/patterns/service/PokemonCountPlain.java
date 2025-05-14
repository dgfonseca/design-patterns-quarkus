package com.design.patterns.service;

public class PokemonCountPlain {

    private static final PokemonCountPlain INSTANCE = new PokemonCountPlain();

    private int requestCount;

    private PokemonCountPlain() {}

    public static PokemonCountPlain getInstance() {
        return INSTANCE;
    }

    public synchronized int increment() {
        return ++requestCount;
    }

    public int getRequestCount() {
        return requestCount;
    }
    
}
