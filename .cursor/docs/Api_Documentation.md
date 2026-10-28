Route E-commerce App — API Documentation
Overview
API Base URL: https://ecommerce.routemisr.com
API Version: v1 and v2
Format: JSON
Authentication: Token-based authentication
Postman Collection: Route E-commerce App
Base Configuration
Content-Type: application/json


Set the following variables in your Postman environment:

Variable	Value
baseUrl	https://ecommerce.routemisr.com
token	Your authentication token
productId	Product ID
reviewId	Review ID
cartId	Cart ID
couponName	Coupon code

Use {{baseUrl}} and the other variables in requests to simplify configuration.

Table of Contents
Categories
Subcategories
Brands
Products
Authentication and Users
Wishlist
User Addresses
Cart API v2
Cart API v1
Reviews
Orders
1. Categories
Get All Categories

GET /api/v1/categories

Retrieve all categories.

Optional query parameters (disabled in the original collection):

Parameter	Example	Description
limit	2	Limit the number of results
page	2	Page number
keyword	Books	Search by keyword

Example:

GET {{baseUrl}}/api/v1/categories

Get Specific Category

GET /api/v1/categories/:categoryId

Retrieve a category by its ID.

Example:

GET {{baseUrl}}/api/v1/categories/6407ebf65bbc6e43516931ec

2. Subcategories
Get All Subcategories

GET /api/v1/subcategories

Retrieve all subcategories.

Optional query parameter: limit.

GET {{baseUrl}}/api/v1/subcategories

Get Specific Subcategory

GET /api/v1/subcategories/:subcategoryId

GET {{baseUrl}}/api/v1/subcategories/6407f40db575d3b90bf957fa

Get Subcategories by Category

GET /api/v1/categories/:categoryId/subcategories

Retrieve subcategories associated with a category.

GET {{baseUrl}}/api/v1/categories/6407ea3d5bbc6e43516931df/subcategories

3. Brands
Get All Brands

GET /api/v1/brands

Retrieve all brands.

Optional query parameters (disabled in the original collection):

limit: Maximum number of results.
keyword: Search term.
GET {{baseUrl}}/api/v1/brands

Get Specific Brand

GET /api/v1/brands/:brandId

GET {{baseUrl}}/api/v1/brands/64089ceb24b25627a2531596

4. Products
Get All Products

GET /api/v1/products

Retrieve products, with optional pagination, sorting, searching, field selection, and filtering.

Optional query parameters (examples from the collection):

Parameter	Example	Purpose
limit	2	Limit results
page	2	Select page
sort	-price	Sort by price descending
fields	title,price	Select fields
keyword	new	Search products
price[gte]	100	Minimum price
price[lte]	13	Maximum price
brand	Brand ID	Filter by brand
category[in]	Category ID	Filter by category

The original collection contains two category[in] entries. Their exact combination behavior depends on the API implementation.

Example:

GET {{baseUrl}}/api/v1/products?limit=10&page=1


Sorting example:

GET {{baseUrl}}/api/v1/products?sort=-price


Filtering example:

GET {{baseUrl}}/api/v1/products?price[gte]=100&price[lte]=500

Get Specific Product

GET /api/v1/products/:productId

GET {{baseUrl}}/api/v1/products/6428de2adc1175abc65ca05b

5. Authentication and Users
Signup

POST /api/v1/auth/signup

Create a new user account.

Request body:

{
  "name": "Your Name",
  "email": "user@example.com",
  "password": "YourPassword123",
  "rePassword": "YourPassword123",
  "phone": "01000000000"
}


The original Postman collection saves the returned token into the JWT environment variable.

Sign In

POST /api/v1/auth/signin

Authenticate an existing user.

Request body:

{
  "email": "user@example.com",
  "password": "YourPassword123"
}


The collection also saves the returned token into JWT.

Forgot Password

POST /api/v1/auth/forgotPasswords

Request a password reset.

{
  "email": "user@example.com"
}

Verify Reset Code

POST /api/v1/auth/verifyResetCode

Verify the password reset code.

{
  "resetCode": "123456"
}

Reset Password

PUT /api/v1/auth/resetPassword

Reset a user's password.

{
  "email": "user@example.com",
  "newPassword": "YourNewPassword123"
}

Update Logged-in User Password

PUT /api/v1/users/changeMyPassword

Authentication: Required.

Headers:

token: {{token}}
Content-Type: application/json


Request body:

