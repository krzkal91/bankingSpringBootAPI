package wsbSpringBootAPI.wsbSpringBootAPI.controller;

import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;

public class Welcome {

    private String status;

    private String java;

    private String date;

    private String author;

    private String readme;

    public Welcome() {
        this.status = "RUNNING";
        this.java = "v11";
        this.date = new Date().toString();
        this.author = "krzysztof.kalinowski@icloud.com";
        try {
            File file = ResourceUtils.getFile("classpath:static/Readme.txt");
            this.readme = new String(Files.readAllBytes(file.toPath()));
        } catch (IOException ioException) {
            ioException.printStackTrace();
            this.readme = "# BankAPI project\n" +
                    "## Author: krzysztof.kalinowski@icloud.com\n" +
                    "## Grupa 2: Java Spring Boot API\n" +
                    "\n" +
                    "### DESCRIPTION\n" +
                    "Spring Boot based REST API application.\n" +
                    "One service, two repositories, two persistence objects\n" +
                    "ORM used in project is Hibernate\n" +
                    "Persistence DB is MongoDB\n" +
                    "Objects persisted in one database, in two separate collections.\n" +
                    "\n" +
                    "Relational strategy between entities is MANYtoMANY - Several Customers can be assigned to an account, several Accounts\n" +
                    "may be assigned to a single customer.\n" +
                    "\n" +
                    "Only the Customer object is keeping reference of the relations - relations are being cascaded\n" +
                    "even though there is no explicit cascading for MongoDB\n" +
                    "\n" +
                    "\n" +
                    "### FUNCTIONALITY\n" +
                    "Project is exposing a simple bank REST API for customers and accounts management.\n" +
                    "It reponds to following operations:\n" +
                    "[1]: GET /customers - responding with all customers with assigned accounts - full info\n" +
                    "[2]: GET /customers/{id} - responding with a chosen customer if exists, if not 404 not found\n" +
                    "[3]: POST /customers - creating a new employee with an auto-assigned ID if not exists (email as a secondary ID)\n" +
                    "[4]: PUT /customers/{id} - updating customers properties or the assigned accounts\n" +
                    "[5]: DELETE /customers/{id} - deleting a chosen customer if exists\n" +
                    "[6]: GET /customers/accounts - responding with a full list of all accounts\n" +
                    "[7]: GET /customers/accounts{id} - responding with a single account if exists\n" +
                    "[8]: POST /customers/accounts - creating a new account with an auto assigned ID (and accountNumber as a secondary)\n" +
                    "[9]: PUT /customers/accounts/{id} - updating the chosen account\n" +
                    "[10]: DELETE /customers/accounts/{id} - deleting account\n" +
                    "[11]: PUT /customers/accounts/transferfunds - transferring funds from one account to another IF such exists, if sufficient funds, etc.\n" +
                    "[12]: GET /customers/searchByEmail - searching a customer by email\n" +
                    "[13]: GET /customers/accounts/{id}/fullinfo - displaying FULL info about the given account INCLUDING the information about the customers that own the account\n";
        }
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getJava() {
        return java;
    }

    public void setJava(String java) {
        this.java = java;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getReadme() {
        return readme;
    }

    public void setReadme(String readme) {
        this.readme = readme;
    }
}
