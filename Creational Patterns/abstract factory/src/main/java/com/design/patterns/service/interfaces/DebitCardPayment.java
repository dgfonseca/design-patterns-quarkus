package com.design.patterns.service.interfaces;

import com.design.patterns.entities.User;

public interface DebitCardPayment {
    void pay(double amount, User user);
}
