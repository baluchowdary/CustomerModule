# CustomerModule
#CustomerModule SpringBoot and MicroServices project

Post: http://localhost:9095/customer/savecustomer 
{
    "firstName": "Nelluri",
    "lastName": "Usha",
    "mobileNumber": "7666566903",
    "gender": "female",
    "address": "Texas,USA"
}

===============

Get: http://localhost:9095/customer/getAllCustomers 

[
    {
        "customerId": 21,
        "firstName": "kollu",
        "lastName": "Bhardwaja",
        "mobileNumber": "7666566899",
        "gender": "male",
        "address": "Hyd,India"
    },
    {
        "customerId": 22,
        "firstName": "kollu",
        "lastName": "Naresh",
        "mobileNumber": "7666566900",
        "gender": "male",
        "address": "Hyd,India"
    },
    {
        "customerId": 23,
        "firstName": "kollu",
        "lastName": "Nagesh",
        "mobileNumber": "7666566901",
        "gender": "male",
        "address": "Hyd,India"
    },
    {
        "customerId": 24,
        "firstName": "manda",
        "lastName": "manasa",
        "mobileNumber": "7666566902",
        "gender": "female",
        "address": "MI,USA"
    },
    {
        "customerId": 25,
        "firstName": "Nelluri",
        "lastName": "Usha",
        "mobileNumber": "7666566903",
        "gender": "female",
        "address": "Texas,USA"
    }
]



===================

Query: Select * from customer_details;

======================

Put: http://localhost:9095/customer/updatecustomer/22 

{
        "customerId": 22,
        "firstName": "kollu",
        "lastName": "Naresh",
        "mobileNumber": "7666566900",
        "gender": "male",
        "address": "Kodad,India"
    }


o/p:

{
    "customerId": 22,
    "firstName": "kollu",
    "lastName": "Naresh",
    "mobileNumber": "7666566900",
    "gender": "male",
    "address": "Kodad,India"
}


===============================

Dummy data for delete:




{
    "firstName": "xyz",
    "lastName": "ABC",
    "mobileNumber": "7666566903",
    "gender": "female",
    "address": "Texas,USA"
}


================

Delete: http://localhost:9095/customer/26 

==================

Delete all customers data:

Delete: http://localhost:9095/customer/deleteallcustomers 