{
  "currentPassword": "CurrentPassword123",
  "password": "NewPassword123",
  "rePassword": "NewPassword123"
}

Update Logged-in User Data

PUT /api/v1/users/updateMe/

Authentication: Required.

{
  "name": "Your Name",
  "email": "user@example.com",
  "phone": "01000000000"
}

Get All Users

GET /api/v1/users

Retrieve users. The collection includes optional limit and keyword query parameters.

Verify Token

GET /api/v1/auth/verifyToken

Authentication: Required.

GET {{baseUrl}}/api/v1/auth/verifyToken
token: {{token}}

6. Wishlist

All wishlist operations in this collection use the token header.

Add Product to Wishlist

POST /api/v1/wishlist

{
  "productId": "{{productId}}"
}

Remove Product from Wishlist

DELETE /api/v1/wishlist/:productId

The original collection supplies an ID in the URL. Confirm whether the API expects a product ID or a wishlist-item ID.

Get Logged-in User Wishlist

GET /api/v1/wishlist

Returns the authenticated user's wishlist.

7. User Addresses

All address endpoints in this collection use token-based authentication.

Add Address

POST /api/v1/addresses

{
  "name": "Home",
  "details": "Home address details",
  "phone": "01000000000",
  "city": "Cairo"
}

Remove Address

DELETE /api/v1/addresses/:addressId

Get Specific Address

GET /api/v1/addresses/:addressId

Get Logged-in User Addresses

GET /api/v1/addresses

Retrieve the authenticated user's saved addresses.

8. Cart API v2

Base path: /api/v2/cart

All operations require the user's token.

Add Product to Cart

POST /api/v2/cart

{
  "productId": "{{productId}}"
}

Get Logged-in User Cart

GET /api/v2/cart

Retrieve the authenticated user's cart.

Update Cart Product Quantity

PUT /api/v2/cart/:productId

{
  "count": 2
}

Remove Product from Cart

DELETE /api/v2/cart/:productId

Clear User Cart

DELETE /api/v2/cart

Apply Coupon to Cart

PUT /api/v2/cart/applyCoupon

{
  "couponName": "{{couponName}}"
}

9. Cart API v1

Base path: /api/v1/cart

Add Product to Cart

POST /api/v1/cart

Request body:

{
  "productId": "{{productId}}"
}

Response body. `products[].product` is a product id string. GET, PUT, and DELETE of a specific item return a populated product object instead.

{
  "status": "success",
  "message": "Product added successfully to your cart",
  "numOfCartItems": 1,
  "cartId": "6adfad1fcfea3d0013fa6d3f",
  "data": {
    "_id": "6adfad1fcfea3d0013fa6d3f",
    "cartOwner": "6adf98b7cfea3d0013fa62c2",
    "products": [
      {
        "count": 1,
        "_id": "6adfad1fcfea3d0013fa6d40",
        "product": "6428eb43dc1175abc65ca0b3",
        "price": 149
      }
    ],
    "createdAt": "2026-10-26T19:42:23.218Z",
    "updatedAt": "2026-10-26T19:42:23.254Z",
    "__v": 0,
    "totalCartPrice": 149
  }
}

Update Cart Product Quantity

PUT /api/v1/cart/:productId

Sample: https://ecommerce.routemisr.com/api/v1/cart/6428eb43dc1175abc65ca0b3

{
  "count": "1"
}

The original collection sends count as a string. Response body is the same populated cart as GET /api/v1/cart. `products[].product` is an object.

Get Logged-in User Cart

GET /api/v1/cart

`products[].product` is a populated product object. Subcategory, category, and brand are nested objects. The cart screen uses title and imageCover.

