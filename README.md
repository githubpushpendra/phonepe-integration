# Test Application

## Hit below API on browser

````
http://localhost:8080/api/user/initiate
````

## Choosing Net Banking or Card
### LogIn credentials for net-banking
````
Username: test
Password: test
````

### Card details for test
````
Debit Card
"card_number": "4242424242424242", 
"card_type": "DEBIT_CARD", 
"card_issuer": "VISA",
"expiry_month": 12, 
"expiry_year": 2023, 
"cvv": "936"

Credit Card
"card_number": "4208585190116667",
"card_type": "CREDIT_CARD",
"card_issuer": "VISA”,
“expiry_month": 06,
"expiry_year": 2027,
"cvv": "508"

````
At last, you will receive status of payment.