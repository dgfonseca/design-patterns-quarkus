package com.design.patterns.service;

import com.design.patterns.entities.User.PaymentType;

import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
@RegisterForReflection
public class PaymentFactory {

    public Payment pay(PaymentType type){
        if(type==null){
            throw new IllegalArgumentException("No Payment");
        }

        switch (type) {
            case PAYPAL:
                return new PayPalPayment();
            case DEBIT:
                return new DebitCardPayment();
            case CREDIT:
                return new CreditCardPayment();
            default:
            throw new IllegalArgumentException("Unknown payment method: " + type);
        }

        
    }
    
}
