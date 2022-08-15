# Starbux
**Starbux is an online coffee place startup, where users can order drinks/toppings and admins can create/update/delete drinks/toppings and have access to reports.**
#### List of Available Products
##### Drinks:
- Black Coffee - 4 eur
- Latte - 5 eur
- Mocha - 6 eur
- Tea - 3 eur
##### Toppings/sides:
- Milk - 2 eur
- Hazelnut syrup - 3 eur
- Chocolate sauce - 5 eur
- Lemon - 2 eur
## Running the application:
1. Clone the app
2. Go to the app root folder and run the following command to start the application:
```
mvn spring-boot:run
```
## API Documentation:
All the required details regarding each endpoint have been added to the following Postman documentation:
https://www.postman.com/warped-satellite-505301/workspace/5c7f7617-275e-4ac5-84e9-f8efdcedf030/documentation/14433878-bd815db3-b4ae-4495-b8cf-bba14a2aaaf1
## API Requirements
**Admin API:**
An API that allows admins to:
- create/update/delete products and toppings.
- Show Reports
**Reports:**
- Total amount of the orders per customer.
- Most used toppings for drinks.
## Admin API endpoints
```
1. {{base_url}}/admin/products
2. {{base_url}}/admin/report/customers
3. {{base_url}}/admin/report/toppings
4. {{base_url}}/admin/customers
5. {{base_url}}/admin/orders
```
**Shop API**:
An API that will be used to order drinks with any of the topping combinations.
- The current amount of the cart and the products
should be communicated back to the caller of the API.
- When finalizing the order, the original amount and the discounted amount should be
communicated back to the caller of the API.
## Shop API endpoints
```
1. {{base_url}}/shop/placeOrder
```
## Postman Collection
https://www.postman.com/warped-satellite-505301/workspace/starbux/collection/14433878-bd815db3-b4ae-4495-b8cf-bba14a2aaaf1?action=share&creator=14433878
## Notes
### Endpoint for placing an order: *base_url/shop/placeOrder*
To place an order, send a POST request to this endpoint.
Required Request Headers:
Content-type: application/json

Payload:
An object containing:
- items: An array of order items
        - order item: 
        1. drinkName : String - Name of the drink (Required)
        2. toppingNames: List of String(s) - Name of the required toppings (Optional)
        3. quantity: Integer - number of required drink items (Optional / If quantity is not provided, it is set to 1)

- customer: customer id such as username/ email
```
{
    "items": [
        {
            "drinkName": "Tea",
            "toppingNames": ["Chocolate sauce", "Lemon"],
            "quantity" : 2
        },
        {
            "drinkName": "Latte",
            "toppingNames": ["Chocolate sauce", "Lemon"]
        }
    ],
    "customer": "yusra23" 
}
```
#### Response
##### Order Successful
In case the order is received successfully, the following items are returned:
1. "message": Success message.
2. "total": Order total
3. "discountedTotal": Order total after applying the dicount (In case of no discount: 0.0)
4. "invoiceNumber": Invoice/ receipt number.
5. "date": current date
6. "items": the order items along with a message regarding each item.
###### items: 
- "message": This value shows if the drinks and toppings have been added successfully or not.
- "drinkId": drink id
- "drinkName": name of the ordered drink
- "toppingNames": name of the ordered toppings
- "quantity": no. of drinks
##### Order not successful
In case the order is not received successfully, the following items are returned:
- "message": Invalid Order Object Format. Customer email/username or items not received!
- "total": 0.0
- "discountedTotal": 0.0
- "invoiceNumber": 0.0
- "date": null
- "items": null



##### Example Response:
```
{
    "message": "Order received! Thanks for shopping with us.",
    "total": 19.0,
    "discountedTotal": 14.25,
    "invoiceNumber": 465,
    "date": "2022-08-15",
    "items": [
        {
            "id": 19,
            "message": "Successfully added drink! ",
            "drinkId": 4,
            "drinkName": "tea",
            "toppingNames": null,
            "quantity": 1
        }
    ],
    "orderId": 18
}
```
### Endpoint for adding a new product: *base_url/admin/products*

Send a **POST** request to this endpoint for adding a new product.
*Payload*:
An object with the following properties/ entities:
- price: float value (Required)
- name: string value (Required)
- category: *"drink"* or "topping" (Optional / default category is drink)

**Name**
The value of name should be unique. According to name requirements, they're case in-sensitive such as the following values are equal:
CHOCOLATE SAUCE and chocolate sauCe

But the following values will be treated differently:
LemonSauce and Lemon Sauce

**Category**
Since the current shop version just has 2 types of categories in products:
1. Drink
2. Toppings (Every side included)

The reason this value (category) is set to be string and not a boolean (for 2 values), is for future extension of the product categories. This way category property can easily be extended without having to change the entire Product class.

```
{
    "name": "Chocolate Shake",
    "price": 9.8,
    "category" "drink"
}
```

