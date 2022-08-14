 **2 POST - http://localhost:8080/api/placeOrder**
 
 -> Create an order object
 -> Create OrderItem objects by adding the values such as:
 1. Drink id
 2. Toppings list
 3. The order this OrderItem belongs to (id)
-> Calculate total and assign to order
-> Calculate discounted total and assign to order

 Request Payload   
 {     
     "items": [
         {
             "drinkName": "Latte",
             "toppingNames": ["Chocolate Sauce"],
             "quantity": 3
         },
         {
             "drinkName": "Latte",
             "toppingNames": ["Chocolate Sauce", "Milk"],
             "quantity": 1
         }
     ],
     "customer": "abcd@gmail.com"
 }
 
 **Response** 
 
{
    "amount": 27.0,
    "discounted": 20.25,
    "invoiceNumber": 254,
    "date": "2022-08-12",
    "items": [
        {
            "id": 14,
            "drinkId": null,
            "drinkName": "Latte",
            "toppingNames": [
                "Chocolssate"
            ],
            "quantity": 3
        },
        {
            "id": 15,
            "drinkId": null,
            "drinkName": "Latte",
            "toppingNames": [
                "Chocolate Sauce",
                "Milk"
            ],
            "quantity": 1
        }
    ],
    "orderId": 13
}