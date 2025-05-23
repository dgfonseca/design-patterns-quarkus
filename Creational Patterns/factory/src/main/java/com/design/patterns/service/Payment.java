package com.design.patterns.service;

import com.design.patterns.entities.User;

public interface Payment {
    void pay(double amount, User user);
}
