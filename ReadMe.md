# Starbux
### Starbux is an online coffee place startup, where users can order drinks/toppings and admins can create/update/delete drinks/toppings and have access to reports.

## API Documentation:
https://www.postman.com/warped-satellite-505301/workspace/5c7f7617-275e-4ac5-84e9-f8efdcedf030/documentation/14433878-bd815db3-b4ae-4495-b8cf-bba14a2aaaf1

## POSTMAN Collection
https://www.postman.com/warped-satellite-505301/workspace/starbux/collection/14433878-bd815db3-b4ae-4495-b8cf-bba14a2aaaf1?action=share&creator=14433878
## API Requirements
### Admin API:
An API that allows admins to:
- create/update/delete products and toppings.
- Show Reports
#### Reports:
- Total amount of the orders per customer.
- Most used toppings for drinks.

### Shop API
An API that will be used to order drinks with any of the topping combinations.
- Visitor journeys should be transparent, the current amount of the cart and the products
should be communicated back to the caller of the API.
- When finalizing the order, the original amount and the discounted amount should be
communicated back to the caller of the API.

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