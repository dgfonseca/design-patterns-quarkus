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
//TODO Configure Path
public class SingletonPokemonResource {


    @Inject
    @RestClient
    PokemonClient clientPokemon;

    //TODO Inject Item Client

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getPokemons() throws JsonProcessingException {
        Pokemon.findAll().list();
    }


    @Transactional
    @Path("/load")
    @GET()
    @Produces(MediaType.APPLICATION_JSON)
    public List<Pokemon> loadPokemon() throws JsonProcessingException {
        PokemonResponse response = clientPokemon.getPokemons();
        List<Pokemon> pokemons=response.getResults().stream().map(obj->{
            Pokemon pokemon=clientPokemon.getPokemon(obj.getName());
            Pokemon.persistIfNotExists(pokemon);
            return pokemon;
        }).collect(Collectors.toList());

        return pokemons;
    }

    @Path("/{name}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Pokemon getPokemon(@PathParam("name") String name) throws JsonProcessingException{
        PokemonCountPlain.getInstance().increment();
        return clientPokemon.getPokemon(name);
    }


    // TODO Pokemon height up and down as FormParams
    public Pokemon getPokemonByHeigh() throws JsonProcessingException{
        return null;
    }
    // TODO Pokemon default as path
    public String getCountPokemonDefault(boolean isDefault) throws JsonProcessingException{

        return null;
    }

    // TODO Pokemon Name as path and default as query parameter
    public Pokemon updateDefault(String pokemon, Boolean isDefault){
        return null;
    }

    // TODO Get Items
    public void getItems(){

    }
}
