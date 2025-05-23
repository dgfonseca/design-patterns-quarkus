package com.design.patterns.service.stripe;

import com.design.patterns.entities.User;
import com.design.patterns.service.interfaces.CreditCardPayment;

public class StripeCreditCardPayment implements CreditCardPayment{

    @Override
    public void pay(double amount, User user) {

        amount=amount*0.95;
        try {
            notifyTransactionToStripe(user, amount);
            user.paymentStatus="Paid "+amount+" in Stripe using Credit Card";
        } catch (InterruptedException e) {
            e.printStackTrace();
            user.paymentStatus="Transaction Declined";
        }finally{
            user.persist();
        }
        
    }

    public void notifyTransactionToStripe(User user, double amount) throws InterruptedException{
        System.out.println("User "+user.name +" "+ user.lastName+" is paying "+amount+" $ using Credit Card. Bank Notified");
        Thread.sleep(2000);
        System.out.println("Transaction approved by the bank.");
    }
    
}
