Feature: Shopping Cart

@addToCart
Scenario: Add a product to the shopping cart and modify the quantity
  Given the user is on the home page
  When the user clicks on Iphone product
  And the user clicks on Laptop product
  And the user clicks on "Shopping Cart Header"
  And the user clicks on "Shopping Cart Button"
  And the user changes the products quantity
    | product        | quantity |
    | First Product  | 2        |
    | Second Product | 3        |
  Then the order total reflects the sum of all products added to the cart