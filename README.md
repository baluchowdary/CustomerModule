# CustomerModule
#CustomerModule SpringBoot and MicroServices project

git clone URL
git checkout -b branchName

git pull origin branchName

git add .

git commit -m "Added comments"

git push origin branchName

==================================================Rest calls ================

port: Eureka - 8761 , APIgateway - 8765 , customer module- 9095 , bank module-9096

POST:

http://localhost:9095/customer/savecustomer

  {
        "firstName": "Bhardwaja",
        "lastName": "Kollu",
        "mobileNumber": "7666566904",
        "gender": "male",
        "address": "Kodad,Hyd"
    }

http://localhost:9096/bank/savebanks

{
    "bankName":"CITI",
    "bankIfscCode":"CITI0000003",
    "bankBranchAddress":"HiTech",
    "bankPoolingAccountNumber":"00001234503"
}

http://localhost:9096/custBank/savecustbankdetails

{
    "bankCustId":"13",
    "bankCustFirstName":"kollu",
    "bankCustLastName":"Bhardwaja",
    "bankCustMobileNumber":"7666566899",
    "bankCustGender":"male",
    "bankCustAddress":"Hyd,India",
    "custBankId":"1002",
    "custBankName":"ICICI",
    "custBankIfscCode":"ICIC0000002",
    "custBankBranchAddress":"ADIBATLA",
    "custBankAccountNo":"00000000002"

}

DELETE:

http://localhost:9095/customer/27

http://localhost:9095/customer/deleteallcustomers


PUT:

http://localhost:9095/customer/updatecustomer/22

{
        "customerId": 22,
        "firstName": "kollu",
        "lastName": "Naresh",
        "mobileNumber": "7666566900",
        "gender": "male",
        "address": "Kodad,India"
 }
 
 http://localhost:9096/bank/updatebankdetails/29
 
 {
        "bankId": 29,
        "bankName": "SBI",
        "bankIfscCode": "SBIN0000001",
        "bankBranchAddress": "SP",
        "bankPoolingAccountNumber": "00001234501"
  }

GET:

http://localhost:9095/customer/getAllCustomers

http://localhost:9095/customer/custById/12

http://localhost:9095/customer/custByIdd/13

http://localhost:9095/customer/custByIddFeign/12

http://localhost:9096/bank/getAllBanks

http://localhost:9096/bank/1001

http://localhost:9096/custBank/getAllCustBankDetails

http://localhost:9096/custBank/101

http://localhost:9096/custBank/bankCustById/12

http://localhost:9096/custBank/bankCustByIdd/12

http://localhost:8765/CUSTOMERMODULE/customer/custByIdd/13

http://localhost:8765/CUSTOMERMODULE/customer/custByIddFeign/12

http://localhost:8765/customermodule/customer/custByIdd/13

http://localhost:8765/customermodule/customer/custByIddFeign/12

http://localhost:8765/customer/custByIdd/13

http://localhost:8765/customer/custByIddFeign/12

http://localhost:8765/bank/getAllBanks

http://localhost:8765/custBank/getAllCustBankDetails

http://localhost:8765/customerkollu/13

Swagger Documentation:

Customer : http://localhost:9095/swagger-ui.html

Bank : http://localhost:9096/swagger-ui.html


========================================================
Distributed Tracing concept / Sleuth, ZipKin, RabbitMQ, --> To generate Unique id per one logger. 

We need to download below given zipkin jar and run by open cmd prop
zipkin-server-2.12.9-exec

cmd/> java -jar zipkin-server-2.12.9-exec

Zipkin URL : http://localhost:9411/







