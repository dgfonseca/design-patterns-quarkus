package com.design.patterns.service.interfaces;

import com.design.patterns.entities.User;

public interface PayPalPayment {
    void pay(double amount, User user);
}
