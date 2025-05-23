package com.design.patterns.service.interfaces;

public interface AbstractFactory {
    CreditCardPayment creditCardPayment();
    DebitCardPayment debitCardPayment();
    PayPalPayment payPalPayment();
}
