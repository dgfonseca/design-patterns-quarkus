package com.design.patterns.entities;

import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.enterprise.context.ApplicationScoped;

@RegisterForReflection
@ApplicationScoped
public class Carro{

    public String marca;

    public String modelo;

    public String placa;
    
}
