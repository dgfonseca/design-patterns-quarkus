package com.design.patterns.service;

import jakarta.inject.Singleton;

@Singleton
public class PokemonCount {
    
    private int requestCount;

    public int increment() {
        return ++requestCount;
    }

    public int getRequestCount() {
        return requestCount;
    }


}
