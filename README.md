# BankAPI project
## Author: krzysztof.kalinowski@icloud.com
## Java Spring Boot API

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
<p>
  Project is exposing a simple bank REST API for customers and accounts management.<br><br>
It reponds to following operations:<br><br>
  <ol>
    
<li> GET /customers - responding with all customers with assigned accounts - full info </li>
<li> GET /customers/{id} - responding with a chosen customer if exists, if not 404 not found </li>
<li> POST /customers - creating a new customer with an auto-assigned ID if not exists (email as a secondary ID) </li>
<li> PUT /customers/{id} - updating customers properties or the assigned accounts </li>
<li> DELETE /customers/{id} - deleting a chosen customer if exists </li>
<li> GET /customers/accounts - responding with a full list of all accounts </li>
<li> GET /customers/accounts{id} - responding with a single account if exists </li>
<li> POST /customers/accounts - creating a new account with an auto assigned ID (and accountNumber as a secondary) </li>
<li> PUT /customers/accounts/{id} - updating the chosen account </li>
<li> DELETE /customers/accounts/{id} - deleting account </li>
<li> PUT /customers/accounts/transferFunds - transferring funds from one account to another IF such exists, if sufficient funds, etc. </li>
<li> GET /customers/searchByEmail - searching a customer by email </li>
<li> GET /customers/accounts/{id}/fullinfo - displaying FULL info about the given account INCLUDING the information about the customers that own the account </li>
  </ol>
  </p>
  
### OBJECT EXAMPLES

<p>
	CUSTOMER
	
  {
	"id": "ksdkjdh2837273678",
        "firstname": "Mariano",
        "lastname": "Italiano",
        "email": "mariano.italiano@1589225973170.it",
	"accountSet": []
}

	ACCOUNT
	
{
	"id": "ksdkjdh2837273678",
        "accountNumber": 88100213,
        "accountType": "SAVINGS",
        "balance": 1500100
}

	TRANSFERFUNDS
	
{
	"fromAccount": 88100213,
	"toAccount": 10098764,
	"amount": 999
}

  </p>
