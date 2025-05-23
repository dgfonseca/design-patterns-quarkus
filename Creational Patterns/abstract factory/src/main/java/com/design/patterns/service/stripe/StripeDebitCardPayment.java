package com.design.patterns.service.stripe;

import com.design.patterns.entities.User;
import com.design.patterns.service.interfaces.DebitCardPayment;

public class StripeDebitCardPayment implements DebitCardPayment{

    @Override
    public void pay(double amount, User user) {

        try {
            notifyTransactionToStripe(user, amount);
            user.paymentStatus="Paid "+amount+" in Stripe using Debit Card";
        } catch (InterruptedException e) {
            e.printStackTrace();
            user.paymentStatus="Transaction Declined";
        }finally{
            user.persist();
        }
        
    }

    public void notifyTransactionToStripe(User user, double amount) throws InterruptedException{
        System.out.println("User "+user.name +" "+ user.lastName+" is paying "+amount+" $ using Debit Card. Bank Notified");
        Thread.sleep(2000);
        System.out.println("Transaction approved by the bank.");
    }
    
}
