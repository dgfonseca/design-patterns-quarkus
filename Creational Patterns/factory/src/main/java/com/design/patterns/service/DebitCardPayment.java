package com.design.patterns.service;

import com.design.patterns.entities.User;

public class DebitCardPayment implements Payment{

    @Override
    public void pay(double amount, User user) {

        ////// Debit Card Processing tax of 0% //////
        user.paymentStatus="Paid "+amount+" With Debit Card";
        user.persist();
        
    }
    
}
