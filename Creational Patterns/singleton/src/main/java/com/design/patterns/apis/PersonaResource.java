package com.design.patterns.apis;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.print.attribute.standard.Media;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import com.design.patterns.entities.Persona;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class PersonaResource {


    ///
    /// TODO Ejercicio 2.1 Crear un metodo POST que cree una persona en la base de datos y me retorne el mismo ojeto creado.
    /// 
    public Response createPersona(Persona persona){

        return Response.created(URI.create("/persona/"+persona.dni)).entity(persona).build();
    }

    ///
    /// TODO Ejercicio 2.2 Crear un metodo GET que retorne una persona dado su numero de dni. Si no existe deberá responder un mensaje generico que no existe.
    /// 
    public Response getPersona(int dni){
        return Response.noContent().build();
    }

    ///
    /// TODO Ejercicio 2.3 Crear un metodo GET que retorne una persona dado su genero y ciudad de nacimiento.
    /// 
    public Response getPersonasGenero(String genero, String ciudad){
        return Response.ok(personas).build();
    }


    ///
    /// TODO 2.4 Crea un metodo PATCH que modifique el peso y estatura que entran como FormParam de una persona cuyo dni entre como PathParam.
    /// 
    public Response patchPesoAndEstatura(int peso, int estatura,int dni){
        return Response.notModified().build();
    }

    ///
    /// TODO 2.5 Crea un metodo GET el cual busque todas las personas que tengan un carro con una marca en particular
    /// 
    public Response getPersonasMarcaCarro(String marca){

        return Response.ok(persona).build();

    }

    ///
    /// TODO 3.1 Crea un metodo get que utilize el cliente REST que se creó para consultar las ubicaciones del api externo.
    /// 
    public Response getLocation(){
        return Response.ok(location).build;
    }



    
}
