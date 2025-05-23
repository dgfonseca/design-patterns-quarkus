package com.design.patterns.service;

import com.design.patterns.entities.User;

public class PayPalPayment implements Payment{

    @Override
    public void pay(double amount, User user) {

        if(amount>1000){
            amount=amount*0.9;
        }
        user.paymentStatus="Paid "+ amount+" with PayPal";
        user.persist();
    }
    
}
