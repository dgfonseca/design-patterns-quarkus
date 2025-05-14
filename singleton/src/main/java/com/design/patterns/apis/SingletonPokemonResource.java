package com.design.patterns.apis;

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import com.design.patterns.clients.PokemonClient;
import com.design.patterns.entities.Pokemon;
import com.design.patterns.entities.PokemonResponse;
import com.design.patterns.service.PokemonCount;
import com.design.patterns.service.PokemonCountPlain;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@ApplicationScoped
@Path("/pokemon")
public class SingletonPokemonResource {


    @Inject
    @RestClient
    PokemonClient client;

    @Inject
    ObjectMapper objectMapper;

    @Inject
    PokemonCount pokemonCount;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getPokemons() throws JsonProcessingException {
        pokemonCount.increment();
        PokemonCountPlain.getInstance().increment();
        return objectMapper.writeValueAsString(Pokemon.findAll().list());
    }


    @Transactional
    @Path("/load")
    @GET()
    @Produces(MediaType.APPLICATION_JSON)
    public String loadPokemon() throws JsonProcessingException {
        pokemonCount.increment();
        PokemonCountPlain.getInstance().increment();
        PokemonResponse response = client.getPokemons();
        List<Pokemon> pokemons=response.getResults().stream().map(obj->{
            Pokemon pokemon=client.getPokemon(obj.getName());
            Pokemon.persistIfNotExists(pokemon);
            return pokemon;
        }).collect(Collectors.toList());

        return objectMapper.writeValueAsString(pokemons);
    }

    @Path("/{name}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getPokemon(@PathParam("name") String name) throws JsonProcessingException{
        PokemonCountPlain.getInstance().increment();
        pokemonCount.increment();
        return objectMapper.writeValueAsString(client.getPokemon(name));
    }

    @GET
    @Path("/count")
    @Produces(MediaType.TEXT_PLAIN)
    public String getRequestCount(){
        return String.valueOf("Quarkus singleton: "+pokemonCount.getRequestCount()+" | Plain Java Singleton: "+PokemonCountPlain.getInstance().getRequestCount());
    }
}
