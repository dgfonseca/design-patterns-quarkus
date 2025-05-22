package com.design.patterns.entities;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.OneToOne;

@RegisterForReflection
@ApplicationScoped
@JsonIgnoreProperties(ignoreUnknown = true)
public class Persona{

    public String nombre;

    public String apellido;

    public int dni;

    public int estatura;

    public int peso;

    public String fechaNacimiento;

    public String ciudadNacimiento;

    public String genero;

    @OneToOne
    public Carro carro;
    
}
