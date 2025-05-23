package com.design.patterns.service;

import com.design.patterns.entities.User;

public class CreditCardPayment implements Payment{

    @Override
    public void pay(double amount, User user) {
        ////// Credit Card Processing tax of 2% //////
        amount = amount*0.98;
        user.paymentStatus="Paid "+amount+" With Credit Card";
        user.persist();
    }
    
}
