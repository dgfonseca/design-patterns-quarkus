package com.design.patterns.service;

import com.design.patterns.service.interfaces.AbstractFactory;
import com.design.patterns.service.interfaces.CreditCardPayment;
import com.design.patterns.service.interfaces.DebitCardPayment;
import com.design.patterns.service.interfaces.PayPalPayment;
import com.design.patterns.service.shopify.ShopifyCreditCardPayment;
import com.design.patterns.service.shopify.ShopifyDebitCardPayment;
import com.design.patterns.service.shopify.ShopifyPayPalPayment;

public class ShopifyFactory implements AbstractFactory{

    @Override
    public CreditCardPayment creditCardPayment() {
        return new ShopifyCreditCardPayment();
    }

    @Override
    public DebitCardPayment debitCardPayment() {
        return new ShopifyDebitCardPayment();
    }

    @Override
    public PayPalPayment payPalPayment() {
        return new ShopifyPayPalPayment();
    }
    
}
