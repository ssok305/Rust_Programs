Last update: 3-12-21

# Customer Web App Test
# Team 1: SpilledCoffee

Basic website for customer order placement using HTML/CSS/JS/PHP/MySQL.
All images used are free-to-use with no attribution.

Available at spilledcoffee.net for team members.

To view locally: 
1. disable any existing localhost. 
2. run an Apache server and a MySQL server using XAMMP (or whatever else)
3. Put folder in htdocs folder in XAMMP
4. Import database tables using PHPMyAdmin (included with XAMMP) or Workbench
5. Use same database name in "includes/dbh-inc.php" or change it
6. Go to index.php using browser

Current functionality:
  - Signup new user
  - Login existing user
  - View product page
  - Add products to cart
  - View cart
  - Delete product from cart
  - Place order / checkout
  - View past orders
  - Error messages
  
Sprint 3 Goals:
  - Add comments to code
  - Validate checkout form
  - Double check all error msgs
  
Future goals:
  - Back-end integration
  - Refactor for logic and layout separation on certain pages
  - Improve mobile view
  - Update product qty in cart
  - Cancel order
  - Cancel order confirmation email
  - Order confirmation email
  - Remarketing email
