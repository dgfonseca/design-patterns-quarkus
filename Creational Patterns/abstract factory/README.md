# Abstract Factory Design Pattern in Quarkus and Plain Java

## 🧠 What is the Abstract Factory Pattern?

The **Abstract Factory** is a creational design pattern that lets you produce families of related objects **without specifying their concrete classes**. It encapsulates a group of individual factories with a common interface.

---

### 🔍 Key Concepts

| Component          | Description                                                                 |
|-------------------|-----------------------------------------------------------------------------|
| **AbstractFactory** | Declares creation methods for a family of related products.                |
| **ConcreteFactory** | Implements the creation methods and returns specific product variants.     |
| **AbstractProduct** | Declares interfaces for a set of products (e.g., `CreditCardPayment`).     |
| **ConcreteProduct** | Implements the abstract product interface (e.g., `StripeCreditCardPayment`). |
| **Client**          | Uses only abstract interfaces and factories. Doesn't depend on concrete classes. |

---

### ✅ Benefits

#### 🔄 Product Family Consistency

Ensures that all created objects belong to a compatible family. For example, all payment methods created by a factory will conform to the Stripe or Shopify context without mixing implementations.

#### 🧼 Interface-Based Design

Clients interact only with interfaces, not implementations. This results in highly decoupled code that is easier to refactor, maintain, and extend.

#### 🔁 Interchangeable Implementations

You can switch between entire sets of implementations (e.g., Stripe to Shopify) with a single change to the factory being used. This improves maintainability and supports deployment across different platforms or regions.

#### 🧪 Enhanced Testability

The pattern facilitates mocking and stubbing by allowing test-specific factories to be injected. This supports better isolation in unit tests and more predictable behavior in integration testing.

#### 🛠 Extensibility

Adding support for new providers or new payment methods requires implementing new concrete factories and products. Existing client code remains unchanged, following the Open/Closed Principle.

#### 🧱 Separation of Concerns

The creation logic is centralized and separated from the business logic. This makes the architecture cleaner and improves the Single Responsibility Principle compliance. 

### ⚠️ When to Use

Use the Abstract Factory pattern when:

- You need to create objects that belong to families with related behavior.
- You want to enforce consistency between products of the same family.
- You need to switch between product families (gateways) dynamically at runtime.
- You want to follow **dependency injection** and **clean architecture** practices.

---

## ⚠️ Limitations & Cons

While the Abstract Factory pattern offers flexibility and scalability, it also comes with certain drawbacks:

### 🚧 Increased Complexity

- Introduces **more classes and interfaces** than simpler approaches.
- Can make the code harder to navigate, especially for small or simple applications.

### 🚫 Harder to Add New Product Types

- Adding a **new payment type** (e.g., "Crypto") requires modifying **every concrete factory** to implement this new method.  
  > This breaks the **Open/Closed Principle** for factories.

### 🧪 More Boilerplate Code

- For each new gateway or product type, you must implement several new classes and interfaces.
- Can result in unnecessary abstraction if not all gateways support the same set of features.

### 🧩 Overhead for Small-Scale Projects

- If your application only ever uses one payment provider or only one payment method, this pattern might be **overkill**.
- Simpler alternatives like the **Factory Method pattern** or even **strategy-based DI (Dependency Injection)** may suffice.

### 🧠 Requires Careful Design

- You must clearly define the boundaries and responsibilities of each "family" of products (e.g., what constitutes a complete payment gateway).
- Poorly planned abstract factories can become too rigid or overly generic.



### 🏦 Pattern Applied to Payments

                   +---------------------+
                   |  <<interface>>      |
                   |  AbstractFactory    |
                   +---------------------+
                   | +debitCardPayment() |
                   | +creditCardPayment()|
                   | +payPalPayment()    |
                   +---------------------+
                      /              \
                     /                \
       +---------------------+   +----------------------+
       | StripePaymentFactory|   | ShopifyPaymentFactory|
       +---------------------+   +----------------------+
         |       |       |            |       |       |
         v       v       v            v       v       v
       +-----+ +-----+ +-----+      +-----+ +-----+ +-----+
       |SCCP | |SDCP | |SPPP |      |SCCP | |SDCP | |SPPP |
       +-----+ +-----+ +-----+      +-----+ +-----+ +-----+


In this payment system:

- **AbstractFactory**: `AbstractFactory.java`  
  > Knows how to create a family of payment objects (credit, debit, PayPal).

- **ConcreteFactory**: `StripeFactory.java`, `ShopifyFactory.java`  
  > Each factory returns a set of gateway-specific payment methods.

- **AbstractProduct**: `CreditCardPayment.java`, `DebitCardPayment.java`, `PayPalPayment.java`  
  > Common interfaces for different payment methods.

- **ConcreteProduct**:  
  > Gateway-specific implementations like:
  - `StripeCreditCardPayment.java`
  - `StripeDebitCardPayment.java`
  - `StripePayPalPayment.java`
  - `ShopifyCreditCardPayment.java`
  - `ShopifyDebitCardPayment.java`
  - `ShopifyPayPalPayment.java`

- **Client**: `SingletonPaymentResource.java`  
  > Uses `AbstractFactory.java` to process payments without knowing the actual implementation details.

---

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
curl --location --request PATCH 'http://localhost:8080/person/pay/123?amount=200&gateway=STRIPE'
```


