# Auction application

Auction is an application where users can participate in auctions, win and buy. User of this application can have two roles: customer and seller.

## Features
### Features provided:
##### General:
- create an account
- log in the application
- change personal information like address, profile picture, email, etc.
- add a credit card
- last chance, new arrivals, feature products and collections can be found on the home page
- by clicking on a product, all the necessery information are provided like images, highest bid, number of bids, related products
- bidding on a product
- number of the viewers of a product
- notification when someone bid on a product that user is viewing
- user can see list of his bids, products (active or sold)
- add a product to the watchlist
- delete from the watchlist
- notification to the user that won when auction is done 
- pay a product
- search products in the shop
- filter search by category, price, color, size, etc.
##### Seller:
- view the list of a bidders 

### Features that need to be provided:
- send email to the seller when payment of a user is done with the necessery information
- add new item

## Realization:
### Technologies
- Angular 2+ (front-end)
- Spring Boot (back-end)

### Database
- PostgreSQL

### Other
- Cloudinary (for saving a product's image)
- Stripe (for the payment process)
- Authentication and Authorization via JWT
- Hibernate Criteria for writing queries
- Pagination of received collections from APIs
- Validation for submittable forms
- Websockets for notifications

### Install and run:
After cloning the git repository: <br>
Back-end: <br>
- Open backend/auction in IDE (I worked in Intellij IDEA)
- Build project
- Run application

Front-end: <br>
- Open frontend/auction in a code editor (visual studio code, etc.)
- Install packages by entering the command in the terminal: npm install
- Run frontend with the command: ng serve

## Deployed application:
- First open API's on this link: https://atlantbh-auction-api.herokuapp.com
- After that, open this link: https://atlantbh-auction.herokuapp.com




