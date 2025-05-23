package com.design.patterns.service.shopify;

import com.design.patterns.entities.User;
import com.design.patterns.service.interfaces.CreditCardPayment;

public class ShopifyCreditCardPayment implements CreditCardPayment{

    @Override
    public void pay(double amount, User user) {

        amount=amount*0.98;
        try {
            notifyTransactionToShopify(user, amount);
            user.paymentStatus="Paid "+amount+" in Shopify using Credit Card";
        } catch (InterruptedException e) {
            e.printStackTrace();
            user.paymentStatus="Transaction Declined";
        }finally{
            user.persist();
        }
        
    }

    public void notifyTransactionToShopify(User user, double amount) throws InterruptedException{
        System.out.println("User "+user.name +" "+ user.lastName+" is paying "+amount+" $ using Credit Card. Bank Notified");
        Thread.sleep(2000);
        System.out.println("Transaction approved by the bank.");
    }
    
}
