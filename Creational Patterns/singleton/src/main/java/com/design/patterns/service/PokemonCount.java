package com.design.patterns.service;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PokemonCount {
    
    private int requestCount;

    public int increment() {
        return ++requestCount;
    }

    public int getRequestCount() {
        return requestCount;
    }


}
