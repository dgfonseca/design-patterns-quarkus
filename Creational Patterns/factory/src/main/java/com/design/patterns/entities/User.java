package com.design.patterns.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@RegisterForReflection
@ApplicationScoped
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "user_table")
public class User extends PanacheEntityBase{

    @Id
    public Integer passport;

    public String name;

    @JsonProperty("last_name")
    public String lastName;

    @JsonProperty("payment_type")
    @Enumerated(EnumType.STRING)
    public PaymentType paymentType;

    public String paymentStatus;

    public enum PaymentType{
        PAYPAL,
        DEBIT,
        CREDIT
    }




    public static boolean persistIfNotExists(User user) {
        if (user.passport != null && User.findById(user.passport) == null) {
            user.persist();
            return true;
        }
        return false;
    }


    
}
