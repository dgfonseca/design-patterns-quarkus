package com.design.patterns.test;

import static org.mockito.Mockito.*;

import java.util.List;

import jakarta.inject.Inject;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;

import com.design.patterns.apis.SingletonPokemonResource;
import com.design.patterns.clients.PokemonClient;
import com.design.patterns.entities.Pokemon;
import com.design.patterns.entities.PokemonResponse;
import com.design.patterns.entities.PokemonSummary;
import com.design.patterns.service.PokemonCount;
import com.fasterxml.jackson.databind.ObjectMapper;


import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class SingletonPokemonResourceTest {

    @Inject
    SingletonPokemonResource resource;

    @InjectMock
    @RestClient
    PokemonClient client;

    @Inject
    ObjectMapper objectMapper;

    @InjectMock
    PokemonCount pokemonCount;

    @Order(1)
    @Test
    void testLoadPokemons() throws Exception{
        PokemonResponse pokemonResponse = new PokemonResponse();
        PokemonSummary pokemonSummary = new PokemonSummary();
        pokemonSummary.setName("bulbasaur");
        pokemonResponse.setResults(List.of(pokemonSummary));
        when(client.getPokemons()).thenReturn(pokemonResponse);

        Pokemon pokemon = new Pokemon();
        pokemon.baseExperience=10;
        pokemon.height=19;
        pokemon.id=1;
        pokemon.isDefault=true;
        pokemon.name="bulbasaur";
        pokemon.weight=10;

        when(client.getPokemon("bulbasaur")).thenReturn(pokemon);

        String pokemons = resource.loadPokemon();
        assertTrue(pokemons.contains("bulbasaur"));
        verify(pokemonCount).increment();

    }

    @Order(2)
    @Test
    void testGetPokemons() throws Exception {

        PokemonResponse pokemonResponse = new PokemonResponse();
        PokemonSummary pokemonSummary = new PokemonSummary();
        pokemonSummary.setName("bulbasaur");
        pokemonResponse.setResults(List.of(pokemonSummary));
        when(client.getPokemons()).thenReturn(pokemonResponse);       
        String result = resource.getPokemons();

        assertNotNull(result);
        assertTrue(result.contains("bulbasaur"));
        verify(pokemonCount).increment();
    }

    @Order(3)
    @Test
    void testGetPokemon() throws Exception {
        Pokemon mockPokemon = new Pokemon();
        mockPokemon.name="pikachu";

        when(client.getPokemon("pikachu")).thenReturn(mockPokemon);

        String result = resource.getPokemon("pikachu");

        assertTrue(result.contains("pikachu"));
        verify(pokemonCount).increment();
    }

}
