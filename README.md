## Electronic Store’s Requirements

###Admin User Operations
• Create a new product

• Remove a product

• Add discount deals for products (Example: Buy 1 get 50% off the second)

###Customer Operations
• Add and remove products to and from a basket

• Calculate a receipt of items, including all purchases, deals applied and total price

## Submission Details

####Project is using Java version 17 /SpringBoot/H2 in Memory DB

####Project can be build using mvn clean install

####Application Can be started by running *com.bullish.elecstore.ElecstoreApplication.java*

Everything should be tested with automated tests
  by running *com.bullish.elecstore.ElecstoreApplicationTests.java* under test folder
  
Unit test for Service and controller classes are in 
*com.bullish.elecstore.controller* and *com.bullish.elecstore.service* packages under test folder

### EndPoint REST API

####Admin
*Creating product* POST- http://localhost:8080/admin/products

Example Data:
{"id":"5",
"name":"prod5",
"price":"50"}

*Deleting product* DELETE- http://localhost:8080/admin/products/{productId}

Example:
http://localhost:8080/admin/products/1 (Product id with 1 will be deleted)

*Adding Discount Deal* POST- http://localhost:8080/admin/products/{productId}/discounts

Example :
http://localhost:8080/admin/products/1/discounts (Will add deal to product id 1)

{
"buyQuantity":"2",
"discountPercentage":"50"
}

*Deleting Discount Deal* DELETE- http://localhost:8080/admin/products/{productId}/discounts/{discountId}

####Customer
*Add Products to basket* POST- http://localhost:8080/customer/basket/{basketID}/products/{productId}

Example:
http://localhost:8080/customer/basket/1/products/1 (Basket id 1 will be added with Product id 1)

*Remove Product from Basket* DELETE- http://localhost:8080/customer/basket/{basketID}/productdelete/{productId}

Example:
http://localhost:8080/customer/basket/1/productdelete/3 (Product id with 3 will be deleted from basket id 1)

*Get Receipt* GET- http://localhost:8080/customer/basket/{basketID}/receipt

Example :
http://localhost:8080/customer/basket/1/receipt (Receipt will be generated for all products in Basket id 1)


### H2 in Memory DB console and GUI

http://localhost:8080/h2-console

User:SA

Password:

### Single command line execution

copy elecstore-0.0.1-SNAPSHOT.jar (target folder) and application.properties(resource folder) into any folder example c:\elecapp

Start the application using

c:\elecapp>java -jar elecstore-0.0.1-SNAPSHOT.jar
