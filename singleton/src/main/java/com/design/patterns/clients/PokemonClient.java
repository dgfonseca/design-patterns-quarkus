package com.design.patterns.clients;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import com.design.patterns.entities.Pokemon;
import com.design.patterns.entities.PokemonResponse;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@ApplicationScoped
@Path("/api/v2/pokemon")
@RegisterRestClient(baseUri = "https://pokeapi.co")
public interface PokemonClient {

    @GET
    @Produces(MediaType.APPLICATION_JSON)

    public PokemonResponse getPokemons();


    @Path("/{name}")
    @GET
    public Pokemon getPokemon(@PathParam("name") String name);
    
}