{
  "status": "success",
  "numOfCartItems": 1,
  "cartId": "6adfad1fcfea3d0013fa6d3f",
  "data": {
    "_id": "6adfad1fcfea3d0013fa6d3f",
    "cartOwner": "6adf98b7cfea3d0013fa62c2",
    "products": [
      {
        "count": 2,
        "_id": "6adfad1fcfea3d0013fa6d40",
        "product": {
          "subcategory": [
            {
              "_id": "6407f1bcb575d3b90bf95797",
              "name": "Women's Clothing",
              "slug": "women's-clothing",
              "category": "6439d58a0049ad0b52b9003f"
            }
          ],
          "_id": "6428eb43dc1175abc65ca0b3",
          "title": "Woman Shawl",
          "quantity": 220,
          "imageCover": "https://ecommerce.routemisr.com/Route-Academy-products/1680403266739-cover.jpeg",
          "category": {
            "_id": "6439d58a0049ad0b52b9003f",
            "name": "Women's Fashion",
            "slug": "women's-fashion",
            "image": "https://ecommerce.routemisr.com/Route-Academy-categories/1681511818071.jpeg"
          },
          "brand": {
            "_id": "64089bbe24b25627a253158b",
            "name": "DeFacto",
            "slug": "defacto",
            "image": "https://ecommerce.routemisr.com/Route-Academy-brands/1678285758109.png"
          },
          "ratingsAverage": 3.9,
          "id": "6428eb43dc1175abc65ca0b3"
        },
        "price": 149
      }
    ],
    "createdAt": "2026-10-26T19:42:23.218Z",
    "updatedAt": "2026-10-26T19:43:43.190Z",
    "__v": 0,
    "totalCartPrice": 298
  }
}

Remove Specific Cart Item

DELETE /api/v1/cart/:productId

Sample: https://ecommerce.routemisr.com/api/v1/cart/6adc9c37cfea3d0013f97460

Path parameter is the product id. Response body is the same populated cart as GET /api/v1/cart. `products[].product` is an object.

Clear User Cart

DELETE /api/v1/cart

Response body:

{
  "message": "success"
}

Authentication: The original collection supplies a token header for these operations.

10. Reviews
Create Review for Product

POST /api/v1/products/:productId/reviews

Authentication: Required.

{
  "review": "Great product",
  "rating": 5
}

Get Reviews for Product

GET /api/v1/products/:productId/reviews

Retrieve reviews associated with a product.

Get All Reviews

GET /api/v1/reviews

Get Review by ID

GET /api/v1/reviews/:reviewId

Update Review

PUT /api/v1/reviews/:reviewId

Authentication: Required. The collection labels this operation as owner-only.

{
  "review": "Updated review",
  "rating": 4
}

Delete Review

DELETE /api/v1/reviews/:reviewId

Authentication: Required. The collection labels this operation as owner/admin.

11. Orders
Create Cash Order from Cart (v2)

POST /api/v2/orders/:cartId

Authentication: Required.

{
  "shippingAddress": {
    "details": "Test address",
    "phone": "01000000000",
    "city": "Cairo",
    "postalCode": "12345"
  }
}

Create Cash Order (v1)

POST /api/v1/orders/:cartId

Authentication: Required.

{
  "shippingAddress": {
    "details": "Shipping address details",
    "phone": "01000000000",
    "city": "Cairo"
  }
}

Get All Orders

GET /api/v1/orders/

Retrieve orders. Actual visibility depends on the API's authorization rules.

Get User Orders

GET /api/v1/orders/user/:userId

Retrieve orders for the specified user ID, subject to API permissions.

Create Checkout Session

POST /api/v1/orders/checkout-session/:cartId

Authentication: Required.

Query parameter:

Parameter	Example	Description
url	http://localhost:3000	Frontend URL used for checkout redirects

Request body:

{
  "shippingAddress": {
    "details": "Shipping address details",
    "phone": "01000000000",
    "city": "Cairo"
  }
}


Example:

POST {{baseUrl}}/api/v1/orders/checkout-session/{{cartId}}?url=http://localhost:3000

Authentication Guidelines

For protected endpoints, use the authentication token obtained from the signup or signin response.

token: {{token}}


Some requests in the original collection use a manually entered token, while others use Postman variables. Replace hardcoded tokens with {{token}} and keep credentials in a local Postman environment.

The collection also contains Bearer authentication configuration for a v2 cart request. Follow the specific endpoint's authentication requirements if the API distinguishes between Bearer authorization and the token header.

Response Documentation

The supplied collection has empty response arrays for all requests. Consequently, actual response examples, response schemas, HTTP status codes, and error messages cannot be documented reliably from this JSON alone.

Run each request in Postman and save representative responses if you want those details included.

Notes
Endpoint paths and HTTP methods are preserved from the supplied collection.
:productId, :categoryId, :reviewId, :addressId, :brandId, :subcategoryId, :cartId, and :userId represent path parameters.
Variables written as {{variableName}} are Postman environment or collection variables.
Query parameters marked disabled in the original collection are documented as optional examples, not active defaults.
Confirm permissions and request formats against the live API before using these examples in production.
Do not commit real access tokens, passwords, or personal information to a public repository.