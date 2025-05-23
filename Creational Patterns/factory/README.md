# Factory Method Design Pattern in Quarkus and Plain Java

## 🧠 What is the Factory Method Pattern?

The **Factory Method** is a **creational design pattern** that provides an interface for creating objects in a superclass, but allows subclasses to alter the type of objects that will be created.

Instead of instantiating classes directly, you delegate the creation process to a method that returns an instance of the appropriate class, usually based on input or configuration.

---

## 🎯 Purpose

- **Decouples object creation from its usage.**
- Promotes **flexibility and extensibility** by allowing the introduction of new types with minimal changes to existing code.
- Follows the **Open/Closed Principle** (open for extension, closed for modification).

---

## 🛠 Use Case: Payment Processing System

In this example, we simulate a payment system where a customer can pay using:
- **Credit Card**
- **PayPal**
- **Debit Card**

The system selects the appropriate payment method at runtime, using a factory class.

---

## 🧩 Structure

``` 
+----------------+          +---------------------+
|   Payment      |<---------| CreditCardPayment   |
| (interface)    |<---------| PayPalPayment       |
|                |<---------| DebitCardPayment    |
+----------------+          +---------------------+

        ▲
        |
        |
+------------------+        --> Factory Method
| PaymentFactory   |---------------------------->
+------------------+
| createPayment()  |
+------------------+

        ▲
        |
+-------------------------------+
|   SingletonPaymentResource    |
+-------------------------------+
```


## 🧪 Implementation

### 1. Payment Interface

```java
public interface Payment {
    void pay(double amount, User user);
}
```

### 2. Concrete Implementations

```java

public class CreditCardPayment implements Payment{

    @Override
    public void pay(double amount, User user) {
        ////// Credit Card Processing tax of 2% //////
        amount = amount*0.98;
        user.paymentStatus="Paid "+amount+" With Credit Card";
        user.persist();
    }
    
}

public class DebitCardPayment implements Payment{

    @Override
    public void pay(double amount, User user) {

        ////// Debit Card Processing tax of 0% //////
        user.paymentStatus="Paid "+amount+" With Debit Card";
        user.persist();
        
    }
    
}

public class PayPalPayment implements Payment{

    @Override
    public void pay(double amount, User user) {

        if(amount>1000){
            amount=amount*0.9;
        }
        user.paymentStatus="Paid "+ amount+" with PayPal";
        user.persist();
    }
    
}

```

### 3. Factory Class

```java

public class PaymentFactory {

    public Payment pay(PaymentType type){
        if(type==null){
            throw new IllegalArgumentException("No Payment");
        }

        switch (type) {
            case PAYPAL:
                return new PayPalPayment();
            case DEBIT:
                return new DebitCardPayment();
            case CREDIT:
                return new CreditCardPayment();
            default:
            throw new IllegalArgumentException("Unknown payment method: " + type);
        }

        
    }
    
}

```

### 4. Client Implementation 

``` java

    @Inject
    PaymentFactory paymentFactory;

    @PATCH
    @Path("/pay/{passport}")
    @Transactional
    public Response pay(@PathParam("passport") int passport, @QueryParam("amount") double amount){

        User user = User.findById(passport);

        if(user==null){
            return Response.noContent().build();
        }

        Payment payment = paymentFactory.pay(user.paymentType);
        payment.pay(amount, user);

        return Response.ok(user).build();
    }

```

## ✅ Benefits

- Easily extendable for new payment types.
- Reduces coupling between the service and concrete classes.
- Promotes good design principles (SOLID).

## 🧪 How to Test the Application

You can interact with the application using the following `curl` commands:

### 1️⃣ Create a User

Send a POST request to create a new user:

```bash
curl --location 'http://localhost:8080/person/' \
--header 'Content-Type: application/json' \
--data '{
    "passport":123,
    "name":"test",
    "last_name":"test",
    "payment_type":"DEBIT"
}'
```

### 2️⃣ Retrieve All Users

Send a GET request to retrieve all users in the system:

```bash
curl --location 'http://localhost:8080/person/'
```

### 3️⃣ Make a Payment

Send a PATCH request to trigger a payment for a specific user (by passport number):

```bash
curl --location --request PATCH 'http://localhost:8080/person/pay/123?amount=200'
```


