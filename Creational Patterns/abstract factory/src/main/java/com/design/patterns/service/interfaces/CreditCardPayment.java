package com.design.patterns.service.interfaces;

import com.design.patterns.entities.User;

public interface CreditCardPayment {

    void pay(double amount, User user);
    
}
