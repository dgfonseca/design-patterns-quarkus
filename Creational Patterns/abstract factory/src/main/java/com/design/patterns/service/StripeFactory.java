package com.design.patterns.service;

import com.design.patterns.service.interfaces.AbstractFactory;
import com.design.patterns.service.interfaces.CreditCardPayment;
import com.design.patterns.service.interfaces.DebitCardPayment;
import com.design.patterns.service.interfaces.PayPalPayment;
import com.design.patterns.service.stripe.StripeCreditCardPayment;
import com.design.patterns.service.stripe.StripeDebitCardPayment;
import com.design.patterns.service.stripe.StripePayPalPayment;

public class StripeFactory implements AbstractFactory{

    @Override
    public CreditCardPayment creditCardPayment() {
        return new StripeCreditCardPayment();
    }

    @Override
    public DebitCardPayment debitCardPayment() {
        return new StripeDebitCardPayment();
    }

    @Override
    public PayPalPayment payPalPayment() {
        return new StripePayPalPayment();
    }

    
}
