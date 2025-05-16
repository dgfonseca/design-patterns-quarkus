package com.design.patterns.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.Id;

@RegisterForReflection
@ApplicationScoped
@JsonIgnoreProperties(ignoreUnknown = true)
public class Pokemon{

    @Id
    public Integer id;

    public String name;

    @JsonProperty("base_experience")
    public Integer baseExperience;

    public Integer height;

    @JsonProperty("is_default")
    public Boolean isDefault;

    public Integer weight;

    public String type;




}
