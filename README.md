# Transation Application

# Disclaimer
Majority codes of this application belongs to Professor Brian Parra from San Francisco State University.
This application was parts of class syllabus for CSC 413 (Software Development).
I was given starting skeleton code and I was asked to complete code in order to run the test cases. 

# Overview
Transaction Processing API

A HTTP-based transaction processing system that allowed users to make deposits into their accounts.
This application uses a custom-built HTTP request handler and store user data and transactions in memory
using DAO (Data Access Object) pattern. And DTO (Data Transfer Object) defines data structure.

# Features
- Create Deposit
- User & Transaction Management
- Simple HTTP API
- Data is stored in memory using Java `HashMap`
- The Server class processes HTTP request and returns the appropriate response
- Responses return a JSON object as the body of an http response
