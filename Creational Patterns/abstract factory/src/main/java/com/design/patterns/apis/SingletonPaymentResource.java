package com.design.patterns.apis;

import java.net.URI;
import java.util.List;

import com.design.patterns.entities.User;
import com.design.patterns.entities.User.PaymentGateway;
import com.design.patterns.entities.User.PaymentType;
import com.design.patterns.service.ShopifyFactory;
import com.design.patterns.service.StripeFactory;
import com.design.patterns.service.interfaces.AbstractFactory;
import com.design.patterns.service.interfaces.CreditCardPayment;
import com.design.patterns.service.interfaces.DebitCardPayment;
import com.design.patterns.service.interfaces.PayPalPayment;

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

    
    AbstractFactory abstractFactory;

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
    public Response pay(@PathParam("passport") int passport, @QueryParam("amount") double amount,@QueryParam("gateway") PaymentGateway gateway){

        User user = User.findById(passport);

        if(user==null){
            return Response.noContent().build();
        }

        

        switch (gateway) {
            case SHOPIFY:
                abstractFactory = new ShopifyFactory();
                break;
            case STRIPE:
                abstractFactory = new StripeFactory();
                break;
            default:
                throw new IllegalArgumentException("Invalid Gateway");
        }

        if(user.paymentType.equals(PaymentType.CREDIT)){
            CreditCardPayment payment = abstractFactory.creditCardPayment();
            payment.pay(amount, user);
        }
        if(user.paymentType.equals(PaymentType.DEBIT))
        {
            DebitCardPayment payment = abstractFactory.debitCardPayment();
            payment.pay(amount, user);
        }
        if(user.paymentType.equals(PaymentType.PAYPAL)){
            PayPalPayment payment = abstractFactory.payPalPayment();
            payment.pay(amount, user);
        }

        return Response.ok(user).build();
    }

    
}
