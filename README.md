# TDD Currencies-Bank REST Application

### Edu-project made to try REST & TDD.

## OpenAPI:
````
http://localhost:7070/swagger-ui/index.html
````

---

## Endpoints:
### Central Bank:
#### Get today currencies:
````
http://localhost:7070/cb/currencies
````

#### Conversion (amount from -> to)
````
http://localhost:7070/cb/convert?amount=1&from=USD&to=RUB
````

---

### Mock Bank:

#### Get today currencies:
````
http://localhost:7070/mock-bank/currencies
````

#### Conversion (amount from -> to)
````
http://localhost:7070/mock-bank/convert?amount=0&from=usd&to=eur
````

#### Update rate of 2 values in Mock Bank
````
localhost:7070/mock-bank/currencies/update-rate?rate=5&from=USD&to=EUR
````