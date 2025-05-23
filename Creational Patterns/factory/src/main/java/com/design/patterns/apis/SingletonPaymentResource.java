package com.design.patterns.apis;

import java.net.URI;
import java.util.List;

import com.design.patterns.entities.User;
import com.design.patterns.service.Payment;
import com.design.patterns.service.PaymentFactory;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
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
@Path("/person")
public class SingletonPaymentResource {


    @Inject
    PaymentFactory paymentFactory;

    @Transactional
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(User user){

       boolean result =  User.persistIfNotExists(user);

       if(result){
           return Response.created(URI.create("/person/"+user.passport)).entity(user).build();
       }
       return Response.serverError().build();

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers(){
        List<User> users = User.listAll();
        return Response.ok(users).build();
    }



    @PATCH
    @Path("/pay/{passport}")
    @Transactional
    public Response pay(@PathParam("passport") int passport, @QueryParam("amount") double amount){

        User user = User.findById(passport);

        if(user==null){
            return Response.noContent().build();
        }

        Payment payment = paymentFactory.pay(user.paymentType);
        payment.pay(amount, user);

        return Response.ok(user).build();
    }

    
}
