package com.design.patterns.service.shopify;

import com.design.patterns.entities.User;
import com.design.patterns.service.interfaces.PayPalPayment;

public class ShopifyPayPalPayment implements PayPalPayment{

    @Override
    public void pay(double amount, User user) {

        if(amount>2000){
            user.paymentStatus="Invalid Amount, transaction declined";
            user.persist();
            return;
        }
        try {
            notifyTransactionToStripe(user, amount);
            user.paymentStatus="Paid "+amount+" in Shopify using PayPal ";
        } catch (InterruptedException e) {
            e.printStackTrace();
            user.paymentStatus="Transaction Declined";
        }finally{
            user.persist();
        }

        
    }

    public void notifyTransactionToStripe(User user, double amount) throws InterruptedException{
        System.out.println("User "+user.name +" "+ user.lastName+" is paying "+amount+" $ using PayPal");
        Thread.sleep(2000);
        System.out.println("Transaction approved by the PayPal");
    }
    
}
