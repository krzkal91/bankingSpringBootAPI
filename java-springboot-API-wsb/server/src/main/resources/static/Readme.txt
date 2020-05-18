# BankAPI project
## Author: krzysztof.kalinowski@icloud.com
## Grupa 2: Java Spring Boot API

### DESCRIPTION
Spring Boot based REST API application.
One service, two repositories, two persistence objects
ORM used in project is Hibernate
Persistence DB is MongoDB
Objects persisted in one database, in two separate collections.

Relational strategy between entities is MANYtoMANY - Several Customers can be assigned to an account, several Accounts
may be assigned to a single customer.

Only the Customer object is keeping reference of the relations - relations are being cascaded
even though there is no explicit cascading for MongoDB


### FUNCTIONALITY
Project is exposing a simple bank REST API for customers and accounts management.
It reponds to following operations:
[1]: GET /customers - responding with all customers with assigned accounts - full info
[2]: GET /customers/{id} - responding with a chosen customer if exists, if not 404 not found
[3]: POST /customers - creating a new employee with an auto-assigned ID if not exists (email as a secondary ID)
[4]: PUT /customers/{id} - updating customers properties or/and the assigned accounts
[5]: DELETE /customers/{id} - deleting a chosen customer if exists
[6]: GET /customers/accounts - responding with a full list of all accounts
[7]: GET /customers/accounts{id} - responding with a single account if exists
[8]: POST /customers/accounts - creating a new account with an auto assigned ID (and accountNumber as a secondary)
[9]: PUT /customers/accounts/{id} - updating the chosen account
[10]: DELETE /customers/accounts/{id} - deleting account
[11]: PUT /customers/accounts/transferFunds - transferring funds from one account to another IF such exists, if sufficient funds, etc.
[12]: GET /customers/searchByEmail - searching a customer by email
[13]: GET /customers/accounts/{id}/fullinfo - displaying FULL info about the given account INCLUDING the information about the customers that own the account
