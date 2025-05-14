#  Singleton Pattern in Quarkus and Plain Java

## 🔍 What Is a Singleton?

A **Singleton** is a design pattern that ensures a class has only **one instance** and provides a **global access point** to it. This is useful when exactly one object is needed to coordinate actions across the system.

---

## ☕ Singleton in Plain Java

In plain Java, a singleton is typically implemented with:

- A **private static instance**
- A **private constructor**
- A **public static accessor**

### Example

```java
public class PokemonCountPlain {

    private static final PokemonCountPlain INSTANCE = new PokemonCount();

    private int requestCount;

    private PokemonCountPlain() {}

    public static PokemonCountPlain getInstance() {
        return INSTANCE;
    }

    public synchronized int increment() {
        return ++requestCount;
    }

    public int getRequestCount() {
        return requestCount;
    }
}
```
### Usage
```java
PokemonCount counter = PokemonCount.getInstance();
counter.increment();
```

This approach works in any Java environment, but it requires manual lifecycle and concurrency management.

## Singleton with @Singleton in Quarkus

Quarkus supports dependency injection using Jakarta CDI. You can annotate a class with @jakarta.inject.Singleton to indicate that it should be instantiated only once during the application's lifecycle.

```java
import jakarta.inject.Singleton;

@Singleton
public class PokemonCount {

    private int requestCount;

    public int increment() {
        return ++requestCount;
    }

    public int getRequestCount() {
        return requestCount;
    }
}
```
### Usage
```java
@Inject
PokemonCount pokemonCount;

...
..
.

pokemonCount.increment();

```

Using @Singleton with Quarkus allows the framework to manage the lifecycle, thread safety (in most cases), and injection into other components like REST resources or services.

## Test Application


### Load pokemons to database

The following request persists the information of pokemons to the database.
```bash
curl --location 'http://localhost:8080/pokemon/load'
```

The following request consults the information of pokemons from the database.
```bash
curl --location 'http://localhost:8080/pokemon'
```

The following request consults the information of a pokemon given its name.
```bash
curl --location 'http://localhost:8080/pokemon/{name}'
```

The following request consults the information of a how many request were made to the application. It compares both implementations of Singleton.
```bash
curl --location 'http://localhost:8080/pokemon/count'
```


## Running the application in dev mode

You can run your application in dev mode that enables live coding using:

```shell script
./mvnw quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at <http://localhost:8080/q/dev/>.

## Packaging and running the application

The application can be packaged using:

```shell script
./mvnw package
```

It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:

```shell script
./mvnw package -Dquarkus.package.jar.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using:

```shell script
./mvnw package -Dnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using:

```shell script
./mvnw package -Dnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/design-patterns-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult <https://quarkus.io/guides/maven-tooling>.

## Related Guides

- SmallRye OpenAPI ([guide](https://quarkus.io/guides/openapi-swaggerui)): Document your REST APIs with OpenAPI - comes with Swagger UI
- RESTEasy Classic ([guide](https://quarkus.io/guides/resteasy)): REST endpoint framework implementing Jakarta REST and more
- JDBC Driver - PostgreSQL ([guide](https://quarkus.io/guides/datasource)): Connect to the PostgreSQL database via JDBC

## Provided Code

### RESTEasy JAX-RS

Easily start your RESTful Web Services

[Related guide section...](https://quarkus.io/guides/getting-started#the-jax-rs-resources)
